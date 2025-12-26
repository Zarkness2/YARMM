package io.tanguygab.yarmm.config.menu

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.tanguygab.yarmm.config.menu.meta.ArmorMetaConfig
import io.tanguygab.yarmm.inventory.MenuItemView
import me.neznamy.tab.shared.Property
import me.neznamy.tab.shared.config.file.ConfigurationSection
import me.neznamy.tab.shared.platform.TabPlayer
import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import kotlin.reflect.KClass

fun <T : Keyed> String.getFromRegistry(registryKey: RegistryKey<T>): T? {
    val key = NamespacedKey.fromString(lowercase()) ?: return null
    return RegistryAccess.registryAccess().getRegistry(registryKey).get(key)
}
fun <T : Keyed> Property.getFromRegistry(registryKey: RegistryKey<T>) = get().getFromRegistry(registryKey)

abstract class ItemMetaConfig(private val clazz: KClass<out ItemMeta>) {
    abstract fun storeData(item: MenuItemView, player: TabPlayer): Map<String, Property>
    abstract fun refresh(meta: ItemMeta, data: Map<String, Property>, force: Boolean)

    fun isMeta(meta: ItemMeta) = clazz.isInstance(meta)

    companion object {
        fun property(item: MenuItemView, player: TabPlayer, value: String) = Property(item, player, value)

        val types = mutableMapOf(
            "armor" to ArmorMetaConfig::class,

        )
        fun fromItem(item: ConfigurationSection) = types
            .filter { it.key in item.keys }
            .map { it.value.java.constructors.first().newInstance(item.getConfigurationSection(it.key)) as ItemMetaConfig }
    }
}

