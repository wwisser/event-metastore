package me.wendelin.eventmetastore.presentation.security

import org.springframework.core.annotation.Order
import org.springframework.http.{HttpHeaders, HttpMethod, HttpStatus}
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

@Component
@Order(1)
class StaticTokenInterceptor extends HandlerInterceptor {

  val HeaderValue: String = "Ä7Üym!z!VbÖ2&iLÄ" // @TODO make configurable

  override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean = {
    if (Set(HttpMethod.GET.name(), HttpMethod.OPTIONS.name()).contains(request.getMethod)) {
      return true
    }

    val header = request.getHeader(HttpHeaders.AUTHORIZATION)
    if (null == header || header != HeaderValue) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value())
      return false
    }

    true
  }

}
