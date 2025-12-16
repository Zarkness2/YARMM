package io.tanguygab.yarmm.internal

import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.api.MenuSession
import io.tanguygab.yarmm.api.inventory.MenuInventory
import io.tanguygab.yarmm.internal.inventory.MenuItemView
import me.neznamy.tab.shared.Property
import me.neznamy.tab.shared.TAB
import me.neznamy.tab.shared.features.types.RefreshableFeature
import me.neznamy.tab.shared.platform.TabPlayer
import org.bukkit.entity.Player

class MenuSessionImpl(
    override val player: TabPlayer,
    override val menu: MenuInventory
) : RefreshableFeature(), MenuSession {

    override fun getFeatureName() = "YARMM"
    override fun getRefreshDisplayName() = "Updating menu title"

    val data = MenuData(Property(this, player, menu.title))

    //override val page = menu.pages[0]
    val items = mutableListOf<MenuItemView>()

    init {
        menu.items.forEach { item ->
            item.slots.forEach { slot ->
                val i = MenuItemView(item, slot, player, data)
                items.add(i)
                TAB.getInstance().featureManager.registerFeature("menu-item-${player.name}-${slot}", i)
            }
        }
        TAB.getInstance().featureManager.registerFeature("menu-session-${player.name}", this)
        refresh(player, true)
    }

    override fun close() {
        val p = player.player as Player
        p.closeInventory()
        items.forEach {
            TAB.getInstance().featureManager.unregisterFeature("menu-item-${player.name}-${it.slot}")
        }
        TAB.getInstance().featureManager.unregisterFeature("menu-session-${player.name}")
    }

    override fun refresh(player: TabPlayer, force: Boolean) {
        if (force || data.title.update()) {
            val inventory = menu.get(player, data)
            items.forEach { it.inventory = inventory }


            val p = player.player as Player
            p.scheduler.run(YARMM.INSTANCE, { p.openInventory(inventory) }, null)

        }
    }
}

data class MenuData(
    val title: Property,
    val materials: MutableMap<MenuItemView, Property> = mutableMapOf(),
    val amounts: MutableMap<MenuItemView, Property> = mutableMapOf(),
    val names: MutableMap<MenuItemView, Property> = mutableMapOf(),
    val lores: MutableMap<MenuItemView, Property> = mutableMapOf(),
    val slots: MutableMap<MenuItemView, Property> = mutableMapOf(),
)