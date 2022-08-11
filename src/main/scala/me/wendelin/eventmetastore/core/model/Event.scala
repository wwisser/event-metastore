package me.wendelin.eventmetastore.core.model

import java.time.LocalDateTime
import scala.beans.BeanProperty

case class Event(@BeanProperty dateTime: LocalDateTime, @BeanProperty location: String, @BeanProperty kind: String, @BeanProperty description: String)
