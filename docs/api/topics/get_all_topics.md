```http
  GET api/topic/all
```

Fetches all the article's topics available in the database.

```
{
    "success": true,
    "additionalMessage": null,
    "data": [
        {
            "name": "iOS",
            "totalSubscribers": 1,
            "timestamp": 1642217005570,
            "id": "61e23e2ddd9010680bc12ebd"
        },
        {
            "name": "Android",
            "totalSubscribers": 1,
            "timestamp": 1642215689612,
            "id": "61e23909ece7d77eacfd9c2d"
        }
    ]
}
```