package ae.android.test.networking.response

import ae.android.test.networking.enums.ErrorType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val errorType: ErrorType = ErrorType.GENERAL_ERROR,
    @Expose
    @SerializedName("code", alternate = ["status_code"])
    var statusCode: Int = 0,
    @SerializedName("msg", alternate = ["message"])
    @Expose
    var message: String? = "",
    @SerializedName("data")
    @Expose
    var data: Any?=null
)