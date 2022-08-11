package me.wendelin.eventmetastore.infra

import me.wendelin.eventmetastore.core.{Event, EventQuery, EventService}
import org.springframework.stereotype.Service
import scala.collection.JavaConverters._

@Service
class EventServiceImpl(val repository: EventRepository) extends EventService {

  override def save(event: Event): Unit = {
    repository.save(event)
  }

  override def delete(id: Int): Unit = {
    repository.deleteById(id)
  }

  override def queryAll(): List[Event] = repository.findAll().asScala.toList

  override def query(query: EventQuery): List[Event] = ???
}
