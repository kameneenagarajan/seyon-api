package io.seyon.invoice.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class SeyonInvoiceConfig {

	@Autowired
	ResourceLoader resourceLoader;
	
	@Bean
	public TemplateEngine templateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();  
		templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
	}
	
	private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());;
        return templateResolver;
    }
	
}
