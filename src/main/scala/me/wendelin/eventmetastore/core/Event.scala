package me.wendelin.eventmetastore.core

import java.time.LocalDateTime
import javax.persistence.{Entity, GeneratedValue, Id, Table}
import scala.beans.BeanProperty

// @TODO architecture: JPA entity should be moved into infra, core model should be encapsulated
@Entity
@Table(name = "events")
class Event(
             @BeanProperty var dateTime: LocalDateTime,
             @BeanProperty var location: String,
             @BeanProperty var kind: String,
             @BeanProperty var description: String
           ) {
  @Id
  @GeneratedValue
  @BeanProperty var id: Int = _

  def this() = this(null, null, null, null)
}
