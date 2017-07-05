package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	private static final long serialVersionUID = -6056699078253966279L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String mail;

	private String token;

	private LocalDateTime lastActionTime;

	private int incorrectLogin;

	private boolean block;

	private LocalDateTime lastIncorrectLogin;

	private boolean deleted;

}
