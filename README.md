# gdx-logging

Simple logging framework for libgdx. This is my first Java library for a long time!

## Getting started

At the moment this is just published direcly using Jitpack and there aren't any versions. Add to your `build.gradle`:

```groovy
allprojects {
  repositories {
    // ...
    maven { url 'https://jitpack.io' }
  }
}
```

And then in your dependencies:

```groovy
dependencies {
  implementation 'com.github.soundasleep:gdx-logging:main-SNAPSHOT'
}
```

And then to use the library... TODO

## Developing in Eclipse

1. Clone this repository using Git
2. _Import_ > _Existing Gradle Project_ (important – don't just import the root folder)

You can also run `gradlew eclipse` to reset the `.project` and `.classpath` files.

