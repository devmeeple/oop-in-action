plugins {
    id("java")
}

group = "io.github.devmeeple"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val mockitoVersion = "5.21.0"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Mockito
    testImplementation("org.mockito:mockito-core:$mockitoVersion") // Mock 객체 생성
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion") // JUnit5와 Mockito 연결

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
