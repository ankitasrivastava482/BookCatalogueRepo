package error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception class to handle when no book found.
 * 
 * @author Ankita Srivastava
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoBookFoundException extends RuntimeException {
	  	   public NoBookFoundException(String str) {
	        super(str);
	    }
}
