```http
  POST api/subscriptionService/subscribeIfNot 🔐
```

| Header Key      | Header Value               |
|:----------------|:---------------------------|
| `Authorization` | Bearer Token <User Token>` |


| Parameter | Type     | Description                             | Required |
|:----------|----------|:----------------------------------------|----------|
| `topicId` | `string` | The unique ID of the topic to subscribe | 👍       |


Subscribe to the topic if the user has not. If the user has already subscribed, API would unsubscribe from it.

```
{
    "success": true,
    "additionalMessage": "Subscribed",
    "data": "61e23909ece7d77eacfd9c2d" // The topicID
}
```