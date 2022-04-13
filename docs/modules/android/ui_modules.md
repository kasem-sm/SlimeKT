## [Common UI Module](https://github.com/kasem-sm/SlimeKT/tree/dev/common-ui)

It contains Jetpack Compose UI components, which are stateless and reusable. Compose Modifier utilities and theme files such as colors and fonts also reside in this module.

## [UI Core Module](https://github.com/kasem-sm/SlimeKT/tree/dev/ui-core)

Contains utilities and classes such as UiEvent, FlowUtils, etc. that are required by every UI Module.

## [Database Module](https://github.com/kasem-sm/SlimeKT/tree/dev/data)

Handles creation and persistence of databases.

## [Task-impl](https://github.com/kasem-sm/SlimeKT/tree/dev/task-impl)

Consists of concrete implementation of 'tas-api' module.

## [Auth-impl](https://github.com/kasem-sm/SlimeKT/tree/dev/auth-impl)

Consists of concrete implementation of 'auth-api' module.

## UI Modules

Every screen is a module that contains a screen UI made with Jetpack Compose, the screen's state, and its ViewModel. They are dependent on feature interactors. The ViewModel exposes a (single) view state to the screen.