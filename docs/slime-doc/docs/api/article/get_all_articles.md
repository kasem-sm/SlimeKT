```http
  GET api/article/all
```

Fetches all the articles in paged form.

| Parameter  | Type     | Description                       | Required | Default Value |
|:-----------|:---------|:----------------------------------|:---------|:--------------|
| `topic`    | `string` | Filter article by the given topic | 👎       | Empty         |
| `query`    | `string` | Filter article by the given query | 👎       | Empty         |
| `page`     | `string` | Page number to retrieve           | 👎       | 0             |
| `pageSize` | `string` | Number of items per page          | 👎       | 3             |

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "info": {
            "articleSize": 22,
            "totalPages": 7,
            "prevPage": null,
            "nextPage": 1
        },
        "articles": [
            {
                "title": "I use an outdated iPhone 12",
                "description": "Does it really matters? *I guess __no__*",
                "featuredImage": "https://images.pexels.com/photos/10768569/pexels-photo-10768569.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "author": "Kasem S.M",
                "topic": "iOS",
                "timestamp": 1644302818957,
                "id": 1644302818
            }
        ]
    }
}
```