package io.tanguygab.yarmm.config

import io.github.tanguygab.conditionalactions.ConditionalActions
import io.github.tanguygab.conditionalactions.actions.ActionGroup
import io.github.tanguygab.conditionalactions.conditions.ConditionGroup
import me.neznamy.tab.shared.config.file.ConfigurationSection
import me.neznamy.tab.shared.config.file.YamlConfigurationFile
import org.bukkit.event.inventory.InventoryType
import java.io.File

private fun getActionGroup(list: Any?) = ActionGroup(ConditionalActions.INSTANCE.actionManager, list as List<*>? ?: emptyList<Any>())

class MenuConfig(file: File, config: MainConfig) : YamlConfigurationFile(null, file) {

    val title = getString("title", null) ?: "<red>No title set"
    val rows = getInt("rows", null) ?: 6
    val type = InventoryType.entries.find { it.name == getString("type", null) } ?: InventoryType.CHEST

    val openActions = getActionGroup(getObject("open-actions", null))
    val closeActions = getActionGroup(getObject("close-actions",  null))

    val items = getMap<String, Any>("items").keys
        .map { getConfigurationSection("items.$it") }
        .map { MenuItemConfig(it, config) }
}

class MenuItemConfig(val section: ConfigurationSection, config: MainConfig) {
    val material = section.getString("material") ?: "STONE"
    val name = config.itemNamePrefix + (section.getString("name") ?: "")
    val amount = section.getString("amount") ?: "1"
    val lore = section.getStringList("lore")?.map { config.itemLorePrefix + it} ?: emptyList()
    val slots = getSlotRanges()

    val clickActions = getActionGroup(section.getObject("click-actions") ?: emptyList<Any>())
    val viewCondition = section.getString("view-condition")?.let { ConditionGroup(ConditionalActions.INSTANCE.conditionManager, it) }

    private fun getSlotRanges(): List<String> {
        val slots = section.getStringList("slots")?.toMutableList()
            ?: mutableListOf(section.getObject("slot", "0").toString())

        val ranges = mutableMapOf<String, List<String>>()
        slots.forEach { it ->
            if (it.contains("%")) return@forEach

            val range = it.split("-")
            if (range.size == 2) {
                ranges[it] = (range[0].toInt() .. range[1].toInt()).map { it.toString() }
            }
        }
        slots.removeAll(ranges.keys)
        slots.addAll(ranges.values.flatten())
        return slots.toList()
    }
}