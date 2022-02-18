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
	id("maven-publish")
}

architectury {
	minecraft = rootProject.properties["minecraft_version"].toString()
}

fun String.captureName(): String {
	return this.substring(0, 1).toUpperCase() + this.substring(1)
}

subprojects {
	apply(plugin = "dev.architectury.loom")
	apply(plugin = "java")
	apply(plugin = "architectury-plugin")
	apply(plugin = "maven-publish")
	apply(plugin = "org.jetbrains.kotlin.jvm")


	val time = SimpleDateFormat("yyyyMMdd").format(Date())
	version = "${rootProject.properties["mod_version"]}-build.$time+${rootProject.properties["minecraft_version"]}"
	group = rootProject.properties["maven_group"].toString()

	extensions.configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	repositories {
		maven("https://maven.architectury.dev/")
		maven {
			url = uri("https://maven.saps.dev/minecraft")
			content {
				includeGroup("dev.latvian.mods")
			}
		}
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
		val shadowCommon by configurations.creating
		tasks {
			"jar"(Jar::class) {
				archiveClassifier.set("dev-slim")
			}

			"shadowJar"(ShadowJar::class) {
				this.configurations = listOf(shadowCommon)
				archiveClassifier.set("dev-shadow")
			}

			"remapJar"(RemapJarTask::class) {
				dependsOn("shadowJar")
				classes
				input.set(named<ShadowJar>("shadowJar").flatMap { it.archiveFile })
				archiveClassifier.set(project.name)
			}
		}

		val sourcesJar by tasks.registering(Jar::class) {
			dependsOn(tasks.classes)
			archiveClassifier.set("sources")
			from(sourceSets.main.get().allSource, parent!!.allprojects.find { it.path == ":common" }!!.sourceSets["main"].allSource)
		}

		publishing {
			publications {
				create<MavenPublication>("maven${project.name.captureName()}") {
					artifactId = "${rootProject.properties["mod_id"].toString()}-${project.name}"
					val remap = tasks.named<RemapJarTask>("remapJar")
					artifact(remap.flatMap { it.archiveFile }) {
						builtBy(remap)
						version = rootProject.properties["mod_version"].toString()
					}
					artifact(sourcesJar.flatMap { it.archiveFile }) {
						builtBy(sourcesJar)
						version = rootProject.properties["mod_version"].toString()
						classifier = "sources"
					}
					pom {
						name.set("Cookie-${project.name.captureName()}")
						description.set("A minecraft library client mod")
						url.set("https://github.com/hiirosakura/cookie")
						developers {
							developer {
								id.set("forpleuvoir")
								name.set("forpleuvoir")
								email.set("695801070@qq.com")
							}
						}
					}
				}
				repositories {
					maven {
						name = "GitHubPackages"
						url = uri("https://maven.pkg.github.com/hiirosakura/cookie")
						credentials {
							username = System.getenv("GITHUB_USERNAME")
							password = System.getenv("GITHUB_TOKEN")
						}
					}
				}
			}
		}
	}
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "architectury-plugin")
}
