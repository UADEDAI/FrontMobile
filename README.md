# DAITP - Cinemapp Kotlin Android App

This is a Kotlin-based Android application. It utilizes Android's Gradle plugin, Kotlin Android plugin, and Google Play services for building and deploying the app.

## Prerequisites

Before starting, ensure you have the following software installed on your machine:

- [Android Studio](https://developer.android.com/studio)
- [Gradle](https://gradle.org/)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Kotlin](https://kotlinlang.org/)

## Getting Started

Clone the repository to your local machine:

```bash
git clone https://github.com/UADEDAI/FrontMobile.git
```

Open the project with Android Studio.

# Building the App

You can build the application using Android Studio's build features, or from the command line with Gradle:

```bash
./gradlew build
```

# Signing the App

The application is configured with a signing configuration for both debug and release builds. The keystore information is found in the `build.gradle` file.

**IMPORTANT**: Never expose your keystore and its credentials in your code. It's recommended to provide these details through environment variables or secure files in your CI/CD process.

# Running the App

You can run the app on an emulator or a real device:

- **Emulator**: Use Android Studio's AVD Manager to create a virtual device and run the app.

- **Real device**: Enable USB debugging in your device's developer options and connect it to your machine.

# Testing

Test the application by running:

```bash
./gradlew test
```

# Dependencies

This project uses a number of dependencies, such as `Firebase`, `Glide`, `Retrofit`, `Gson`, `Google Play Services`, `Navigation Components` and several `AndroidX` libraries. For a full list, refer to the `build.gradle` file.
