package jp.ac.it_college.std.s23011.menusample

@Serializable
data class FoodMenu(
    val id: Long,
    val name: String,
    val price: Int,
    val desc: String,
)