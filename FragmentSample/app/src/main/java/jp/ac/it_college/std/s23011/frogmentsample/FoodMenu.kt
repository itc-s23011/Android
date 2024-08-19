package jp.ac.it_college.std.s23011.frogmentsample

import kotlinx.serialization.Serializable

@Serializable
data class FoodMenu(
    val id: Long,
    val name: String,
    val price: Int,
    val desc: String,
    )
