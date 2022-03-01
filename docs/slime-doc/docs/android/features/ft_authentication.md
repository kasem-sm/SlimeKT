## Overview

- Implemented proper navigation flow as guided by the official Android documentation.
- As soon as the authentication state changes:
    - User subscribed topics gets cleared from the cache.
    - Every visible screen gets refreshed data.
- User's confidential data such as Password and Unique Token is encrypted on the server as well as on the client side.
- Proper username and password validation, again on server as well as on the client side.
- User's can opt in whether to be displayed to other user's in explore section while registering themselves.
- Authentication is only required to personalize the app's content.

## Demo

<figure>
    <video width="400" height="250" controls loop>
    <source src="demo.mp4" type="video/mp4">
       Something went wrong
    </video>
    <figcaption>Authentication Demo</figcaption>
</figure>