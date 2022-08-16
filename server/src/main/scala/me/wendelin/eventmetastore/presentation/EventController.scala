package me.wendelin.eventmetastore.presentation

import me.wendelin.eventmetastore.core.model.Event
import me.wendelin.eventmetastore.core.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, PathVariable, PostMapping, RequestBody, RequestMapping, ResponseStatus, RestController}

@RestController
@RequestMapping(path = Array("/events"))
class EventController(eventService: EventService) {

  @GetMapping
  def query: List[Event] = eventService.queryAll()

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  def post(@RequestBody event: Event): Event = {
    Thread.sleep(1000) // @TODO remove
    eventService.save(event)
    event
  }

  @DeleteMapping(value = Array("/{id}"))
  def delete(@PathVariable id: Int): Unit = eventService.delete(id)

}
