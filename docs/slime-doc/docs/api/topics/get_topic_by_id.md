```http
  GET api/topic/get
```

Fetches the topic with the given ID. If `userId` is provided in the parameter, the server would scan and also include `hasUserSubscribed` `boolean` which indicates whether the user has subscribed to that topic or not.

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