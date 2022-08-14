package me.wendelin.eventmetastore.infra.repository

import me.wendelin.eventmetastore.core.model.Location
import org.springframework.data.jpa.repository.JpaRepository

trait LocationRepository extends JpaRepository[Location, Int]
