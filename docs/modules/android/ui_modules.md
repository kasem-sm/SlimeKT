## [Common UI Module](https://github.com/kasem-sm/SlimeKT/tree/dev/common-ui)

It contains Jetpack Compose UI components, which are stateless and reusable. Compose Modifier utilities and theme files such as colors and fonts also reside in this module.

## [UI Core Module](https://github.com/kasem-sm/SlimeKT/tree/dev/ui-core)

Contains utilities and classes such as UiEvent, FlowUtils, etc. that are required by every UI Module.

## [Navigation Module](https://github.com/kasem-sm/SlimeKT/tree/dev/navigation)

Responsible for handling the navigation of the app. All the screen modules are a dependency on the navigation module. It constructs the navigation graph for the app.

## [Data Module](https://github.com/kasem-sm/SlimeKT/tree/dev/data)

Handles data-allied tasks such as creation and persistence of databases, session and task management, caching data, etc.

## UI Modules

Every screen is a module that contains a screen UI made with Jetpack Compose, the screen's state, and its ViewModel. They are dependent on feature interactors. The ViewModel exposes a (single) view state to the screen.
