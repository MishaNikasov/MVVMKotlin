package com.my.project.firstkotlin.data.remote.data.response

data class Instruction(
    val name: String,
    val steps: List<Step>
) {
    data class Step(
        val number: Int,
        val step: String
    )
}