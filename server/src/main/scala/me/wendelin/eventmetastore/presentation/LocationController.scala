package me.wendelin.eventmetastore.presentation

import me.wendelin.eventmetastore.core.model.Location
import me.wendelin.eventmetastore.core.service.LocationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, PathVariable, PostMapping, RequestBody, RequestMapping, ResponseStatus, RestController}


@RestController
@RequestMapping(path = Array("/locations"))
class LocationController(locationService: LocationService) {

  @GetMapping
  def query: List[Location] = locationService.queryAll()

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  def post(@RequestBody location: Location): Location = {
    locationService.save(location)
    location
  }

  @DeleteMapping(value = Array("/{id}"))
  def delete(@PathVariable id: Int): Unit = locationService.delete(id)

}
