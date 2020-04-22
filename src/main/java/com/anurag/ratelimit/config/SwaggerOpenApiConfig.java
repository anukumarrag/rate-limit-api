package com.anurag.ratelimit.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;



@Configuration
public class SwaggerOpenApiConfig{
	
	 @Bean
	    public OpenAPI customOpenAPI(@Value("${build.version}") String appVersion) {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Demo Application For Rate Limit Test")
	                        .version(appVersion)
	                        .description("This is a demo web application which is used for rate limit test")
	                        .contact(new Contact().name("Anurag Kumar").email("anukumarrag@gmail.com"))
	                        .termsOfService("http://swagger.io/terms/")
	                        .license(new License().name("Anurag Kumar")));
	    }


	@Component("apiConfig")
    public static class OpenApiConfig implements OpenApiCustomiser {

        private static final List<Function<PathItem, Operation>> OPERATION_GETTERS = Arrays.asList(
                PathItem::getGet, PathItem::getPost, PathItem::getDelete, PathItem::getHead,
                PathItem::getOptions, PathItem::getPatch, PathItem::getPut);

        private Stream<Operation> getOperations(PathItem pathItem) {
            return OPERATION_GETTERS.stream()
                    .map(getter -> getter.apply(pathItem))
                    .filter(Objects::nonNull);
        }

        @Override
        public void customise(OpenAPI openApi) {
            openApi.getPaths().values().stream()
                    .flatMap(this::getOperations)
                    .forEach(this::customize);
        }

        private void customize(Operation operation) {
            operation.addParametersItem(
                    new Parameter()
                            .in("header")
                            .required(true)
                            .description("Access-Token")
                            .name("Access-Token")
                            );
        }
    }
	
	
	
}
