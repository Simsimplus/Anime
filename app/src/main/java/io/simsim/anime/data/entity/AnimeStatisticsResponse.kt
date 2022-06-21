package io.simsim.anime.data.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeStatisticsResponse(
    @Json(name = "data") var animeStatisticsData: AnimeStatisticsData = AnimeStatisticsData()
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class AnimeStatisticsData(
        @Json(name = "completed") var completed: Int = 0,
        @Json(name = "dropped") var dropped: Int = 0,
        @Json(name = "on_hold") var onHold: Int = 0,
        @Json(name = "plan_to_watch") var planToWatch: Int = 0,
        @Json(name = "scores") var scores: List<Score> = listOf(),
        @Json(name = "total") var total: Int = 0,
        @Json(name = "watching") var watching: Int = 0
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Score(
            @Json(name = "percentage") var percentage: Double = 0.0,
            @Json(name = "score") var score: Int = 0,
            @Json(name = "votes") var votes: Int = 0
        )
    }
}

fun AnimeStatisticsResponse.AnimeStatisticsData.getScoreMap() =
    (1..5).associateWith { 0f }.mapValues { (score, _) ->
        scores.filter { it.score in score..(score * 2) }.sumOf { it.percentage }.toFloat()
    }
