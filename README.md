<h1 align="center">Contacts App</h1>

As an assignment, creating an app similar to the iOS Contacts app but with a much smaller feature set. 

<p align="center">
<img src="/screens/screen1.png" alt="Portrait Screen"/>
  <b>Portrait View</b>
<img src="/screens/screen2.png" alt="Landscape Screen"/>
  <b>Landscape View</b>
</p>


***You can Install and test Contacts App from below 👇***

[Download App](https://drive.google.com/uc?export=download&id=1nam13xgT5-WIar413PqzbWzs8GSz2_1N)


## About
It simply loads **Contacts** data from API and stores it in persistence storage (i.e. Room Database). Contacts will be always loaded from local database. Remote data (from API) and Local data is always synchronized. Used the Modern Android development tools - (Kotlin, Coroutines, Flow, Hilt, Architecture Components, MVVM, Room, Retrofit, Material Components).

- This makes it offline capable. 
- Clean and Simple Material UI.
- It supports dark theme too.
- It supports dynamic UI view by orientation changes or devices size. 

*Application data are load from [here](https://reqres.in/)*.

## Techstack and Open Source libraries

### Built With
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous operations.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Retrofit2](https://github.com/square/retrofit) to make HTTP calls to the REST API.
- [GSON](https://github.com/google/gson) to deserialize JSON requests.
- [Coil](https://github.com/coil-kt/coil) for image loading.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
- Android Jetpack
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - [Paging Library 3](https://developer.android.com/topic/libraries/architecture/paging)
    - [Room](https://developer.android.com/topic/libraries/architecture/room)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - dependency injection into an Android application.
  - [Hilt](https://dagger.dev/hilt/)
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack)
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Package Structure
    
    dev.shreyaspatil.foodium    # Root Package
    .
    ├── data                # For data handling.
    │   ├── local           # Local Persistence Database. Room (SQLite) database
    |   │   ├── dao         # Data Access Object for Room   
    │   ├── remote          # Remote Data Handlers     
    |   │   ├── api         # Retrofit API for remote end point.
    │   └── repository      # Single source of data.
    |
    ├── model               # Model classes
    |
    ├── di                  # Dependency Injection(Hilt)     
    │   └── module          # DI Modules
    |
    ├── ui                  # Activity/View layer
    │   ├── adapter         # List View Adapter / Paging Adapter
    │   ├── contact         # Application Features module
    |   │   ├── create      # Create UI View and ViewModel
    |   │   ├── detail      # Detail UI View and ViewModel
    |   │   ├── edit        # Edit UI View and ViewModel
    |   │   └── list        # List UI View and ViewModel
    │   └── Activity        # Feature Activity (Using single activity architecture)
    |
    └── utils               # Utility Classes / Kotlin extensions


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](/screens/screen4.png)

[Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db) implementation that uses a layered data source.
![](/screens/screen3.png)


## Contact
If you need any help, you can connect with me.

Email:- nyinyizaw.dev@gmail.com
