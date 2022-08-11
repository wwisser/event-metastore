package me.wendelin.eventmetastore.core

trait EventService {

  def save(event: Event): Unit

  def delete(id: Int): Unit

  def queryAll(): List[Event]

  def query(query: EventQuery): List[Event]

}
