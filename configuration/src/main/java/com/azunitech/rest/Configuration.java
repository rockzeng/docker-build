
package com.azunitech.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.azunitech.rest.domain.MemcachedConfig;
import com.azunitech.rest.domain.Result;

@Path("/v1/key")
public class Configuration {

    @GET 
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result getIt( @PathParam(value = "key") String key) throws IOException {
    	
    	
    	MemcachedConfig config = new MemcachedConfig();
    	String url = config.getUrl();
        return new Result();
    }
}
