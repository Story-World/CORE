package com.storyworld.domain.sql;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.storyworld.domain.sql.basic.BasicWithNameEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLE")
public class Role extends BasicWithNameEntity {

}
