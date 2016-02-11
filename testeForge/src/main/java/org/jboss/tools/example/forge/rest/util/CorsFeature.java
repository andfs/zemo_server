package org.jboss.tools.example.forge.rest.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@Provider
public class CorsFeature implements Feature{

    public boolean configure(FeatureContext context) {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        context.register(corsFilter);
        return true;
    }

	public void filter(ContainerRequestContext arg0) throws IOException {}  
}
