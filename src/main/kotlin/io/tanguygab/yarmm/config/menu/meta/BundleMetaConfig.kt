package io.tanguygab.yarmm.config.menu.meta

import io.tanguygab.yarmm.config.menu.ItemMetaConfig
import io.tanguygab.yarmm.inventory.MenuItemView
import me.neznamy.tab.shared.Property
import me.neznamy.tab.shared.platform.TabPlayer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import org.bukkit.inventory.meta.ItemMeta

class BundleMetaConfig(val inventory: List<String>) : ItemMetaConfig(BundleMeta::class) {
    override fun storeData(item: MenuItemView, player: TabPlayer) = mapOf(
        "inventory" to property(item, player, inventory.joinToString("\n"))
    )

    override fun refresh(meta: ItemMeta, data: Map<String, Property>, force: Boolean) {
        val inventory = data["inventory"]!!
        if (inventory.update() || force) (meta as BundleMeta).setItems(inventory
            .get()
            .split("\n")
            .mapNotNull { Material.getMaterial(it) }
            .map { ItemStack(it) }
        )
    }
}