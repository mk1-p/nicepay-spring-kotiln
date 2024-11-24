package io.mk1.nicepay.common

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class FormDataArgumentResolver(private val objectMapper: ObjectMapper) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(FormData::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: org.springframework.web.bind.support.WebDataBinderFactory?
    ): Any {
        val servletRequest = webRequest.nativeRequest as HttpServletRequest
        val formData = servletRequest.parameterMap.mapValues { it.value.joinToString(",") }
        val targetClass = parameter.parameterType
        return objectMapper.convertValue(formData, targetClass)
    }
}

