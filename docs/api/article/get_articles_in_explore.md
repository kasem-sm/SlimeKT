```http
  GET api/article/explore
```

Fetches four Articles of any topics that are not yet subscribed by the user. 

```
{
    "success": true,
    "additionalMessage": null,
    "data": [
        {
            "title": "I use an outdated iPhone 12",
            "description": "Does it really matters? *I guess __no__*",
            "featuredImage": "https://images.pexels.com/photos/10768569/pexels-photo-10768569.jpeg?auto=compress&",        
            "author": "Kasem S.M", 
            "topic": "iOS",
            "timestamp": 1644302818957,
            "id": 1644302818
        },
    ]
}
```