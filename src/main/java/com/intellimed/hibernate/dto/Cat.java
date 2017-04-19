package com.intellimed.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAT_TYPE")
public class Cat extends Animal {
	private String hairColor;

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}
	

}
