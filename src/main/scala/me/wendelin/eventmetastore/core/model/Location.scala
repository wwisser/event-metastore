package me.wendelin.eventmetastore.core.model

import javax.persistence.{Entity, GeneratedValue, Id, Table}
import scala.beans.BeanProperty

// @TODO architecture: JPA entity should be moved into infra, core model should be encapsulated
@Entity
@Table(name = "locations")
class Location(@BeanProperty var name: String) {

  @Id
  @GeneratedValue
  @BeanProperty var id: Int = _

  def this() = this(null)
}
