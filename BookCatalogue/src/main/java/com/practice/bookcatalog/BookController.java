package com.practice.bookcatalog;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import error.NoBookFoundException;

/**
 * A REST Controller class for Add, delete, update and display list rest api end
 * points.
 * 
 * @author Ankita Srivastava
 *
 */
@RestController
public class BookController {

	@Autowired
	private BookRepository repository;
	@Autowired
	private JmsTemplate jms;

	/**
	 * Add a new book to the list of books
	 */
	@PostMapping(path = "/addbook", consumes = "application/json")
	public Book AddBook(@RequestBody @Valid Book newbook) {
		jms.convertAndSend("BookCatalouge", "New book added with ISBN # " + newbook.getIsbn());
		return repository.save(newbook);
	}

	/**
	 * Add books via json objects
	 */
	@PostMapping(path = "/addbooks", consumes = "application/json")
	List<Book> AddBooks(@RequestBody List<Book> newbooks) {
		newbooks.forEach(x -> jms.convertAndSend("BookCatalouge", "New book added with ISBN # " + x.getIsbn()));
		return repository.saveAll((List<Book>) newbooks);
	}

	/**
	 * Show list of all the books in database
	 */
	@GetMapping("/books")
	List<Book> findAll() {
		List<Book> books = repository.findAll();
		if (books == null) {
			throw new NoBookFoundException("There are no books to be displayed.");
		}
		return books;
	}

	/**
	 * Search book by ISBN from the list of books
	 */
	@GetMapping("/SearchByISBN/{isbn}")
	Book SearchByIsbn(@PathVariable @Valid String isbn) {
		return repository.findById(isbn)
				.orElseThrow(() -> new NoBookFoundException("No book found associated to ISBN# " + isbn));
	}
	
	
	/**
	 * Search book by author and title
	 */	
	@GetMapping("/SearchByTitleAuthor/{title}/{author}")
	Book SearchByTitleAuthor(@PathVariable(value = "title") String title,
			@PathVariable(value = "author") String author) {
		return repository.findByTitle(title).filter(x -> x.getAuthor().contains(author)).orElseThrow(
				() -> new NoBookFoundException("No book found with title " + title + " and author " + author));
	}

	/**
	 * Update a book from the list of books
	 */
	@PutMapping(path = "/updatebook", consumes = "application/json")
	Optional<Book> UpdateBook(@RequestBody Book updatebook) {
		if (!repository.findById(updatebook.getIsbn()).isPresent()) {
			throw new NoBookFoundException("No book found associated to ISBN# " + updatebook.getIsbn());
		} else {

			if (updatebook.getAuthor() != null && updatebook.getPublicationDate() != null
					&& updatebook.getTitle() != null) {
				repository.findById(updatebook.getIsbn()).map(x -> {
					x.setAuthor(updatebook.getAuthor());
					x.setPublicationDate(updatebook.getPublicationDate());
					x.setTitle(updatebook.getTitle());
					return repository.save(x);
				});
				jms.convertAndSend("BookCatalouge",
						"The title, author and publication date of book associated with ISBN # " + updatebook.getIsbn()
								+ " has been updated.");
			} else {
				if (updatebook.getAuthor() != null) {
					repository.findById(updatebook.getIsbn()).map(x -> {
						x.setAuthor(updatebook.getAuthor());
						return repository.save(x);
					});
					jms.convertAndSend("BookCatalouge",
							"The author of book associated with ISBN # " + updatebook.getIsbn() + " has been updated.");
				}
				if (updatebook.getTitle() != null) {
					repository.findById(updatebook.getIsbn()).map(x -> {
						x.setTitle(updatebook.getTitle());
						return repository.save(x);
					});
					jms.convertAndSend("BookCatalouge",
							"The title of book associated with ISBN # " + updatebook.getIsbn() + " has been updated.");
				}
				if (updatebook.getPublicationDate() != null) {
					repository.findById(updatebook.getIsbn()).map(x -> {
						x.setPublicationDate(updatebook.getPublicationDate());
						return repository.save(x);
					});
					jms.convertAndSend("BookCatalouge", "The publication date of book associated with ISBN # "
							+ updatebook.getIsbn() + " has been updated.");
				}
			}
		}
		return repository.findById(updatebook.getIsbn());
	}

	
	/**
	 * Delete a book from the list of books
	 */	
	@DeleteMapping(path = "/deletebook/{isbn}")
	String DeleteBook(@PathVariable String isbn) {

		if (!repository.findById(isbn).isPresent()) {
			throw new NoBookFoundException("No book found associated to ISBN# " + isbn);
		} else {
			repository.deleteById(isbn);
			jms.convertAndSend("BookCatalouge", "The book is deleted with ISBN # " + isbn);
			return "Book associated to ISBN #" + isbn + " has been deleted successfully";
		}
	}
}
