package me.wendelin.eventmetastore.infra.repository

import me.wendelin.eventmetastore.core.model.Event
import org.springframework.data.jpa.repository.JpaRepository

trait EventRepository extends JpaRepository[Event, Int] {

  def findByOrderByDateTimeAsc(): java.util.List[Event]

}
