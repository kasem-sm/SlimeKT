```http
  GET api/subscriptionService/explore
```

Fetches all the **unsubscribed** topics of the current User. If the `userId` is not passed in the parameters, the request would redirect to [Get All Topics](http://127.0.0.1:8000/SlimeKT/api/topics/get_all_topics/) route.


| Parameter | Type     | Description         | Required | Default Value |
|:----------|:---------|:--------------------|:---------|:--------------|
| `userId`  | `string` | The Current User ID | 👎       | `null`        |

```
{
    "success": true,
    "additionalMessage": null,
    "data": [
        {
            "name": "Politics",
            "totalSubscribers": 1,
            "hasUserSubscribed": false,
            "timestamp": 1642217005570,
            "id": "61e23e2ddd9010680bc12ebd"
        },
        {
            "name": "Animation",
            "totalSubscribers": 1,
            "hasUserSubscribed": false,
            "timestamp": 1642300794226,
            "id": "61e3857af680350cc7e66bc4"
        },
        {
            "name": "Science",
            "totalSubscribers": 1,
            "hasUserSubscribed": false,
            "timestamp": 1642217010923,
            "id": "61e23e32dd9010680bc12ebf"
        },
    ]
}
```