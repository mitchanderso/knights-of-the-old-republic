# KTOR & KTOR

A simple example application written in Kotlin to 

- Introduce some of the new developers in the office to REST APIs
- Introduce some of the non-jvm developers to Kotlin
- Introduce some of the jvm developers to Ktor
- Have a bit of fun by really running the KTOR (Knights of the Old Republic and KTOR) pun in to the ground

It features some of the basic plugin usage from KTOR including

- Routes
- Authentication / Authorization
- Status Pages (Exception Mapping)
- Content negotiation
- Connecting to a mongoDB using [KMongo](https://litote.org/kmongo/)

Some things that it doesnt do

- Properly explain/demonstrate hierarchical routing
- Show how you can move things out of the Application.kt file usin extension functions
- Use KTOR config files for env config type variables

#### How to run

Had a few issues managing versions of kotlin to make sure everything played right, boiled down to making sure the jvmTarget
was set to 1.8 (hard requirement from KMongo)

Pre-reqs
* Gradle
* MongoDB running locally on port 27017

`gradle build`

If you are running from intelliJ (and lets be honest if you are using Kotlin and KTOR, 
you are definitely using intelliJ) you can just set a run configuration for the Application.kt file


TODO // Include google slides presentation when complete