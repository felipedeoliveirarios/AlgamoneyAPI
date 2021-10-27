package com.feliperios.algamoneyapi.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreationEvent extends ApplicationEvent {

	private HttpServletResponse response;
	private Long id;

	public ResourceCreationEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getId() {
		return id;
	}
}
