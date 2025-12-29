package io.tanguygab.yarmm.converter

import me.neznamy.tab.shared.config.file.ConfigurationSection
import me.neznamy.tab.shared.config.file.YamlConfigurationFile
import org.bukkit.Bukkit
import java.io.File

abstract class PluginConverter(val folder: String, private val itemsSection: String) {

    fun convert(): Boolean {
        val folder = Bukkit.getPluginsFolder().resolve(folder)
        if (folder.exists()) {
            convertFiles(folder.parentFile)
            return true
        }
        return false
    }

    private fun convertFiles(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach { convertFiles(it) }
            return
        }
        if (!file.name.endsWith(".yml")) return
        convertFile(file, Bukkit.getPluginsFolder().resolve("YARMM/menus/converted/$file"))
    }

    private fun convertFile(input: File, output: File): YamlConfigurationFile {
        if (!output.exists()) {
            if (!output.parentFile.exists())
                output.parentFile.mkdirs()
            output.createNewFile()
        }
        val old = YamlConfigurationFile(null, input)
        val new = YamlConfigurationFile(null, output)

        val args = getArgs(old)
        convertMenu(old, new, args)

        val oldSection = old.getConfigurationSection(itemsSection)
        oldSection.keys.forEach {
            convertItem(oldSection.getConfigurationSection("$it"), new, "items.$it", args)
        }
        return new
    }

    protected fun convertActions(actions: List<String>, args: Map<String, String>, requirements: Any? = null): List<Any> {
        val list = mutableListOf<Any>()
        if (requirements != null) {
            try {
                list.addAll(convertRequirements(requirements, args))
            } catch (e: Exception) {
                println("Failed to convert requirement $requirements")
                e.printStackTrace()
            }
        }
        list.addAll(actions.mapNotNull {
            try {
                convertAction(it, args)
            } catch (e: Exception) {
                println("Failed to convert action $it")
                e.printStackTrace()
                null
            }
        })
        return list
    }

    protected abstract fun getArgs(input: YamlConfigurationFile): Map<String, String>
    protected abstract fun convertMenu(input: YamlConfigurationFile, output: YamlConfigurationFile, args: Map<String, String>)
    protected abstract fun convertItem(input: ConfigurationSection, output: YamlConfigurationFile, path: String, args: Map<String, String>)
    protected abstract fun convertAction(input: String, args: Map<String, String>): String?
    protected abstract fun convertRequirementType(input: Any, args: Map<String, String>): String
    protected abstract fun convertRequirements(input: Any, args: Map<String, String>): List<Map<String, Any?>>

}