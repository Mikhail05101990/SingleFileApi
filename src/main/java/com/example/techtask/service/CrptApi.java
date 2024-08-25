package com.example.techtask.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import com.example.techtask.model.Document;
import com.example.techtask.repository.DocumentRepository;

public class CrptApi {
    private static int requestlimit = 10;
    private static int timeLimit = 1;
    private static TimeUnit spanType = TimeUnit.MINUTES;
    private static long prevStamp = 0;
    private static AtomicInteger counter = new AtomicInteger(0);
    private final DocumentRepository documentRepository;
    private static ReentrantLock lock = new ReentrantLock();

    public CrptApi(DocumentRepository documentRepository, TimeUnit timeUnit, int limit)
    {
        timeLimit = CalculateSpan(timeUnit);
        requestlimit = limit;
        spanType = timeUnit;
        prevStamp = System.nanoTime();
        this.documentRepository = documentRepository;
    }

    public void CreateDocument(Document doc)
    {   
        if(counter.incrementAndGet() <= requestlimit)      
        {
            documentRepository.save(doc);
        }
        else
        {
            int elapsed;

            do
            {
                elapsed = CalculateElapsed();

                if(elapsed < timeLimit)
                    WaitForNextSpan();
            }
            while (elapsed < timeLimit && counter.incrementAndGet() <= requestlimit);

            lock.lock();
            
            try
            {
                prevStamp = System.nanoTime();
                counter.set(1);
                documentRepository.save(doc);
            }
            finally
            {
                lock.unlock();
            }

            
        }
    }

    private int CalculateSpan(TimeUnit spanType)
    {
        if(spanType.equals(TimeUnit.SECONDS))
            return 1;

        if(spanType.equals(TimeUnit.MINUTES))
            return 60;
        
        if(spanType.equals(TimeUnit.HOURS))
            return 3600;

        return 10;
    }

    private void WaitForNextSpan()
    {
        long waitTime = 100;
        int elapsed = CalculateElapsed();

        while(elapsed < timeLimit)
        {
            try
            {
                Thread.sleep(waitTime);     
            }
            catch(InterruptedException e)
            {
                break;
            }    

            elapsed = CalculateElapsed();
        }
    }

    private int CalculateElapsed()
    {
        long estimatedTime = System.nanoTime() - prevStamp;
        
        return (int)TimeUnit.SECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
    }
}