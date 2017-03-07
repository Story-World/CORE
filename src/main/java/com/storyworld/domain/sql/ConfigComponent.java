package com.storyworld.domain.sql;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.storyworld.enums.ConfigComponentType;

@Entity
@Table(name= "CONFIG_COMPONENT")
public class ConfigComponent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3762014167430699321L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	@NotNull
	private User user;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private ConfigComponentType type;
	
	@Type(type="text")
	@NotNull
	private String value;

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ConfigComponentType getType() {
		return type;
	}

	public void setType(ConfigComponentType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
