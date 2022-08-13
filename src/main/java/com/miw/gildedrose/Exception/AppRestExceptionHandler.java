package com.miw.gildedrose.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Global AppRestExceptionHandler
 *
 */
@ControllerAdvice
public class AppRestExceptionHandler {

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	private class ExceptionJsonResponse {
		String message;
	}

	/**
	 * The General Rest Exception Handling point.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionJsonResponse> handleGeneralException(Exception e) {
		return new ResponseEntity<ExceptionJsonResponse>(new ExceptionJsonResponse(e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * The UserDefinedException handling point.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = UserDefinedSystemException.class)
	public ResponseEntity<ExceptionJsonResponse> handleException(Exception e) {
		return new ResponseEntity<ExceptionJsonResponse>(new ExceptionJsonResponse(e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
