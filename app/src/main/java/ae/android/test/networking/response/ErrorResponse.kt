package ae.android.test.networking.response

import ae.android.test.networking.enums.ErrorType
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponse(
    val errorType: ErrorType = ErrorType.GENERAL_ERROR,
    @JsonProperty("code")
    @JsonAlias("status_code")
    var statusCode: Int = 0,
    @JsonProperty("msg")
    @JsonAlias("message")
    var message: String? = "",
    @JsonProperty("data")
    var data: Any? = null
)