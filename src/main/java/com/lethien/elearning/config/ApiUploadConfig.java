/**
 * Dec 29, 2020
 * 5:16:07 PM
 * @author LeThien
 */
package com.lethien.elearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ApiUploadConfig implements WebMvcConfigurer{
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}
}
