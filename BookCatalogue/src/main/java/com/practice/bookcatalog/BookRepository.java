/**
 * 
 */
package com.practice.bookcatalog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ankita
 *
 */
public interface BookRepository extends JpaRepository<Book, String> {
	 Optional<Book> findByTitle(String title);
}
