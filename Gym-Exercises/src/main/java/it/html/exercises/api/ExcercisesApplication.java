package it.html.exercises.api;

import java.io.IOException;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ExcercisesApplication<E> extends ResourceConfig {
    public ExcercisesApplication() {
        packages("it.html.exercises.api");
    }
    public void filter(ContainerRequestContext requestContext)
    		throws IOException {
	}
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
    		throws IOException {
	}
    Response toResponse(E exception) {
		return null;
	}
}