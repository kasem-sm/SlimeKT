## [Common UI Module](https://github.com/kasem-sm/SlimeKT/tree/dev/common-ui)

Contains Jetpack Compose UI components which are almost stateless (reusable). Compose Modifier utils, Theme files such as Colors, Fonts also reside in this module too.

## [UI Core Module](https://github.com/kasem-sm/SlimeKT/tree/dev/ui-core)

Contains utilities and classes such as UiEvent, FlowUtils etc. that are required by every UI Module.

## [Navigation Module](https://github.com/kasem-sm/SlimeKT/tree/dev/navigation)

Responsible for handling app's navigation. All the screen modules are a dependency to the Navigation module. It constructs the Navigation Graph of the app.

## [Data Module](https://github.com/kasem-sm/SlimeKT/tree/dev/data)

Handles `data` related work such as creation and persistence of Database, session and task management, caching data etc.

## UI Modules

Every screen is a module that contains Screen UI made with Jetpack Compose, Screen's state and it's ViewModel. Feature interactors are a dependency to screen modules. The ViewModel exposes `viewState` to the screen.
