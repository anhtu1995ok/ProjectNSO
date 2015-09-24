package com.android.nso.model;

import java.io.Serializable;
import java.util.ArrayList;

public class News implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, description, content, url_image, created_at, state;
	
	public News(){
		
	}

	public News(int id, String name, String description, String content, String url_image, String created_at,
			String state) {
		this.name = name;
		this.description = description;
		this.content = content;
		this.url_image = url_image;
		this.created_at = created_at;
		this.state = state;
	}

	// getData from Server
	public ArrayList<News> getData(ArrayList<News> data) {
		
		int max = 10;
		for (int i = 0; i < max; i++) {
			News news = new News(i, "Tin moi thu : " + i, "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription " + i, " content " + i, "", "9:00 AM", "");
			data.add(news);
		}
		
		return data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
