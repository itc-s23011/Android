package jp.ac.it_college.std.s23011.asynccoroutinesample

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("coord") val coordinates: Coordinates,
    val weather: List<Weather>,
    @SerialName("name") val cityName: String,
)

@Serializable
data class Coordinates(
    @SerialName("lon") val longitube: Double,
    @SerialName("lat") val latitube: Double
)

@Serializable
data class Weather(
    val descriptor: String
)