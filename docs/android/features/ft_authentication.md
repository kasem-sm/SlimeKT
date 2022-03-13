## Overview

- Implemented proper navigation flow as guided by the official Android documentation.
- As soon as the authentication state changes:
    - User subscribed topics get cleared from the cache.
    - Every visible screen gets refreshed with updated data.
- User's confidential data, such as passwords and unique tokens, is encrypted on the server as well as on the client-side.
- Proper username and password validation, again on the server as well as on the client-side.
- Users can opt-in to whether they want to be displayed to other users in the explore section while registering themselves.
- Authentication is only required to personalize the app's content.

## Demo

<figure>
    <video width="400" height="250" controls loop>
    <source src="demo.mp4" type="video/mp4">
       Something went wrong
    </video>
    <figcaption>Authentication Demo</figcaption>
</figure>