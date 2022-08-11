package me.wendelin.eventmetastore.infra

import me.wendelin.eventmetastore.core.Event
import org.springframework.data.jpa.repository.JpaRepository

trait EventRepository extends JpaRepository[Event, Int]