package com.aurelius.graph;

import com.aurelius.graph.document.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@ComponentScan("com.aurelius.graph")
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	public void run (String... args) {
		Book book = new Book();
		book.setAuthor("Kim");
		mongoTemplate.insert(book);
	}
}
