package com.aurelius.graph.repository;

import com.aurelius.graph.document.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
