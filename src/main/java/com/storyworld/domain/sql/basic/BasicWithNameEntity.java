package com.storyworld.domain.sql.basic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BasicWithNameEntity extends BasicEntity {

	@NotNull
	@Column(unique = true)
	@Length(min = 4, max = 255)
	protected String name;

	public BasicWithNameEntity(Long id, String name) {
		super(id);
		this.name = name;
	}

	public BasicWithNameEntity(String name) {
		this.name = name;
	}

}
