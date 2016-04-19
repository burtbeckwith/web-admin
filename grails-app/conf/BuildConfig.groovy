grails.project.work.dir = 'target'

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits "global"
    log "warn"
    repositories {
        mavenLocal()
        grailsCentral()
        mavenCentral()
        //mavenRepo "http://repo.qiyestore.com/repository/m2/"
    }

    plugins {
        build(":release:3.1.2", ":rest-client-builder:2.1.1") {
            export = false
        }

        compile(":asset-pipeline:2.7.4") {
            export = false
        }

        compile ":shiro:1.2.1"

        runtime(":jquery:1.11.1",
                ":hibernate4:4.3.10") {
            export = false
        }
    }
}
