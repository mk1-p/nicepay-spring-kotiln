package io.mk1.nicepay.payments

import io.mk1.nicepay.common.FormData
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/payments")
class PaymentController(private val paymentService: PaymentService) {

    private val logger = LoggerFactory.getLogger(PaymentController::class.java)


    @GetMapping("/info")
    fun getPayInfo(): ResponseEntity<PayInfoDto> {
        val info = paymentService.getInfo()
        return ResponseEntity.ok().body(info)
    }

    @PostMapping("/authReq")
    fun requestAuth(@FormData payAuthDto: PayAuthDto): ResponseEntity<String> {
        logger.info("Pay auth request")
        paymentService.approvePayAuth(payAuthDto)
        return ResponseEntity.noContent().build()
    }

}
