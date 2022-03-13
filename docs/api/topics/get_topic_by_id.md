```http
  GET api/topic/get
```

Fetches the article topic with the `id` given in the parameters. If the `userId` exists in the parameters, the server will scan and include `hasUserSubscribed` `boolean` that indicates whether the current user has subscribed to that topic or not.

| Parameter | Type     | Description                  | Required |
|:----------|----------|:-----------------------------|----------|
| `id`      | `string` | The unique ID of the article | 👍       |
| `userId`  | `string` | Current user ID              | 👎       |

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "name": "iOS",
        "totalSubscribers": 1,
        "timestamp": 1642217005570,
        "id": "61e23e2ddd9010680bc12ebd",
        "hasUserSubscribed": false
    }
}
```