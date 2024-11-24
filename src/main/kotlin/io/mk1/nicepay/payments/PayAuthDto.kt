package io.mk1.nicepay.payments

import com.fasterxml.jackson.annotation.JsonProperty

data class PayAuthDto(
    @JsonProperty("AuthResultCode")
    val authResultCode: String,
    @JsonProperty("AuthResultMsg")
    val authResultMsg: String,
    @JsonProperty("AuthToken")
    val authToken: String,
    @JsonProperty("PayMethod")
    val payMethod: String,
    @JsonProperty("MID")
    val mid: String,
    @JsonProperty("Moid")
    val moid: String,
    @JsonProperty("Signature")
    val signature: String,
    @JsonProperty("Amt")
    val amt: Long,
    @JsonProperty("ReqReserved")
    val reqReserved: String,
    @JsonProperty("TxTid")
    val txTid: String,
    @JsonProperty("NextAppURL")
    val nextAppURL: String,
    @JsonProperty("NetCancelURL")
    val netCancelURL: String,
) {
}
