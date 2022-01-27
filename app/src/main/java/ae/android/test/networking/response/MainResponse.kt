package ae.android.test.networking.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MainResponse(


    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("copyright")
    @Expose
    private val copyright: String,

    @SerializedName("num_results")
    @Expose
    private val numResults: String,

    @SerializedName("results")
    @Expose
    val results: MutableList<MostPopularResponse>

)