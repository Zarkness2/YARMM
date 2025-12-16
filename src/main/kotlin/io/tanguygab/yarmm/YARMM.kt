package io.tanguygab.yarmm

import io.tanguygab.yarmm.internal.MenuManagerImpl
import io.tanguygab.yarmm.internal.listeners.InventoryListener
import me.neznamy.tab.api.event.plugin.TabLoadEvent
import me.neznamy.tab.shared.TAB
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class YARMM : JavaPlugin() {

    lateinit var menuManager: MenuManagerImpl

    override fun onEnable() {
        INSTANCE = this
        TAB.getInstance().eventBus!!.register(TabLoadEvent::class.java) {
            onDisable()
            load()
        }
        load()
    }

    private fun load() {
        menuManager = MenuManagerImpl()
        menuManager.load()
        server.pluginManager.registerEvents(InventoryListener(menuManager), this)
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)
        menuManager.unload()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val menu = menuManager.menus[if (args.isEmpty()) "menu" else "menu2"]!!
            menuManager.openMenu(TAB.getInstance().getPlayer(sender.uniqueId)!!, menu)
        }
        return true
    }

    companion object {
        lateinit var INSTANCE: YARMM
    }
}
