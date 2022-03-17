package ae.android.test.networking.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MostPopularResponse(
    @JsonProperty("title")
    val title: String,

    @JsonProperty("abstract")
    val _abstract: String,

    @JsonProperty("published_date")
    val publishedDate: String?,

    @JsonProperty("source")
    val source: String
)
