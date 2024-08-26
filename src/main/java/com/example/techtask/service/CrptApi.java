package com.example.techtask.service;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import com.example.techtask.model.Document;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class CrptApi {
    private static int requestlimit = 10;
    private static int timeLimit = 1;
    private static TimeUnit spanType = TimeUnit.MINUTES;
    private static long prevStamp = 0;
    private static AtomicInteger counter = new AtomicInteger(0);
    private static ReentrantLock lock = new ReentrantLock();

    public CrptApi(TimeUnit timeUnit, int limit)
    {
        timeLimit = CalculateSpan(timeUnit);
        requestlimit = limit;
        spanType = timeUnit;
        prevStamp = System.nanoTime();
    }

    public void CreateDocument(Document doc)
    {   
        if(counter.incrementAndGet() <= requestlimit)      
        {
           DB.create(doc);
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
                DB.create(doc);
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

    class DB {

        static String jdbcUrl = "jdbc:postgresql://localhost:5432/techtask";
        static String username = "techtask";
        static String password = "techtask";
        static String pattern = "yyyy-MM-dd";
        static DateFormat df = new SimpleDateFormat(pattern);

        public static void create(Document doc) {
            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                
                String sql = "INSERT INTO documents(id, doc_id, doc_status, doc_type, descr_id, import_request"
                    .concat(", owner_inn, participant_inn, producer_inn, production_type, production_date, reg_date")
                    .concat(", reg_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, doc.Id.longValue());
                preparedStatement.setString(2, doc.DocumentId);
                preparedStatement.setString(3, doc.Status);
                preparedStatement.setString(4, doc.DocumentType);
                preparedStatement.setLong(5, doc.DescriptionId.longValue());
                preparedStatement.setBoolean(6, doc.ImportRequest);
                preparedStatement.setString(7, doc.OwnerInn);
                preparedStatement.setString(8, doc.ParticipantInn);
                preparedStatement.setString(9, doc.ProducerInn);
                preparedStatement.setString(10, doc.ProductionType);
                preparedStatement.setDate(11, doc.ProductionDate);
                preparedStatement.setDate(12, doc.RegistrationDate);
                preparedStatement.setString(13, doc.RegistrationNumber);
                preparedStatement.executeUpdate();
                
                connection.close();
            }
            catch(ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}