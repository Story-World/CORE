package com.storyworld.domain.json;

import com.storyworld.enums.FavouritePlacesType;

public class FavouritePlaces {

	private FavouritePlacesType type;
	
	private String facet;
	
	private String name;
	
	private String value;
	
	public FavouritePlaces(){
	}
	
	public FavouritePlaces(FavouritePlacesType type, String facet, String name, String value) {
		super();
		this.type = type;
		this.facet = facet;
		this.name = name;
		this.value = value;
	}

	public FavouritePlacesType getType() {
		return type;
	}

	public void setType(FavouritePlacesType type) {
		this.type = type;
	}

	public String getFacet() {
		return facet;
	}

	public void setFacet(String facet) {
		this.facet = facet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
