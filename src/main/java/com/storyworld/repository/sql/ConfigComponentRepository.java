package com.storyworld.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.ConfigComponent;

public interface ConfigComponentRepository extends JpaRepository<ConfigComponent, Long> {

	public ConfigComponent findByUserId(long id);

}
