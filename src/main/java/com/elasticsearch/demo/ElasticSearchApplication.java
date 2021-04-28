package com.elasticsearch.demo;

import com.elasticsearch.demo.model.Book;
import com.elasticsearch.demo.service.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;

@SpringBootApplication
public class ElasticSearchApplication implements CommandLineRunner {
	@Autowired
	private ElasticsearchOperations es;

	@Autowired
	private BookService bookService;


	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		printElasticSearchInfo();

		bookService.save(new Book("1001", "Elasticsearch Basics", "RR", "23-FEB-2017"));
		bookService.save(new Book("1002", "Apache Lucene Basics", "RP", "13-MAR-2017"));
		bookService.save(new Book("1003", "Apache Solr Basics", "RR", "21-MAR-2017"));

		Page<Book> books = bookService.findByAuthor("RP", new PageRequest(0, 10));


		books.forEach(x -> System.out.println(x));
	}

	private void printElasticSearchInfo() {
		System.out.println("Ucc");

		System.out.println("--ElasticSearch--");
		Client client = es.getClient();
		Map<String, String> asMap = client.settings().getAsMap();

		asMap.forEach((k, v) -> {
			System.out.println(k + " = " + v);
		});
		System.out.println("--ElasticSearch--");
	}

}
