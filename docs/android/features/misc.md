## Tech Stack

- **Kotlin:** Official Programming language for Android.
- **Jetpack Compose:** Jetpack Compose is Android’s modern toolkit for building native UI.
- **Kotlin Coroutines:** A concurrency design pattern. On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive.
- **Kotlin Flows:** In coroutines, a flow is a type that can emit multiple values sequentially.
- **Room Database:** The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- **Ktor Client:** Ktor includes a multiplatform asynchronous HTTP client, which allows you to make requests and handle responses in the Kotlin way.
- **Coil:** An image loading library for Android backed by Kotlin Coroutines.
- **Dagger Hilt:** A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection.
- **Accompanist:** A collection of extension libraries for Jetpack Compose.
- **Work Manager:** The recommended solution for persistent work such as Immediate or Long running tasks.
- **Glance:** Build widget layouts for remote surfaces using a Jetpack Compose-style API.


## Other Features

- **Observer Pattern:** Data from cache  would be shown while new data is fetched from the server. The data on the screen gets updated immediately once new data is cached (Hot Flow).
- **Parallel API Requests:** While subscribing to multiple topics at once, the app sends a parallel API request to the server for faster execution of the task. (The requests are independent of one another).
- **Prioritize Recent Request:** Any ongoing requests gets cancelled once a new request is made (For example: User filters the article list with topic "a" and then immediately wishes to filter the list with topic "b").
- **IME Action:** App uses Keyboard's IME Action wherever possible for the best user experience.
- **WorkManager Use case:** App uses WorkManager API to update user's subscriptions and to fetch Daily Read articles.
- **AndroidX Glance Use case:** Daily Read Widget with the power of Jetpack Compose.
- **Composition Local:** To provide different font styles to Composable.
- **UI Sate:** App combines various flows to construct UI state.
- **Lightweight modules:** Every module is as small as possible and is only responsible for the given task.
- **Dark Mode and Material You:** Application supports dark mode as well as Material You. The color of the app changes according to the user's wallpaper.