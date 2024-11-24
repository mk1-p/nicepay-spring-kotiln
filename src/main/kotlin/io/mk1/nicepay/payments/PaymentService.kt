package io.mk1.nicepay.payments

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal.isError
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service()
class PaymentService {

    private final val logger = LoggerFactory.getLogger(PaymentService::class.java)
    private final val mid: String = "nicepay00m"
    private final val merchantKey: String = "EYzu8jGGMfqaDEp76gSckuvnaHHu+bC4opsSN6lHv3b2lurNYkVXrZ7Z1AoqQnXI3eLuaUFyoRNC6FkrzVjceg=="

    /**
     * 결제 페이지 호출 정보 전달.
     */
    fun getInfo(): PayInfoDto {
        // TODO 샘플코드 상 하드코딩이지만, 실제 결제 프로세스를 만들때는 데이터베이스의 상품 정보를 호출하여 만들 것.
        // 결제 페이지 요청을 위한 정보 세팅
        val now = LocalDateTime.now()
        val ediDate = getFormattedEdiDate(now)
        val amt: Long = 1000
        val moid = "test-10001"

        val signData = createSignData("$ediDate$mid$amt$merchantKey")

        return PayInfoDto(
            goodsName = "Test Goods",
            amt = amt,
            moid = moid,
            buyerName = "Test Buyer",
            merchantID = mid,
            ediDate = ediDate,
            signData = signData,
        )

    }

    fun approvePayAuth(payAuthDto: PayAuthDto): Unit {
        val now = LocalDateTime.now()
        val ediDate = getFormattedEdiDate(now)
        val tid = payAuthDto.txTid
        val authToken = payAuthDto.authToken
        val mid = payAuthDto.mid
        val amt = payAuthDto.amt
        val signData = createSignData("$authToken$mid$amt$ediDate$merchantKey")

        val reqDto = PayApprovalReqDto(
            tid = tid,
            authToken = authToken,
            mid = mid,
            amt = amt,
            ediDate = ediDate,
            signData = signData,
        )

        var url: String? = null

        // 승인 세팅
        if (payAuthDto.authResultCode == "0000") {
            url = payAuthDto.nextAppURL
        }
        // 망취소 세팅
        else {
            url = payAuthDto.netCancelURL
            reqDto.setCancelState()
        }


        val webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()

        val res = webClient
            .post()
            .body(BodyInserters.fromFormData(reqDto.toFormData()))
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response ->
                response.bodyToMono(String::class.java).flatMap { errorBody ->
                    logger.error("Error response: $errorBody")
                    Mono.error(RuntimeException("Server returned an error response"))
                }
            }
            .bodyToMono<String>()
            .block()

        logger.info("결제 결과값 : ${res.toString()}")

    }


    private fun createSignData(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun getFormattedEdiDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return date.format(formatter)
    }


}
