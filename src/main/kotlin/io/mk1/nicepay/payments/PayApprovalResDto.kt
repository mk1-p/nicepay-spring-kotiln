package io.mk1.nicepay.payments

import com.fasterxml.jackson.annotation.JsonProperty

data class PayApprovalResDto(
    @JsonProperty("ResultCode")
    val resultCode: String,
    @JsonProperty("ResultMsg")
    val resultMsg: String,
    @JsonProperty("MID")
    val mid: String,
    @JsonProperty("Moid")
    val moid: String,
    @JsonProperty("Signature")
    val signature: String,
) {

    override fun toString(): String {
        return "$resultCode : $resultMsg"
    }
}
