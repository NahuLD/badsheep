import java.util.*

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.4.0"
    id("com.github.hierynomus.license") version "0.15.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

group = "me.nahu.swingy"
version = "0.1.1"

repositories {
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        name = "spigotmc"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        name = "papermc"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        name = "aikar-repo"
        url = uri("https://repo.aikar.co/content/groups/aikar/")
    }

    maven {
        name = "themoep-repo"
        url = uri("https://repo.minebench.de/")
    }

    maven {
        name = "enginehub-repo"
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven {
        name = "chatmenuapi-repo"
        url = uri("https://dl.bintray.com/nahuld/minevictus/")
    }

    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
    implementation("co.aikar:acf-bukkit:0.5.0-SNAPSHOT")
    implementation("org.jcodec:jcodec:0.2.5")
    implementation("org.jcodec:jcodec-javase:0.2.5")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    arrayOf(
        "co.aikar.commands",
        "co.aikar.locales",
        "org.jcodec"
    ).forEach { relocate(it, "${project.group}.shadow.$it") }
}

bukkit {
    name = "BadSheep"
    description = "Bad Apple but worse"
    main = "me.nahu.badsheep.BadSheepPlugin"
    authors = listOf("NahuLD")
}

license {
    header = rootProject.file("LICENCE-HEADER")
    ext["year"] = Calendar.getInstance().get(Calendar.YEAR)
    ext["name"] = "Nahuel Dolores"
    include("**/*")
}
