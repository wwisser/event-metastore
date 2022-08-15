package me.wendelin.eventmetastore.core.service

import me.wendelin.eventmetastore.core.model.Location

trait LocationService {

  def save(location: Location): Unit

  def delete(id: Int): Unit

  def queryAll(): List[Location]

}
