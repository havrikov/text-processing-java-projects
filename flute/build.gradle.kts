// The version from mavenCentral() has the following error:
// inconsistent module metadata found. Descriptor: milyn:flute:1.3 Errors: bad group: expected='org.milyn' found='milyn'
// However, jcenter() is going to be discontinued:
// https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/
// Therefore, who knows how long this will work.
repositories {
    jcenter()
}

dependencies {
    implementation(project(":sac-driver"))
    subject("org.milyn", "flute", Deps.fluteVersion)
}
