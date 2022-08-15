package me.wendelin.eventmetastore.core.service

import me.wendelin.eventmetastore.core.model.{Event, EventQuery}

trait EventService {

  def save(event: Event): Unit

  def delete(id: Int): Unit

  def queryAll(): List[Event]

  def query(query: EventQuery): List[Event]

}
