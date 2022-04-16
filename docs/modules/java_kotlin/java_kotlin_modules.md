## [Core Module](https://github.com/kasem-sm/SlimeKT/tree/dev/core)

It consists of core business models, classes, and utilities for all other modules in the project. It doesn't depend on any other modules.

## [Common Test Utils](https://github.com/kasem-sm/SlimeKT/tree/dev/common-test-utils)

It consists of utilities and helper extension functions used during unit testing.

## [Task-api](https://github.com/kasem-sm/SlimeKT/tree/dev/task-api)

It consists of interface that is responsible to execute different workers. This is done to avoid providing worker modules directly to any other modules. This module also help us during testing, i.e, we can swap out the worker with a fake Implementation of this interface.

## [Auth-api](https://github.com/kasem-sm/SlimeKT/tree/dev/auth-api)

It consists of interface that is responsible to manage the current authentication session. This can also make testing easier.