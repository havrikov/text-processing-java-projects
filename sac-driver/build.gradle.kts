dependencies {
    implementation(project(":utils"))
    // expose the sac interfaces to this project's consumers
    api("org.w3c.css", "sac", Deps.sacVersion)
}
