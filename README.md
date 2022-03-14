![Asset 1](/docs/header.png)

[![Slime Build (Android)](https://github.com/kasem-sm/SlimeKT/actions/workflows/android_action.yml/badge.svg)](https://github.com/kasem-sm/SlimeKT/actions/workflows/android_action.yml)
[![Slime Build (API)](https://github.com/kasem-sm/SlimeKT/actions/workflows/api_action.yml/badge.svg)](https://github.com/kasem-sm/SlimeKT/actions/workflows/api_action.yml)
![Slime Heroku Deployment](https://img.shields.io/github/deployments/kasem-sm/SlimeKT/slime-kt?logo=Heroku)

[![GitHub stars](https://img.shields.io/github/stars/kasem-sm/SlimeKT?style=social)](https://github.com/kasem-sm/SlimeKT/stargazers)
[![GitHub watchers](https://img.shields.io/github/watchers/kasem-sm/SlimeKT?style=social)](https://github.com/kasem-sm/SlimeKT/watchers)

## Introduction 🙋‍♂️

An article sharing platform where you can _**personalize, subscribe to your favorite topics, get
daily-read reminders, explore new authors, and share your articles**_. It uses the most cutting-edge
technology in the frontend and backend parts, such as <b>Ktor, MongoDB, and Jetpack Compose.</b>

It follows the recommended app architecture as stated in
official [Android documentation](https://developer.android.com/jetpack/guide)

## Documentation 📚

[![Documentation](https://img.shields.io/badge/Visit-blue?style=for-the-badge)](https://kasem-sm.github.io/SlimeKT)

We have prepared a detailed guide on every feature, API route, and project structure. Please
visit [here](https://kasem-sm.github.io/SlimeKT) to know more about the same. Additionally, every
major folder, such as [`/api`](/api) and [`/features`](/features), contains short documentation in
the Github repository itself.

## Repository overview 📂

SlimeKT has its backend built with Ktor. The folder [`/api`](/api) consists of our backend deployed
on Heroku. SlimeKT Android application resides inside of the [`/app`](/app) folder.

## Sample, Screenshots and Demo 📱

[![Slime APK](https://img.shields.io/github/v/release/kasem-sm/slimekt?color=8D0AF2&label=Download&logo=android&style=for-the-badge)](https://github.com/kasem-sm/slimekt/releases/sample.apk)

<table>
    <tr>
        <td>
            <figure>
                <a href="#1">
                    <img src="docs/screenshots/1.png" width=300 height=400>
                </a>
                <figcaption>Home screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#2">
                    <img src="docs/screenshots/2.png" width=300 height=500>
                </a>
                <figcaption>Explore Screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#3">
                    <img src="docs/screenshots/3.png" width=300>
                </a>
                <figcaption>Detail Screen</figcaption>
            </figure>
        </td>
    </tr>
    <tr>
        <td>
            <figure>
                <a href="#4">
                    <img src="docs/screenshots/4.png" width=300>
                </a>
                <figcaption>Subscribe Topics Screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#5">
                    <img src="docs/screenshots/5.png" width=300>
                </a>
                <figcaption>Login sheet</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#6">
                    <img src="docs/screenshots/6.png" width=300>
                </a>
                <figcaption>Register sheet</figcaption>
            </figure>
        </td>
    </tr>    
    <tr>
        <td>
            <figure>
                <a href="#7">
                    <img src="docs/screenshots/7.png" width=300>
                </a>
                <figcaption>Subscribe Topics Screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#8">
                    <img src="docs/screenshots/8.png" width=300>
                </a>
                <figcaption>Login sheet</figcaption>
            </figure>
        </td>
    </tr>    
</table>

For more screenshots and screen-recording
demo, [please visit our documentation](https://kasem-sm.github.io/SlimeKT).

## What's next ✨

SlimeKT is a _Work In Progress_ project and there are several features/ideas which are yet to be
implemented. You can help us on our journey by contributing your skills. Please don't forget to
create an issue/feature request first, or pick an existing one.

## Tech stack / Miscellaneous Features 🚀

- Kotlin, Jetpack Compose, Coroutines, Flow, Accompanist, Navigation Component, Ktor (Android
  client), Room Database, Coil, Dagger Hilt, etc.
- **Observer Pattern:** Data from the cache would be shown while new data is fetched from the
  server. Newly retrieved data gets updated immediately on the screen.
- **WorkManager:** App uses WorkManager API to update user's subscriptions and to fetch Daily Read
  articles.
- **AndroidX Glance:** Daily Read Widget with the power of Jetpack Compose.
- **Material You:** On Android 12 and above, you can have custom theme based on your device
  wallpaper.

Please visit [the documentation of this project](https://kasem-sm.github.io/SlimeKT) for detailed
information.

## Project Setup Guide ⚒

### Android App

You need to have [Android Studio](https://developer.android.com/studio) Bumblebee or Dolphin to set
up this project locally. After downloading all the requirements, please import the project into
Android Studio, build the project, and click Run.

### Backend (API)

You need to have [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)
and [MongoDB Compass](https://www.mongodb.com/products/compass) installed. After downloading all the
requirements, please import the [`/api`](/api) module into IntelliJ IDEA, build the project and
click the Run icon beside `main` function in `Application.kt` file.

## Contribution Guide and Contributors 👷‍♂️

There are no special rules for contributing your expertise and making the open-source community more
powerful. Just don't forget to file an issue or start a discussion so that I may not be surprised
when you create a pull request. Running `spotlessApply` before creating a pull request would be _
cherry on the cake_.

* Special thanks to our contributors:
    - The first one gets featured here 😎
    - [All contributors](https://github.com/kasem-sm/SlimeKT/graphs/contributors)

## Medium Articles related to this project 🖋

1. When Jetpack's Glance met his fellow worker, WorkManager (draft)
2. Create Animated PlaceHolder for your Jetpack Compose text fields (draft)
3. Android’s IME Actions: Don’t ignore
   them. [Read here](https://proandroiddev.com/androids-ime-actions-don-t-ignore-them-36554da892ac)

More articles by [_kasem-sm_](https://medium.com/@kasem.sm) on Medium.

## Contact 🤙

Direct Messages on [My Twitter](https://twitter.com/KasemSM_) are always open. If you have any
questions related to SlimeKT or Android development, ping me anytime!

## Credits 💎

- [**Tivi**](https://github.com/chrisbanes/tivi) by [chrisbanes](https://github.com/chrisbanes) - A
  divine project for me to explore. I refer to this project as Gold 🥇.
- [**Gabor Varadi**](https://twitter.com/Zhuinden) - He is always willing to answer my questions. A
  great man and a blessing to the Android community (AKA, the `SavedStateHandle` preacher).
- [**Doris Liu**](https://twitter.com/doris4lt) and [**Manuel
  Vivo**](https://twitter.com/manuelvicnt) - They always help review my code snippets and add their
  value to them.

## License

```
Copyright 2022 Kasem SM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
