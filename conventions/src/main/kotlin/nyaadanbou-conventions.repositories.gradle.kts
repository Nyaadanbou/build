repositories {
    maven("https://repo.mewcraft.cc/releases")
    maven("https://repo.mewcraft.cc/private") {
        credentials {
            username = project.providers.gradleProperty("nyaadanbou.mavenUsername").orNull
            password = project.providers.gradleProperty("nyaadanbou.mavenPassword").orNull
        }
    }

    // TODO Move to the MewcraftRepository
    //  These are just locally cached repositories
    //  We need to move these to our own repo eventually
    mavenLocal {
        content {
            includeGroup("at.helpch") // ChatChat
            includeGroup("net.leonardo_dgs") // InteractiveBooks
            includeGroup("su.nightexpress.gamepoints") // GamePoints
        }
    }

    mavenCentral()

    // Purpur MC
    maven("https://repo.purpurmc.org/snapshots") {
        content {
            includeGroup("org.purpurmc.purpur")
        }
    }

    // Paper MC
    maven("https://repo.papermc.io/repository/maven-public/") {
        content {
            includeGroup("com.velocitypowered")
            includeGroup("io.papermc.paper")
            includeGroup("com.mojang")
            includeGroup("net.md-5")
        }
    }

    // ProtocolLib
    maven("https://repo.dmulloy2.net/repository/public/") {
        content {
            includeGroup("com.comphenix.protocol")
        }
    }

    // Luck's projects
    maven("https://repo.lucko.me") {
        content {
            includeGroup("me.lucko")
        }
    }

    // Phoenix616's projects
    maven("https://repo.minebench.de/") {
        content {
            includeGroup("de.themoep.utils")
            includeGroup("de.themoep.connectorplugin")
        }
    }

    // All projects hosted on JitPack
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

    // All projects hosted on Sonatype
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        content {
            includeGroup("team.unnamed") // unnamed team mocha
        }
    }

    // Towny
    maven("https://repo.glaremasters.me/repository/towny/") {
        content {
            includeGroup("com.palmergames.bukkit.towny")
        }
    }

    // MythicMobs
    maven("https://mvn.lumine.io/repository/maven-public/") {
        content {
            includeGroup("io.lumine") // MythicMobs
            includeGroup("com.ticxo.modelengine") // ModelEngine
        }
    }

    // MythicLib, MMOCore, MMOItems
    maven("https://nexus.phoenixdevt.fr/repository/maven-public/") {
        mavenContent {
            includeGroup("io.lumine") // MythicLib
            includeGroup("net.Indyuce") // MMOItems
        }
    }

    // WorldEdit, WorldGuard
    maven("https://maven.enginehub.org/repo/") {
        content {
            includeGroup("com.sk89q.worldguard")
            includeGroup("com.sk89q.worldguard.worldguard-libs")
            includeGroup("com.sk89q.worldedit")
            includeGroup("com.sk89q.worldedit.worldedit-libs")
        }
    }

    // NPCs
    maven("https://repo.citizensnpcs.co/#/") {
        content {
            includeGroup("net.citizensnpcs")
        }
    }

    // Placeholders
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        content {
            includeGroup("me.clip")
        }
    }

    // Bedrock bridge
    maven("https://repo.opencollab.dev/maven-snapshots/") {
        content {
            includeGroup("org.geysermc.floodgate")
        }
    }

    // EssentialsX
    maven("https://repo.essentialsx.net/releases/") {
        mavenContent {
            includeGroup("net.essentialsx")
        }
    }

    // BetterGUI
    maven("https://repo.codemc.io/repository/maven-public/") {
        content {
            includeGroup("me.hsgamer")
            includeGroup("me.hsgamer.bettergui")
        }
    }

    // PacketEvents
    maven("https://repo.codemc.io/repository/maven-releases/") {
        content {
            includeGroup("com.github.retrooper")
        }
    }

    // PacketEvents, AnvilGUI, WorldGuardWrapper
    maven("https://repo.codemc.io/repository/maven-snapshots/") {
        content {
            includeGroup("net.wesjd")
            includeGroup("org.codemc.worldguardwrapper")
            includeGroup("com.github.retrooper")
        }
    }

    // NoCheatPlus
    maven("https://repo.md-5.net/content/repositories/snapshots/") {
        // See doc: https://github.com/NoCheatPlus/Docs/wiki/API
        content {
            includeGroup("fr.neatmonster")
        }
    }

    // BetonQuest
    maven("https://nexus.betonquest.org/repository/betonquest/") {
        content {
            includeGroup("org.betonquest")
        }
    }

    // HuskHomes, HuskTowns, HuskSync
    maven("https://repo.william278.net/releases") {
        content {
            includeGroup("net.william278")
            includeGroup("net.william278.huskhomes")
            includeGroup("net.william278.husksync")
        }
    }

    // Nova, InvUI, Commons, Configurate
    maven("https://repo.xenondevs.xyz/releases") {
        content {
            includeGroup("xyz.xenondevs.invui")
            includeGroup("xyz.xenondevs.commons")
            includeGroup("xyz.xenondevs.configurate")
        }
    }

    // unnamed org
    maven("https://repo.unnamed.team/repository/unnamed-public/") {
        content {
            includeGroup("team.unnamed")
        }
    }

    // ChestSort
    maven("https://repo.jeff-media.com/public/") {
        content {
            includeGroup("de.jeff_media")
        }
    }
}