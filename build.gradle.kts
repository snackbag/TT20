plugins {
	id("toni.blahaj")
}

blahaj {
	config {
		yarn()
		versionedAccessWideners()
	}

	setup {}
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
	maven("https://artifacts.snackbag.net/artifactory/shitlib") // ShitLib
}

dependencies {
	implementation("net.snackbag.shit:shit:${property("deps.shitlib")}")
}