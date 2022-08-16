package me.wendelin.eventmetastore

import me.wendelin.eventmetastore.presentation.security.StaticTokenInterceptor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.{CorsRegistry, InterceptorRegistry, WebMvcConfigurer}
import org.springframework.beans.factory.annotation.Value

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
class EventMetastoreApplication {

  @Bean
  def corsConfigurer(): WebMvcConfigurer = {
    new WebMvcConfigurer() {
      override def addCorsMappings(registry: CorsRegistry): Unit = {
        registry.addMapping("/**")
          .allowedOrigins("*")
          .allowedHeaders("*")
          .allowedMethods("*")
      }
    }
  }

  @Bean
  def interceptorConfigurer(): WebMvcConfigurer = {
    new WebMvcConfigurer() {
      @Value("${security.token}") private val token: String = null

      override def addInterceptors(registry: InterceptorRegistry): Unit = {
        registry.addInterceptor(new StaticTokenInterceptor(token)).addPathPatterns("/**")
      }
    }
  }

  @Bean
  def objectMapper: ObjectMapper = {
    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.registerModule(new JavaTimeModule)
    mapper
  }

}

object EventMetastoreApplication extends App {
  SpringApplication.run(classOf[EventMetastoreApplication])
}
