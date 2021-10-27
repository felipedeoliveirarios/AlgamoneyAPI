package com.feliperios.algamoneyapi.event.listener;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCreationListener implements ApplicationListener<ResourceCreationEvent> {
	@Override
	public void onApplicationEvent(ResourceCreationEvent event) {
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();

		addHeaderLocation(response, id);
	}

	private void addHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}
}
