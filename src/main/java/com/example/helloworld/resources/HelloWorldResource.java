package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.api.Saying;
import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

import com.wordnik.swagger.annotations.*;

/**
 * Created by Alison on 24/01/15
 */
@Path("/hello-swagger")
@Api(value = "/hello-swagger", description = "Say hello to the given name or Swagger by default")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        counter = new AtomicLong();
    }

    @GET
    @Timed
    @ApiOperation(value = "Say hello to the name", notes = "More notes about this method", response = Saying.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid name supplied"),
            @ApiResponse(code = 404, message = "Template not found")
    })
    public Saying sayHello(@ApiParam(value = "Name to say hello to", required = false, defaultValue = "Stranger") @QueryParam("name") Optional<String> name){
        String content = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), content);
    }
}
