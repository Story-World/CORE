package com.storyworld.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler
	public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		ex.printStackTrace(pw);
		LOG.error(sw.getBuffer().toString());
		return new ResponseEntity<>("status", HttpStatus.OK);
	}

}