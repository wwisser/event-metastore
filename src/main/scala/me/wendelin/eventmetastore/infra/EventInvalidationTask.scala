package me.wendelin.eventmetastore.infra

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EventInvalidationTask {

  @Scheduled(fixedDelay = 1000 * 5)
  def invalidateEvents(): Unit = {
    // @TODO invalidate events
  }

}
