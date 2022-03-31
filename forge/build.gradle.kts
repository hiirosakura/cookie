architectury {
	platformSetupLoomIde()
	forge()
}

base.archivesName.set(rootProject.properties["archives_base_name"].toString())

repositories {
	maven("https://thedarkcolour.github.io/KotlinForForge/")
}

loom {
	forge {
		mixinConfigs("cookie.forge.mixin.json", "cookie.common.mixin.json")
	}
}

dependencies {
	forge("net.minecraftforge:forge:${rootProject.properties["minecraft_version"]}-${rootProject.rootProject.properties["forge_version"]}")
	implementation("thedarkcolour:kotlinforforge:${rootProject.properties["forge_kotlin_version"]}")
	forgeRuntimeLibrary(kotlin("stdlib-jdk8"))
	forgeRuntimeLibrary(kotlin("reflect"))
	modApi("dev.latvian.mods:rhino-forge:${rootProject.properties["rhino_version"]}")
	implementation(project(":common", configuration = "namedElements")) { isTransitive = false }
	"developmentForge"(project(":common", configuration = "namedElements")) { isTransitive = false }
	shadowCommon(project(":common", configuration = "transformProductionForge")) { isTransitive = false }
}

tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("META-INF/mods.toml") {
			expand("version" to project.version)
		}
	}
}

