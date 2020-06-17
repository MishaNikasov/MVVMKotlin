package com.my.project.firstkotlin.data.remote.data.response

data class Ingredient(
    val aisle: String,
    val amount: Double,
    val consistency: String,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val metaInformation: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val originalString: String,
    val unit: String
) {
    data class Measures(
        val metric: Metric,
        val us: Us
    )

    data class Metric(
        val amount: Double,
        val unitLong: String,
        val unitShort: String
    )

    data class Us(
        val amount: Double,
        val unitLong: String,
        val unitShort: String
    )
}

