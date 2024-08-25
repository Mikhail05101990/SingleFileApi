package com.example.techtask.repository;

import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;
import com.example.techtask.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, BigInteger> {
}
