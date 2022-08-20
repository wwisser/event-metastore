package me.wendelin.eventmetastore.infra.service

import me.wendelin.eventmetastore.core.model.{Event, EventQuery}
import me.wendelin.eventmetastore.core.service.EventService
import me.wendelin.eventmetastore.infra.repository.EventRepository
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class EventServiceImpl(val repository: EventRepository) extends EventService {
  override def save(event: Event): Unit = repository.save(event)

  override def delete(id: Int): Unit = repository.deleteById(id)

  override def queryAll(): List[Event] = repository.findByOrderByDateTimeAsc().asScala.toList

  override def query(query: EventQuery): List[Event] = ???
}
