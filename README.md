<div align="center">
<img src=".github/assets/logo.png" width="200" height="200">
</div>
<h1 align="center">Pokedex Compose</h1>

<p align="center">  
Futuristic Pokedex app built with Jetpack Compose.
</p>

## 🔮 Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for asynchronous.
- Jetpack Compose - A modern toolkit for building native Android UI.
- MVVM Architecture
    - Repository pattern
    - Dependency Injection with [Koin](https://insert-koin.io/)
    - Networking with [Retrofit](https://square.github.io/retrofit/)
    - Image loading with [Coil](https://github.com/coil-kt/coil)
    - Logging with [Timber](https://github.com/JakeWharton/timber)
    - Palette management with [Kmpalette](https://github.com/jordond/kmpalette)
    - Recomposition debugging with [rebugger](https://github.com/theapache64/rebugger)

## 🗂️ Folder Structure
```
Pokedex/
├── app/                     # Main application directory
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Java/Kotlin source files
│   │   │   │   └── com/
│   │   │   │       └── pokedex/
│   │   │   │           ├── data/               # Repository and data models
│   │   │   │           │    ├── local/         # Local data source
│   │   │   │           │    │   ├── db         # Database
│   │   │   │           │    │   └── pref       # Shared Preferences
│   │   │   │           │    ├── remote/        # Remote data source
│   │   │   │           │    │   ├── api        # Retrofit API
│   │   │   │           │    │   └── service    # Service classes
│   │   │   │           │    └── repository/    # Repository classes
│   │   │   │           ├── di/                 # Dependency Injection
│   │   │   │           ├── initializer/        # App initializers
│   │   │   │           ├── notification/       # Notification manager
│   │   │   │           ├── presentation/       # Jetpack Compose UI
│   │   │   │           │   ├── common/         # Common UI components
│   │   │   │           │   ├── theme/          # App theme
│   │   │   │           │   └── ui/             # UI components
│   │   │   │           │       ├── screen1
│   │   │   │           │       │   ├── Screen1.kt           # Screen 1
│   │   │   │           │       │   ├── Screen1ViewModel.kt  # ViewModel for Screen 1
│   │   │   │           │       │   └── components/          # Screen 1 components
│   │   │   │           │       │       ├── Component1.kt    # Component 1
│   │   │   │           │       │       └── Component2.kt    # Component 2
│   │   │   │           │       └── screen2
│   │   │   │           │           ├── Screen2.kt           # Screen 2
│   │   │   │           │           ├── Screen2ViewModel.kt  # ViewModel for Screen 2
│   │   │   │           │           └── components/          # Screen 2 components
│   │   │   │           │               ├── Component1.kt    # Component 1
│   │   │   │           │               └── Component2.kt    # Component 2
│   │   │   │           └── utils/   # Utility classes and helpers
│   │   │   ├── res/                 # Resource files (layouts, drawables, etc.)
│   │   │   └── AndroidManifest.xml # Android configuration
│   └── build.gradle          # App-specific Gradle configurations
├── build/                    # Build outputs
├── gradle/                   # Gradle configuration files
├── .gitignore                # Git ignore rules
├── build.gradle              # Project-wide Gradle configuration
└── settings.gradle           # Project settings
```