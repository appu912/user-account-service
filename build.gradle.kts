plugins {
    `java-library`
    id("com.diffplug.spotless") version "7.2.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.3")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.5.4")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0-M1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.0-M1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0-M1")

    testImplementation("org.mockito:mockito-core:5.18.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
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