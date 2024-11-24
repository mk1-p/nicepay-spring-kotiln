package io.mk1.nicepay.payments

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PayInfoDto(
    val goodsName: String,
    val amt: Long,
    val moid: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val buyerName: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val buyerEmail: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val buyerTel: String? = null,
    val merchantID: String,
    val ediDate: String,
    val signData: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val returnUrl: String? = null,
) {
}
