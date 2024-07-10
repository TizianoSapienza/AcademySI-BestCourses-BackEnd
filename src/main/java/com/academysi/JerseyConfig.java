package com.academysi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig{

	//Costruttore
		public JerseyConfig() {
			packages("com.academysi");
		}
}
