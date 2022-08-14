package me.wendelin.eventmetastore.presentation

import me.wendelin.eventmetastore.core.model.Kind
import me.wendelin.eventmetastore.core.service.KindService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, PathVariable, PostMapping, RequestBody, RequestMapping, ResponseStatus, RestController}

@RestController
@RequestMapping(path = Array("/kinds"))
class KindController(kindService: KindService) {

  @GetMapping
  def query: List[Kind] = kindService.queryAll()

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  def post(@RequestBody kind: Kind): Kind = {
    kindService.save(kind)
    kind
  }

  @DeleteMapping(value = Array("/{id}"))
  def delete(@PathVariable id: Int): Unit = kindService.delete(id)

}
