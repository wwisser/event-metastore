package me.wendelin.eventmetastore.presentation

import me.wendelin.eventmetastore.core.model.Event
import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

import java.time.LocalDateTime

@RestController
class EventController {

  @GetMapping(value = Array("/events"))
  def getEvents: Event = {
    Event(LocalDateTime.now(), "Europapark", "Gästeunterhaltung", "Solo")
  }

}
