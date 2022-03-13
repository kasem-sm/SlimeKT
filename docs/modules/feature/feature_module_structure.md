## Structure
Every feature module consists of the following sub-modules:

- **datasource:** It contains interfaces for cache and network that can be injected into the `interactors` module. This can also make testing easier. The naming convention of the classes in this module are `XFeatureApiService`, `XFeatureDatabaseService`

- **datasource-impl:** It contains a concrete implementation of the interface from the data source module. The naming convention of the classes are `XFeatureApiServiceImpl`, `XFeatureDatabaseServiceImpl`

- **domain/model:** It contains the UI Model class of the feature, which can be provided to `feature-common-ui` modules or the module where the screen using the feature resides.

- **domain/interactors:** It contains use-cases or business logic for the feature. It takes a feature data source as a dependency. Fetching and caching-related tasks are done here. 

## Feature Authentication
The authentication feature module consists of only one extra module, including all the other modules that a feature module usually contains. They are:

- **worker:** This module verifies the user's authentication status periodically and checks if the user still exists in our database or if their token has expired.

## Feature Article
The Article feature module consists of two extra modules, including all the other modules that a feature module usually contains. They are:

- **markdown:** It contains all the necessary classes that help in rendering text markdown in the Article Detail Screen. It depends on a third-party library called `common mark`.
- **widget:** It contains all the necessary classes that help in creating a Daily Read widget using the Jetpack Glance API.
- **worker:** This module manages the Daily Read feature. It consists of `DailyReadManager` which fetches articles and shows them as "Daily Read" via notification and widget.
- **common-article-ui:** It contains common UI components related to article features such as ArticleCard, which are used on every screen where an article is displayed.

## Feature Topic
A topic feature module consists of two extra modules, including all the other modules that a feature module usually contains. They are:

- **worker:** This module manages the subscription of topics.
- **common-topic-ui:** It contains common UI components related to topic features, such as Selectable Topic Chip.
