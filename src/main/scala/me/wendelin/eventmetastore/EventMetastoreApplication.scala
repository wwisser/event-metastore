package me.wendelin.eventmetastore

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class EventMetastoreApplication

object EventMetastoreApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[EventMetastoreApplication])
  }
}
