package com.practice.bookcatalog;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class BookCatalogueApplication {

	public static void main(String[] args) {
		ApplicationContext appContx =SpringApplication.run(BookCatalogueApplication.class, args);
		JmsTemplate jms = appContx.getBean(JmsTemplate.class);
		jms.convertAndSend("BookCatalouge", "BookCatalouge API up and running.");
	}
	
	/**
	 *  List of the books in h2 database
	 */
	@Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.save(new Book("9781484250518", "Pro Spring Security: Securing Spring Framework 5 and Boot 2-based Java Applications", " Carlo Scarioni, Massimo Nardone " , "2019-11-22"));
            repository.save(new Book("9781789613476","Hands-On Microservices with Spring Boot and Spring Cloud: Build and deploy Java microservices using Spring Cloud, Istio, and Kubernetes", "Magnus Larsson ", "2019-09-20"));
            repository.save(new Book("9781617294549","Microservices Patterns: With examples in Java","Chris Richardson",  "2018-11-19"));
            repository.save(new Book("9780578528151","Blockchain Bubble or Revolution: The Future of Bitcoin, Blockchains, and Cryptocurrencies","Neel Mehta,Aditya Agashe,Parth Detroja","2019-07-12"));
        };
    }
	

}
