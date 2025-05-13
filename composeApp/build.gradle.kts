import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "1.9.10"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)
        }

        val ktorVersion = "2.3.4"
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
            implementation("org.jetbrains.compose.ui:ui-tooling-preview-desktop:1.7.3")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            implementation("org.json:json:20231013")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-cio:$ktorVersion")
            implementation("io.ktor:ktor-client-websockets:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-websockets:$ktorVersion")
            implementation("ch.qos.logback:logback-classic:1.2.3")
        }

    }
}

// exe portable [build/compose/binaries] -> ./gradlew createDistributable
// exe install  [build/compose/binaries] -> ./gradlew packageReleaseDistributionForCurrentOS
// jar [build/compose/jars] -> ./gradlew packageReleaseJar
compose.desktop {
    application {
        mainClass = "com.vikmanz.stomptc.app.MainKt"

        buildTypes {
            release {
                proguard {
                    isEnabled = false
                }
            }
        }

        jvmArgs += listOf("-Xmx256m")


        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "stomptc"
            packageVersion = "0.0.7"


            windows {
                menuGroup = "STOMP test client"
                shortcut = true
                console = false
            }
        }

    }

}