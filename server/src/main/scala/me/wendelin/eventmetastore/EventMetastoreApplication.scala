package me.wendelin.eventmetastore

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
class EventMetastoreApplication

object EventMetastoreApplication extends App {
  SpringApplication.run(classOf[EventMetastoreApplication])
}
