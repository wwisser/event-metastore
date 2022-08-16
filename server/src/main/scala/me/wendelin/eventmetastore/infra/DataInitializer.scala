package me.wendelin.eventmetastore.infra

import me.wendelin.eventmetastore.core.model.{Event, Kind, Location}
import me.wendelin.eventmetastore.infra.repository.{EventRepository, KindRepository, LocationRepository}
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Component
class DataInitializer(
                       val kindRepository: KindRepository,
                       val locationRepository: LocationRepository,
                       val eventRepository: EventRepository
                     ) {

  @PostConstruct
  def initialize(): Unit = {
    kindRepository.save(new Kind("Entertainment"))
    locationRepository.save(new Location("Munich"))
    eventRepository.save(new Event(LocalDateTime.now(), 1, 1, "Duo"))
  }

}
