architectury {
	common()
}

repositories {
	maven("https://maven.fabricmc.net")
}

loom {

}

dependencies {
	modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabric_loader_version"]}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${rootProject.properties["fabric_kotlin_version"]}")

}