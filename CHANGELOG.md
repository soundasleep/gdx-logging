# gdx-logging changelog

## [Unreleased](https://github.com/soundasleep/gdx-logging/compare/0.7.0...HEAD)

## [0.7.1](https://github.com/soundasleep/gdx-logging/compare/0.7.0...0.7.1)

- Added temporary `Slf4jGdxLogger#disableInfoLoggingForName` workaround for super-verbose logging classes

## [0.7.0](https://github.com/soundasleep/gdx-logging/compare/0.6.3...0.7.0)

- Added experimental support for slf4j; slf4j logging should now automatically go to GDX
- Based on https://www.slf4j.org/faq.html#slf4j_compatible
- Moved `FastLogger.Level` to top-level enum `Level`
- Fixed a bug where `.willLog(ERROR)` would return false for an `INFO` logger

## [0.6.3](https://github.com/soundasleep/gdx-logging/compare/0.6.0...0.6.3)

- Fix cached loggers double-formatting cached log messages
- Make `actuallyLog` a public method on all loggers

## [0.6.0](https://github.com/soundasleep/gdx-logging/compare/0.5.3...0.6.0)

- During startup, `GdxLog`s will now cache log messages, and restore these log messages once `Gdx.app` is available, so that no startup logging information is lost
- Library now explicitly targets JDK 16 rather than whatever JDK is available at compile time
- Fixes an issue where a logger initialised before `Gdx.app` was set would continually check for `Gdx.app` even after it was set in the future

## [0.5.3](https://github.com/soundasleep/gdx-logging/compare/0.5.1...0.5.3)

- Added `maven-publish` plugin to build script to allow defining licenses

## [0.5.1](https://github.com/soundasleep/gdx-logging/compare/0.5.0...0.5.1)

- Change default `PrintTimeOption` for new logs to `NONE`

## [0.5.0](https://github.com/soundasleep/gdx-logging/compare/0.4.3...0.5.0)

- By default, logs are now printed with the current time - configurable with `PrintTimeOption`

## [0.4.3](https://github.com/soundasleep/gdx-logging/compare/0.4.2...0.4.3)

- Stdout log messages above ERROR are now printed to stderr

## [0.4.2](https://github.com/soundasleep/gdx-logging/compare/0.4.1...0.4.2)

- Fixed a potential infinite loop when printing a throwable containing formatting characters such as `%s`

## [0.4.1](https://github.com/soundasleep/gdx-logging/compare/0.4.0...0.4.1)

- Added support for logging `Throwable`s directly with `log#throwable(Throwable t);`

## [0.4.0](https://github.com/soundasleep/gdx-logging/compare/0.3.1...0.4.0)

- Added support for a fallback log if Gdx.app has not been setup yet

## [0.3.1](https://github.com/soundasleep/gdx-logging/compare/0.3.0...0.3.1)

- Renamed `LICENSE.md` to `LICENSE` to fix [Gradle-License-Report](https://github.com/jk1/Gradle-License-Report) not picking up the correct license

## [0.3.0](https://github.com/soundasleep/gdx-logging/compare/0.2.0...0.3.0)

- Updated GitHub CI to OpenJDK 17
- Updated Gradlew CI to OpenJDK 17/gradle 7.3
- Added `@NonNull` annotation to `GdxLog.newLog(...)`
