## Overview

- User's can search for articles, filter it according to their favourite author or any topic they like.
- Custom Implementation:
    - Implemented our own Pagination system. It also supports recovery from process death and is based on Single Source of Truth principle. Everything such as the page number, search query, selected topic will be restored after process death from the cache.
    - Article detail screen supports Text Markdown such as displaying quotes, code blocks, and many more.
- Daily Read Reminder notification and widget:
    - Users get a notification to read an article every 24 hours.
    - If the user is authenticated and has subscribed to any topics of their choice, server would pick a random article from their subscription.
    - If the user is not authenticated, server would pick any random article.
    - Article once shown for Daily Read would not be shown again (verified on the client side).
    - App's widget also displays the article selected for Daily Read.
    - Clicking the Daily Read notification would open the respective article.

## Demo

<figure>
    <video width="400" height="250" controls loop>
        <source src="demo.mp4" type="video/mp4">
            Something went wrong
    </video>
    <figcaption>Article List and Detail View Demo</figcaption>
</figure>

## Screenshots

<table>
    <tr>
        <td>
            <figure>
                <a href="#1">
                    <img src="detail_screen_light.png" width=300>
                </a>
                <figcaption>Detail Screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#2">
                     <img src="detail_screen_dark.png" width=300>
                </a>
            <figcaption>Detail Screen (Dark)</figcaption>
            </figure>
        </td>
    </tr>
        <tr>
        <td>
            <figure>
                <a href="#3">
                    <img src="article_list.png" width=300>
                </a>
                <figcaption>Article List Screen</figcaption>
            </figure>
        </td>
        <td>
            <figure>
                <a href="#4">
                     <img src="article_list_dark.png" width=300>
                </a>
                <figcaption>Article List Screen (Dark)</figcaption>
            </figure>
        </td>
    </tr>
</table>
