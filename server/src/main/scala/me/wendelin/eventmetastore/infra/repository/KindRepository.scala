package me.wendelin.eventmetastore.infra.repository

import me.wendelin.eventmetastore.core.model.Kind
import org.springframework.data.jpa.repository.JpaRepository

trait KindRepository extends JpaRepository[Kind, Int]
