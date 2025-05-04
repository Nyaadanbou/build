import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

class LibrariesRepositoryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.applyRepositories()
    }
}

private fun Project.applyRepositories() {
    repositories {
        addLibrariesRepository()
    }
}

private fun RepositoryHandler.addLibrariesRepository() {

    mavenCentral()

    maven("https://repo.purpurmc.org/snapshots") {
        content {
            includeGroup("org.purpurmc.purpur") // PurpurMC
        }
    }

    maven("https://repo.papermc.io/repository/maven-public/") {
        // We simply include all. We believe Paper won't upload random stuff to their repo.
    }

    maven("https://repo.dmulloy2.net/repository/public/") {
        content {
            includeGroup("com.comphenix.protocol") // ProtocolLib
        }
    }

    maven("https://repo.lucko.me") {
        content {
            includeGroup("me.lucko") // Lucko's libraries
        }
    }

    maven("https://repo.minebench.de/") {
        content {
            includeGroup("de.themoep.utils") // lang
            includeGroup("de.themoep.connectorplugin") // connector
        }
    }

    maven("https://jitpack.io") {
        content {
            includeGroup("com.github.simplix-softworks") // SimplixStorage
            includeGroup("com.github.TownyAdvanced") // Towny
            includeGroup("com.github.MilkBowl") // Vault
            includeGroup("com.github.LoneDev6") // ItemsAdder
            includeGroup("com.github.Moulberry") // adventure-binary-serializer
            includeGroup("com.github.TechFortress") // GriefPrevention
            includeGroup("com.github.angeschossen") // LandAPI
        }
    }

    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        content {
            includeGroup("team.unnamed") // unnamed team mocha
        }
    }

    maven("https://repo.glaremasters.me/repository/towny/") {
        content {
            includeGroup("com.palmergames.bukkit.towny") // Towny
        }
    }

    maven("https://mvn.lumine.io/repository/maven-public/") {
        content {
            includeGroup("io.lumine") // MythicMobs
            includeGroup("com.ticxo.modelengine") // ModelEngine
        }
    }

    maven("https://nexus.phoenixdevt.fr/repository/maven-public/") {
        mavenContent {
            includeGroup("io.lumine") // MythicLib
            includeGroup("net.Indyuce") // MMOItems, MMOCore
        }
    }

    maven("https://maven.enginehub.org/repo/") {
        content {
            includeGroup("com.sk89q.worldguard")
            includeGroup("com.sk89q.worldguard.worldguard-libs")
            includeGroup("com.sk89q.worldedit")
            includeGroup("com.sk89q.worldedit.worldedit-libs")
        }
    }

    maven("https://repo.citizensnpcs.co/#/") {
        content {
            includeGroup("net.citizensnpcs")
        }
    }

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        content {
            includeGroup("me.clip") // PlaceholderAPI
        }
    }

    maven("https://repo.opencollab.dev/maven-snapshots/") {
        content {
            includeGroup("org.geysermc.floodgate")
        }
    }

    maven("https://repo.essentialsx.net/releases/") {
        mavenContent {
            includeGroup("net.essentialsx")
        }
    }

    maven("https://repo.codemc.io/repository/maven-public/") {
        content {
            includeGroup("com.ghostchu") // QuickShop-Hikari
            includeGroup("me.hsgamer")
            includeGroup("me.hsgamer.bettergui")
        }
    }

    maven("https://repo.codemc.io/repository/maven-releases/") {
        content {
            includeGroup("com.github.retrooper") // PacketEvents
        }
    }

    maven("https://repo.codemc.io/repository/maven-snapshots/") {
        content {
            includeGroup("net.wesjd") // AnvilGUI
            includeGroup("org.codemc.worldguardwrapper") // WorldGuardWrapper
            includeGroup("com.github.retrooper") // PacketEvents
        }
    }

    maven("https://repo.md-5.net/content/repositories/snapshots/") {
        content {
            includeGroup("fr.neatmonster") // See doc: https://github.com/NoCheatPlus/Docs/wiki/API
        }
    }

    maven("https://nexus.betonquest.org/repository/betonquest/") {
        content {
            includeGroup("org.betonquest") // BetonQuest
        }
    }

    maven("https://repo.william278.net/releases") {
        content {
            includeGroup("net.william278")
            includeGroup("net.william278.huskhomes") // HuskHomes
            includeGroup("net.william278.husksync") // HuskSync
        }
    }

    maven("https://repo.xenondevs.xyz/releases") {
        content {
            includeGroup("xyz.xenondevs.invui")
            includeGroup("xyz.xenondevs.commons")
            includeGroup("xyz.xenondevs.configurate")
        }
    }

    maven("https://repo.unnamed.team/repository/unnamed-public/") {
        content {
            includeGroup("team.unnamed") // creative
        }
    }

    maven("https://repo.jeff-media.com/public/") {
        content {
            includeGroup("de.jeff_media") // ChestSort
        }
    }

}