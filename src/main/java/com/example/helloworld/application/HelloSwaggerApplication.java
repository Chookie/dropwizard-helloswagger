package com.example.helloworld.application;

import com.example.helloworld.configuration.HelloWorldConfiguration;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.wordnik.swagger.jaxrs.config.*;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.config.*;
import com.wordnik.swagger.reader.*;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by Alison on 24/01/15
 */
public class HelloSwaggerApplication extends Application<HelloWorldConfiguration> {

    //final static Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(String[] args) throws Exception {
        new HelloSwaggerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {

    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

        environment.jersey().register(new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName()));

        configureCors(environment);
        configureSwagger(environment);
    }

    void configureSwagger(Environment environment) {
        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("0.0.1");
        config.setBasePath(environment.getApplicationContext().getContextPath());
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
