package io.tanguygab.yarmm.listeners

import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.tab
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryListener(val plugin: YARMM) : Listener {

    @EventHandler
    fun onMenuOpen(e: InventoryOpenEvent) {
        val player = e.player
        if (player !is Player) return
        println("Open " + e.inventory)
    }

    @EventHandler
    fun onMenuClose(e: InventoryCloseEvent) {
        val player = e.player
        if (player !is Player) return
        println("Close " + e.inventory + " " + plugin.menuManager.sessions[player.tab])
    }

    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        val player = e.whoClicked
        if (player !is Player || player.tab !in plugin.menuManager.sessions) return
        e.isCancelled = true
        plugin.menuManager.sessions[player.tab]!!.items
            .findLast { it.getSlot() == e.rawSlot } // until priority is added
            ?.let { item -> plugin.server.asyncScheduler.runNow(plugin) { item.config.clickActions.execute(player) } }
    }

//    @EventHandler
//    fun onMenuDrag(e: InventoryDragEvent) {}

}