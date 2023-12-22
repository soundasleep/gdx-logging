# gdx-logging changelog

## [Unreleased](https://github.com/soundasleep/gdx-logging/compare/0.4.2...HEAD)

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
