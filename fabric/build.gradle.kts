architectury {
	platformSetupLoomIde()
	fabric()
}

base.archivesName.set(rootProject.properties["archives_base_name"].toString())

val generatedResources = file("src/generated/resources")

sourceSets {
	main {
		resources {
			srcDir(generatedResources)
		}
	}
}

repositories {
	maven {
		name = "TerraformersMC"
		url = uri("https://maven.terraformersmc.com/releases")

		content {
			includeGroup("com.terraformersmc")
		}
	}
}

dependencies {
	modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabric_loader_version"]}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.properties["fabric_api_version"]}")
	modApi("net.fabricmc:fabric-language-kotlin:${rootProject.properties["fabric_kotlin_version"]}")
	modApi("dev.latvian.mods:rhino-fabric:${rootProject.properties["rhino_version"]}")
	modLocalRuntime(modCompileOnly("com.terraformersmc:modmenu:${rootProject.properties["mod_menu_version"]}")!!)
	implementation(project(":common", configuration = "namedElements")) { isTransitive = false }
	"developmentFabric"(project(":common", configuration = "namedElements")) { isTransitive = false }
	shadowCommon(project(":common", configuration = "transformProductionFabric")) { isTransitive = false }
}


tasks {

	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand("version" to project.version)
		}
	}
}

