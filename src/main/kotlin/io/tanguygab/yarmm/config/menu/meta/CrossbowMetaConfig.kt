package io.tanguygab.yarmm.config.menu.meta

import io.tanguygab.yarmm.config.menu.ItemMetaConfig
import io.tanguygab.yarmm.inventory.MenuItemView
import me.neznamy.tab.shared.Property
import me.neznamy.tab.shared.platform.TabPlayer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CrossbowMeta
import org.bukkit.inventory.meta.ItemMeta

class CrossbowMetaConfig(val projectiles: List<String>) : ItemMetaConfig(CrossbowMeta::class) {
    override fun storeData(item: MenuItemView, player: TabPlayer) = mapOf(
        "projectiles" to property(item, player, projectiles.joinToString("\n"))
    )

    override fun refresh(meta: ItemMeta, data: Map<String, Property>, force: Boolean) {
        val projectiles = data["projectiles"]!!
        if (projectiles.update() || force) (meta as CrossbowMeta).setChargedProjectiles(projectiles
            .get()
            .split("\n")
            .mapNotNull { Material.getMaterial(it) }
            .map { ItemStack(it) }
        )
    }
}