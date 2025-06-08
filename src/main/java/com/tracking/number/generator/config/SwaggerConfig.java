package com.tracking.number.generator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tracking.number.generator.utils.ConfigConstant.SWAGGER_DESCRIPTION;
import static com.tracking.number.generator.utils.ConfigConstant.SWAGGER_TITLE;
import static com.tracking.number.generator.utils.ConfigConstant.SWAGGER_VERSION;

/** The type Swagger config. */
@Configuration
public class SwaggerConfig {

    /**
     * Custom open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(SWAGGER_TITLE)
                                .version(SWAGGER_VERSION)
                                .description(SWAGGER_DESCRIPTION));
    }
}

