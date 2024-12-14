plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

group = "com.github.chwltd"
version = "v0.1.6"

android {
    namespace = "com.chwltd.utils"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.chwltd"
            artifactId = "ChTest"
            version = "v0.1.6"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

tasks.register<Zip>("generateRepo") {
    val publishTask = tasks.named(
        "publishReleasePublicationToMyrepoRepository",
        PublishToMavenRepository::class.java)
    from(publishTask.map { it.repository.url })
    into("ChTest")
    archiveFileName.set("ChTest.zip")
}