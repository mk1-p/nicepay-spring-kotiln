package io.mk1.nicepay.payments

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class PayApprovalReqDto(
    @JsonProperty("TID")
    val tid: String,
    @JsonProperty("AuthToken")
    val authToken: String,
    @JsonProperty("MID")
    val mid: String,
    @JsonProperty("Amt")
    val amt: Long,
    @JsonProperty("EdiDate")
    val ediDate: String,
    @JsonProperty("SignData")
    val signData: String,
    @JsonProperty("CharSet")
    val charSet: String? = "euc-kr",
    @JsonProperty("NetCancel")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var netCancel: String? = null,
) {

    fun setCancelState() {
        this.netCancel = "1"
    }

    fun toFormData(): LinkedMultiValueMap<String, String> {
        val formData = LinkedMultiValueMap<String, String>()

        formData.add("TID", tid)
        formData.add("AuthToken", authToken)
        formData.add("MID", mid)
        formData.add("Amt", amt.toString())
        formData.add("EdiDate", ediDate)
        formData.add("SignData", signData)
        formData.add("CharSet", charSet)
        this.netCancel?.let {
            formData.add("NetCancel", it)
        }
        return formData
    }
}
