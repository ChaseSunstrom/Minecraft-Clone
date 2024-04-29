import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.Copy

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // LWJGL dependencies
    implementation("org.lwjgl:lwjgl:3.3.0")
    implementation("org.lwjgl:lwjgl-opengl:3.3.0")
    implementation("org.lwjgl:lwjgl-glfw:3.3.0")

    // Native dependencies for Windows
    implementation("org.lwjgl:lwjgl:3.3.0:natives-windows")
    implementation("org.lwjgl:lwjgl-glfw:3.3.0:natives-windows")
    implementation("org.lwjgl:lwjgl-opengl:3.3.0:natives-windows")
}

tasks {
    // Use JUnit platform for testing
    withType<org.gradle.api.tasks.testing.Test> {
        useJUnitPlatform()
    }

    // Task to extract native libraries
    register<Copy>("extractNatives") {
        from(configurations.runtimeClasspath.get().filter {
            it.name.contains("natives")
        })
        into("$buildDir/natives")
        include("**/*.dll", "**/*.so", "**/*.dylib")
    }

    // Ensure natives are extracted before running JavaExec tasks
    withType<JavaExec> {
        dependsOn("extractNatives")
        systemProperty("org.lwjgl.librarypath", project.buildDir.resolve("natives").absolutePath)
        systemProperty("org.lwjgl.util.Debug", "true")
        systemProperty("org.lwjgl.util.DebugLoader", "true")
    }

    // Run task to execute the main class
    register<JavaExec>("run") {
        group = "application"
        mainClass = "Main" // Replace with your actual main class
        classpath = sourceSets.main.get().runtimeClasspath
        systemProperty("org.lwjgl.librarypath", project.buildDir.resolve("natives").absolutePath)
        systemProperty("org.lwjgl.util.Debug", "true")
        systemProperty("org.lwjgl.util.DebugLoader", "true")
    }
}
