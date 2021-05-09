package com.practice.bookcatalog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 * @author Ankita srivastava
 *
 */
@Entity
public class Book {

	@Pattern(regexp = "^[0-9]{13}", message = "Length must be 13 digits") // for 13 digit ISBN number
	@Id // Unique Id
	private String isbn;
	private String title;
	@ElementCollection
	private List<String> author;
	private Date publicationDate;

	public Book(String isbn, String title, String author, String publicationDate) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = Stream.of(author.split(",")).collect(Collectors.toList());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.publicationDate = df.parse(publicationDate);
		} catch (ParseException ex) {
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public Book() {
		super();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

}
