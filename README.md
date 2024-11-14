# KMP eCommerce App

A cross-platform e-commerce application for shopping the latest footwear, built with Kotlin Multiplatform and Compose Multiplatform. This app offers a smooth shopping experience with essential e-commerce functionalities and a modern UI design, tailored to Android and iOS.

## 📱 Features

- **Cross-Platform Implementation**: Single codebase targeting both iOS and Android platforms
- **JWT Authentication**: Secure user authentication and session management
- **Product Catalog**: Browse and search through shoe products
- **Shopping Cart**: Add/remove items, manage quantities
- **Wishlists**: Save favorite items for later
- **Order Management**: Track order status and history
- **Reviews & Ratings**: View and submit product reviews
- **Offline Support**: Local data persistence for cart and wishlist

## 🛠 Technologies & Libraries

### Core Technologies

- **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)**: Framework for cross-platform development
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)**: UI toolkit for building native applications for Android, iOS, and desktop

### Networking & Data

- **[Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)**: Multiplatform HTTP client
  - OkHttp engine for Android
  - Darwin engine for iOS
- **[KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization)**: JSON parsing and serialization
- **[Kamel](https://github.com/Kamel-Media/Kamel)**: Async image loading library for Compose Multiplatform

### State Management & DI

- **[Koin](https://insert-koin.io/)**: Lightweight dependency injection framework
  - Koin Compose for ViewModel injection
- **[Lifecycle ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)**: Managing UI-related data in a lifecycle-conscious way

### Storage

- **[DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore)**: Key-value storage for user preferences and session management
- **[Room](https://developer.android.com/training/data-storage/room)**: Local database for cart and wishlist persistence
  - Note: Currently used on Android, with equivalent iOS implementation

### Navigation

- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**: Type-safe navigation framework for Compose

### Location Services

- **[Compass Geolocation](https://github.com/Kotlin/kotlinx-io)**: Cross-platform location services

## 🏗 Architecture

The app follows Clean Architecture principles with feature-based modules:

```
shared/
├── core/
│   ├── data/          # Core data layer utilities, network client
│   ├── domain/        # Core domain layer utilities, base models
│   └── presentation/  # Core presentation layer utilities, base ViewModels
│
├── feature/
│   ├── profile/
│   │   ├── data/         # User profile, auth repositories
│   │   │   ├── model/    # API DTOs, local entities
│   │   │   ├── remote/   # API services
│   │   │   ├── local/    # Local storage
│   │   │   └── repository/ # Repository implementations
│   │   ├── domain/       # Profile business logic
│   │   │   ├── model/    # Domain models
│   │   │   ├── repository/ # Repository interfaces
│   │   │   └── usecase/  # Profile use cases
│   │   └── presentation/ # Profile UI logic
│   │       ├── model/    # UI models, states
│   │       └── viewmodel/ # Profile ViewModels
│   │
│   └── shop/
│       ├── data/         # Products, cart, orders repositories
│       │   ├── model/    # API DTOs, local entities
│       │   ├── remote/   # API services
│       │   ├── local/    # Local storage
│       │   └── repository/ # Repository implementations
│       ├── domain/       # Shopping business logic
│       │   ├── model/    # Domain models
│       │   ├── repository/ # Repository interfaces
│       │   └── usecase/  # Shopping use cases
│       └── presentation/ # Shop UI logic
│           ├── model/    # UI models, states
│           └── viewmodel/ # Shop ViewModels
│
└── di/              # Dependency injection modules

androidApp/         # Android-specific implementation
├── ui/            # Compose UI components
└── MainActivity   # Android entry point

iosApp/            # iOS-specific implementation
├── ui/            # SwiftUI components
└── iOSApp        # iOS entry point
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- Xcode 13 or newer (for iOS development)
- JDK 11 or newer
- Kotlin Multiplatform Mobile plugin

### Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/kmp-ecommerce.git
```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Run the desired target platform:
   - Android: Run 'androidApp' configuration
   - iOS: Run 'iosApp' configuration

## 📚 Learning Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform Guide](https://www.jetbrains.com/lp/compose-multiplatform/)
- [KMM Samples](https://kotlinlang.org/docs/multiplatform-mobile-samples.html)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
