# gdx-logging [![](https://jitpack.io/v/soundasleep/gdx-logging.svg)](https://jitpack.io/#soundasleep/gdx-logging)

Simple logging framework for [libgdx](https://github.com/libgdx/libgdx/). This is my first Java library for a long time, so there may be some weirdness going on.

## Getting started

```groovy
// add to build.gradle
allprojects {
  repositories {
    // ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.soundasleep:gdx-logging:0.6.0' // or 'main-SNAPSHOT' for the latest build
}
```

You can then refresh your Gradle dependencies to pick up the library.

(If you're using `main-SNAPSHOT`, use `gradlew --refresh-dependencies` to force Gradle to pull the latest copy of all your dependencies.)

### Classes can have their own log

```java
public class MyClass {
  private static GdxLog LOG = GdxLog.newLog(MyClass.class); // creates a new log group "MyClass"
  
  private void method() {
    if (LOG.info()) {
      LOG.info("hello! %s + %s = %s", 1, 1, 2);  // => System.out.println("[MyClass] hello! 1 + 1 = 2");
    }
  } 
}
```

### Or, you can log anywhere you want

```java
private void method() {
  GdxLog.newLog("my-library").info("hello!");
}
```

## Developing in Eclipse

1. Clone this repository using Git
2. _Import_ > _Existing Gradle Project_ (important – don't just import the root folder)

You can also run `gradlew eclipse` to reset the `.project` and `.classpath` files.

## TODO

* Able to configure the logging levels of different groups
* Writing logs to a filesystem
* All the other things that a good logging library needs
