package com.radish.chotto

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
        @Autowired private val loginInterceptor: LoginInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/login")
            .excludePathPatterns("/static/**")
    }
}
