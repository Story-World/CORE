package com.storyworld.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.storyworld.domain.sql.User;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		StringWriter writer = new StringWriter();
		joinStackTrace(ex, writer);
		System.out.println(writer.toString());
		HttpStatus status = HttpStatus.OK;
		User user = new User();
		user.setName("test");
		return new ResponseEntity<>(user, status);
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
