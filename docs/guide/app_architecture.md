SlimeKT follows the MVVM Architecture. Every screen in our app follows the below mentioned model. 

## UI Layer

<figure>
    <a href="#1">
        <img src="1.jpg">
    </a>
</figure>

### Screen
Every screen in the app is tied to a `viewModel`. The screen composable collects data from the `viewModel` and provides it to our **screen content** which is a stateless composable. UI-related events such as navigation and displaying messages to the user are performed by this composable.

### Screen Content - A Stateless Composable
As the name states, this part contains the actual content of our screen, such as _**Button**_, _**LazyList**_, _**Text Composable**_, etc. This Composable is stateless. The main aim of creating a separate composable that is stateless is to allow easier testing.

### ViewModel
The `viewModel` exposes `uiEvent` and `uiState` and some public functions. `uiEvent` is a SharedFlow that is used to fire off one-time events such as triggering navigation or showing a message to the user. `uiState` is constructed by combining various Flows to update the state of the screen. The `viewModel` takes domain layer classes such as **Interactors** and **Observers** as a dependencies for it. 

## Domain Layer

<figure>
    <a href="#2">
        <img src="2.jpg">
    </a>
</figure>

### Interactors
