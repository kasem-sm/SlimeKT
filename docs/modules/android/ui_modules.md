## Common UI Module

[A Module](https://github.com/kasem-sm/SlimeKT/tree/dev/common-ui) that contains Jetpack Compose UI components which are almost stateless (reusable). Compose Modifier utils, Theme files such as Colors, Fonts also resides in this module too.

## UI Core Module

[A Module](https://github.com/kasem-sm/SlimeKT/tree/dev/ui-core) that contains utilities and classes such as UiEvent, FlowUtils etc. that are required by every UI Modules.

## Navigation Module

[A Module](https://github.com/kasem-sm/SlimeKT/tree/dev/navigation) that is only responsbile to handle app's navigation. All the screens modules are a dependency to the Navigation module. It constructs the Navigation Graph of the app.

## Data Module

[A Module](https://github.com/kasem-sm/SlimeKT/tree/dev/data) that handles data related work such as creation and persistance of Database, session and task management, caching data etc.

# UI Modules

Every screen is a module that contains Screen UI made with Jetpack Compose, Screen's state and it's ViewModel. Feature interactors are a dependency to screen modules. The ViewModel exposes `viewState` to the screen.

