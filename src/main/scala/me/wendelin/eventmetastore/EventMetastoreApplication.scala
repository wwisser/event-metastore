package me.wendelin.eventmetastore

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class EventMetastoreApplication {
}

object EventMetastoreApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[EventMetastoreApplication])
  }
}
