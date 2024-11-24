package io.mk1.nicepay

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping("/")
    fun index(): String {
        return "payment-request"
    }

}
