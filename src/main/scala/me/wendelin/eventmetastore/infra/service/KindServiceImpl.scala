package me.wendelin.eventmetastore.infra.service

import me.wendelin.eventmetastore.core.model.Kind
import me.wendelin.eventmetastore.core.service.KindService
import me.wendelin.eventmetastore.infra.repository.KindRepository
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class KindServiceImpl(val repository: KindRepository) extends KindService {
  override def save(kind: Kind): Unit = repository.save(kind)

  override def delete(id: Int): Unit = repository.deleteById(id)

  override def queryAll(): List[Kind] = repository.findAll().asScala.toList
}
