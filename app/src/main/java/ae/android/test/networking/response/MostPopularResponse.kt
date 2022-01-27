package ae.android.test.networking.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MostPopularResponse(
    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("abstract")
    @Expose
    val _abstract: String,

    @SerializedName("published_date")
    @Expose
    val publishedDate: String?,

    @SerializedName("source")
    @Expose
    val source: String
)
