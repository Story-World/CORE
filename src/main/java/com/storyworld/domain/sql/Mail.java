package com.storyworld.domain.sql;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.storyworld.domain.sql.basic.BasicEntity;
import com.storyworld.domain.sql.enums.Status;
import com.storyworld.domain.sql.enums.TypeToken;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "MAIL")
public class Mail extends BasicEntity {

	@Enumerated(EnumType.STRING)
	private TypeToken template;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime sent;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public Mail(TypeToken template, Status status, User user) {
		super();
		this.template = template;
		this.status = status;
		this.user = user;
	}

}
