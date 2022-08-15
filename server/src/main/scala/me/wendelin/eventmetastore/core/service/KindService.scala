package me.wendelin.eventmetastore.core.service

import me.wendelin.eventmetastore.core.model.Kind

trait KindService {

  def save(kind: Kind): Unit

  def delete(id: Int): Unit

  def queryAll(): List[Kind]

}
