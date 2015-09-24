package com.android.nso.model;

import java.io.Serializable;

public class Trading implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, region, created_by, target, type, content, url_image, created_at, expired_at, state, censored;

	public Trading(int id, String name, String region, String created_by, String target, String type, String content,
			String url_image, String created_at, String expired_at, String state, String censored) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.created_by = created_by;
		this.target = target;
		this.type = type;
		this.content = content;
		this.url_image = url_image;
		this.created_at = created_at;
		this.expired_at = expired_at;
		this.state = state;
		this.censored = censored;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getExpired_at() {
		return expired_at;
	}

	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCensored() {
		return censored;
	}

	public void setCensored(String censored) {
		this.censored = censored;
	}

}
