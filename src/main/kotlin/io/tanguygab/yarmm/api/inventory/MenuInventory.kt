package io.tanguygab.yarmm.api.inventory

import io.tanguygab.yarmm.internal.MenuData
import me.neznamy.tab.shared.platform.TabPlayer
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

interface MenuInventory {

    val title: String
    val type: InventoryType?
    val rows: Int?

    val pages: List<MenuPage>
    val items: List<MenuItem>

    fun get(player: TabPlayer, data: MenuData): Inventory
    
}