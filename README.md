[![](https://jitpack.io/v/dev.d1s/spring-boot-starter-welcomer.svg)](https://jitpack.io/#d1snin/spring-boot-starter-welcomer)

# spring-boot-starter-welcomer

A user-friendly welcomer for your Spring Boot web apps.
The content is located on the endpoint `/humans.txt ` and on `/` as a redirect to `/humans.txt`.
Content type is `text/plain`.

### Installation

```kotlin
repositories {
    maven(url = "https://jitpack.io")
}

implementation("dev.d1s:spring-boot-starter-welcomer:{spring-boot-starter-welcomer version}")
```

### Usage

You can configure the content padding, welcoming message and exclude unwanted info properties:

```yaml
# your application.yml or any other configuration source

welcomer:
  padding: 0 # 3 by default.
  message: "Hey there!" # "Welcome." by default.
  exclude-properties: # [] by default.
    - "git"
```

Add your own info:

```kotlin
@Component
class DummyInfoContributor : InfoContributor {

    override fun contribute(builder: Info.Builder) {
        builder.withDetail("dummy", true)
    }
}
```

