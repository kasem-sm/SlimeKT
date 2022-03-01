```http
  GET api/subscriptionService/all
```

Fetches all the **subscribed** topics of the current User. If the `userId` is not passed in the parameters, an empty list would be retrieved.


| Parameter | Type     | Description         | Required | Default Value |
|:----------|:---------|:--------------------|:---------|:--------------|
| `userId`  | `string` | The Current User ID | 👎       | `null`        |

```
{
    "success": true,
    "additionalMessage": null,
    "data": [
        {
            "name": "iOS",
            "totalSubscribers": 1,
            "hasUserSubscribed": true,
            "timestamp": 1642217005570,
            "id": "61e23e2ddd9010680bc12ebd"
        },
        {
            "name": "Technology",
            "totalSubscribers": 1,
            "hasUserSubscribed": true,
            "timestamp": 1642300794226,
            "id": "61e3857af680350cc7e66bc4"
        },
        {
            "name": "Web",
            "totalSubscribers": 1,
            "hasUserSubscribed": true,
            "timestamp": 1642217010923,
            "id": "61e23e32dd9010680bc12ebf"
        },
    ]
}
```