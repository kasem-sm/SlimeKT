## Overview

- Users can search for articles and filter them according to their favorite author or any topic they like.
- Custom Implementation:
    - **Article Detail Screen Supports Text Markdown** such as displaying quotes, code blocks, and many more.
- Daily Read Reminder notification and widget:
    - Users get a notification to read an article every 24 hours.
    - If the user is authenticated and has subscribed to any topics of their choice, the server will pick a random article from their subscription.
    - If the user is unauthenticated, the server will pick any random article.
    - Article once shown for Daily Read would not be shown again (verified on the client-side).
    - The app's widget also displays the article selected for the Daily Read.
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
