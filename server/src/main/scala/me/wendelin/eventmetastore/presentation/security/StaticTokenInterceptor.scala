package me.wendelin.eventmetastore.presentation.security

import org.springframework.core.annotation.Order
import org.springframework.http.{HttpHeaders, HttpMethod, HttpStatus}
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

@Order(1)
class StaticTokenInterceptor(val token: String) extends HandlerInterceptor {

  override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean = {
    println(token)
    if (Set(HttpMethod.GET.name(), HttpMethod.OPTIONS.name()).contains(request.getMethod)) {
      return true
    }

    val header = request.getHeader(HttpHeaders.AUTHORIZATION)
    if (null == header || header != token) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value())
      return false
    }

    true
  }

}
