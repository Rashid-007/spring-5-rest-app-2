package throne.springreacto.spring5restapp2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket apiDoc(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        Contact contact = new Contact("Enayetullah Rasuli", "https://self.blog.com", "e.ras@gmail.com");
        return new ApiInfo(
                "Advanced Spring Training",
                "Spring Framework 5",
                "1.0",
                "Terms of Service: TODO",
                contact,
                "Apache License 2",
                "https://www.apache.org",
                new ArrayList<>()

        );
    }

    /**
     * Resource handler in case swagger ui is not available. Usually Spring Boot takes care of this.
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        //for styling
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
