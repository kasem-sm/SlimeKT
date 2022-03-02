## Core Module

[A pure Java/Kotlin module](https://github.com/kasem-sm/SlimeKT/tree/dev/core) that consists of core business models, classes and utlities for all other modules in the project. It doesn't depends on any other modules.

## Feature Modules Structure
Every feature module consists of the following sub-modules:

- **datasource:** Contains interface for cache and network which can be injected to `interactors` module The naming convention of the interface in this module are `XFeatureApiService`, `XFeatureDatabaseService`

- **datasource-impl:** Contains concrete implementation of the interface in datasource module. The naming convention of the classes here are `XFeatureApiServiceImpl`, `XFeatureDatabaseServiceImpl`

- **domain/model:** Contains the UI Model class of the feature which can be provided to `feature-common-ui` modules or the module where the screen using the feature resides.

- **domain/interactors:** Contains use-cases/business logic of the feature. It takes datasource as a dependency. Fetching and caching related works are done here. 

- worker: Performs task(s) using WorkManager related to the current feature. 

- feature-common-ui: Contains feature common Ui elements. For instance, `Article CardView` which is displayed in two different screens are added into `feature-common-ui` modules.


## Feature Article
Article feature module consists of two extra modules including all the other modules that a feature modules usually contains. The two extra modules are:

- **markdown:** Contains all the necessary classes which helps in rendering text markdown in Article Detail Screen. It depends on a third party library called `commonmark`.
- **widget:** It contains all the necessary classes that helps in creating Daily Read widget using Jetpack Glance API.