package me.wendelin.eventmetastore

import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

@RestController
class TestController {

  @GetMapping(value = Array("/destination"))
  def destination(@RequestParam name: String): String = {
    "Dear %s, your test was successful.".format(name)
  }

}
