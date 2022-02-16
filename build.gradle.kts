import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

plugins {
	id("architectury-plugin") version "3.4-SNAPSHOT"
	id("dev.architectury.loom") version "0.10.0-SNAPSHOT" apply false
	kotlin("jvm") version "1.6.10"
	id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

architectury {
	minecraft = rootProject.properties["minecraft_version"].toString()
}

subprojects {
	apply(plugin = "dev.architectury.loom")
	apply(plugin = "java")
	apply(plugin = "architectury-plugin")
	apply(plugin = "maven-publish")
	apply(plugin = "org.jetbrains.kotlin.jvm")


	val time = SimpleDateFormat("yyyyMMdd").format(Date())
	version = "${rootProject.properties["mod_version"]}-build.$time"
	group = rootProject.properties["maven_group"].toString()

	extensions.configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	repositories {
		maven { setUrl("https://maven.architectury.dev/") }
		mavenCentral()
		mavenLocal()
	}

	dependencies {
		"minecraft"("com.mojang:minecraft:${rootProject.properties["minecraft_version"]}")
		"mappings"("net.fabricmc:yarn:${rootProject.properties["yarn_mappings"]}:v2")
	}

	tasks {
		withType<JavaCompile> {
			options.encoding = "UTF-8"
			options.release.set(17)
		}

		withType<KotlinCompile> {
			kotlinOptions.jvmTarget = "17"
		}

		"jar"(Jar::class) {
			from(rootProject.file("LICENSE"))
		}
	}
}

subprojects {
	if (path != ":common") {
		apply(plugin = "com.github.johnrengelman.shadow")
		val bundle by configurations.creating {
			isCanBeConsumed = false
			isCanBeResolved = true
		}
		tasks {
			"jar"(Jar::class) {
				archiveClassifier.set("dev-slim")
			}

			"shadowJar"(ShadowJar::class) {
				archiveClassifier.set("dev-shadow")
				configurations = listOf(bundle)
			}

			"remapJar"(RemapJarTask::class) {
				dependsOn("shadowJar")
				input.set(named<ShadowJar>("shadowJar").flatMap { it.archiveFile })
				archiveClassifier.set(project.name)
			}
		}
	}
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "architectury-plugin")
}
