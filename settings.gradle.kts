rootProject.name = "cookie"
include("common")
include("fabric")
include("forge")

pluginManagement {
	repositories {
		maven { setUrl("https://maven.fabricmc.net/") }
		maven { setUrl("https://maven.architectury.dev/") }
		maven { setUrl("https://files.minecraftforge.net/maven/") }
		gradlePluginPortal()
	}
}
