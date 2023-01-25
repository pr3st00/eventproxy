package br.com.fernandoalmeida.eventproxy.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.fernandoalmeida.eventproxy.caller.Status;
import br.com.fernandoalmeida.eventproxy.caller.Status.Result;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Status> defaultHandler(Exception e) {
		Status status = Status.builder().result(Result.FAILED).message(e.getLocalizedMessage()).build();

		return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
