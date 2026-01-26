plugins {
    `java-library`
    id("org.springframework.boot")
    id("com.diffplug.spotless")
}

val springBootVersion: String by project
val spotlessVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    // spring bill of materials (BOM)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    annotationProcessor(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

spotless {
    java {
        target("src/*/java/**/*.java")
        importOrder("java|javax", "", "com.progmatic", "com.progmatic.store", "\\#")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}