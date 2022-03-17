package ae.android.test.networking.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class MainResponse(


    @JsonProperty("status")
    val status: String,

    @JsonProperty("copyright")
    private val copyright: String,

    @JsonProperty("num_results")
    private val numResults: String,

    @JsonProperty("results")
    val results: MutableList<MostPopularResponse>

)