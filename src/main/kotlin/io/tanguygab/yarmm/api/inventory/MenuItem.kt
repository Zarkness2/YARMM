package io.tanguygab.yarmm.api.inventory

data class MenuItem(
    val material: String,
    val name: String,
    val amount: String = "1",
    val lore: List<String> = listOf(),
    val slots: List<String> = listOf(),
)