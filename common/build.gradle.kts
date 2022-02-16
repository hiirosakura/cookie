architectury {
	common()
}

repositories {
	maven("https://maven.fabricmc.net")
}

loom {

}

dependencies {
	modImplementation("net.fabricmc:fabric-loader:0.11.3")
	modImplementation("net.fabricmc:fabric-language-kotlin:1.6.+")
}