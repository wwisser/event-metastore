package me.wendelin.eventmetastore.core.model

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.LocalDateTime
import javax.persistence.{Entity, GeneratedValue, Id, Table}
import scala.beans.BeanProperty

// @TODO architecture: JPA entity and DTO should be moved into infra/presentation, core model should be encapsulated
@Entity
@Table(name = "events")
class Event(
             @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
             @BeanProperty var dateTime: LocalDateTime,
             @BeanProperty var locationId: Int,
             @BeanProperty var kindId: Int,
             @BeanProperty var description: String
           ) {
  @Id
  @GeneratedValue
  @BeanProperty var id: Int = _

  def this() = this(null, -1, -1, null)
}
