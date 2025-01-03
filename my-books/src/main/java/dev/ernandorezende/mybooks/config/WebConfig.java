package dev.ernandorezende.mybooks.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false).defaultContentType(MediaType.APPLICATION_GRAPHQL_RESPONSE)
                .mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML);
    }
}
