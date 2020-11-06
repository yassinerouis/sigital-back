package org.sid.sigital.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString 
@Entity
public class Role implements Serializable{
		private static final long serialVersionUID = 1L;
		@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long Id;
		String name;
}