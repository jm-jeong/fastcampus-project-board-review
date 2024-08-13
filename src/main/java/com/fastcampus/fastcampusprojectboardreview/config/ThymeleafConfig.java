package com.fastcampus.fastcampusprojectboardreview.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
public class ThymeleafConfig {

	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver(
		SpringResourceTemplateResolver defaultTemplateResolver,
		Thymeleaf3Properties thymeleaf3Properties
	) {
		defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

		return defaultTemplateResolver;
	}


	@RequiredArgsConstructor
	@Getter
	@ConfigurationProperties("spring.thymeleaf3")
	public static class Thymeleaf3Properties {
		/**
		 * Use Thymeleaf 3 Decoupled Logic
		 */
		private final boolean decoupledLogic;
	}

}
