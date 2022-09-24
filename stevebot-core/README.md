# Stevebot Core

The core pathfinding and path-following logic of stevebot. As independent of any minecraft version or modding tool as possible. Can be added as a (gradle) dependency (see "stevebot-mod").

## How to use

1. Build a local maven artifact.

    ```
    ./gradlew publishToMavenLocal
    ```

2. Include artifact in your mod project as a gradle dependency (see "stevebot-mod" for an example)
 
    ```
   dependencies {
       compile "io.github.smiley4:stevebot-core:<version>"
   }
    ```


