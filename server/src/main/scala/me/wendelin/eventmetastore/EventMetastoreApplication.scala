package me.wendelin.eventmetastore

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry

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

}

object EventMetastoreApplication extends App {
  SpringApplication.run(classOf[EventMetastoreApplication])
}
