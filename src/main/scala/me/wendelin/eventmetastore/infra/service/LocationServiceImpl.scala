package me.wendelin.eventmetastore.infra.service

import me.wendelin.eventmetastore.core.model.Location
import me.wendelin.eventmetastore.core.service.LocationService
import me.wendelin.eventmetastore.infra.repository.LocationRepository

import scala.collection.JavaConverters._

class LocationServiceImpl(val repository: LocationRepository) extends LocationService {

  override def save(location: Location): Unit = repository.save(location)

  override def delete(id: Int): Unit = repository.deleteById(id)

  override def queryAll(): List[Location] = repository.findAll().asScala.toList

}
