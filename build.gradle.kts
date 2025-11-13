plugins {
    java
    alias(libs.plugins.springframeworkBoot)
}

apply(plugin = "io.spring.dependency-management")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform(libs.camel.boot.bom))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.camel.spring.boot.starter)
    implementation(libs.camel.direct.starter)
    implementation(libs.camel.platform.http.starter)
    implementation(libs.camel.rest.starter)
    implementation(libs.camel.cxf.soap.starter)
}