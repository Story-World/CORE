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
		StringWriter writer = new StringWriter();
		joinStackTrace(ex, writer);
		LOG.error(writer.toString());
		return new ResponseEntity<>("status", HttpStatus.OK);
	}

	private static void joinStackTrace(Throwable e, StringWriter writer) {
		PrintWriter printer = null;
		try {
			printer = new PrintWriter(writer);

			while (e != null) {

				printer.println(e);
				StackTraceElement[] trace = e.getStackTrace();
				for (int i = 0; i < trace.length; i++)
					printer.println("\tat " + trace[i]);

				e = e.getCause();
				if (e != null)
					printer.println("Caused by:\r\n");
			}
		} finally {
			if (printer != null)
				printer.close();
		}
	}
}