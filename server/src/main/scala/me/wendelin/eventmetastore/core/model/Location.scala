package me.wendelin.eventmetastore.core.model

import javax.persistence.{Entity, GeneratedValue, Id, Table}
import javax.validation.constraints.NotNull
import scala.beans.BeanProperty

// @TODO architecture: JPA entity and DTO should be moved into infra/presentation, core model should be encapsulated
@Entity
@Table(name = "locations")
class Location(@NotNull @BeanProperty var name: String) {

  @Id
  @GeneratedValue
  @BeanProperty var id: Int = _

  def this() = this(null)
}
