```http
  GET api/article/get
```

Fetches the article with the given ID.

| Parameter | Type     | Description                  | Required |
|:----------|:---------|:-----------------------------|:---------|
| `id`      | `string` | The unique ID of the article | 👍       |

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "title": "Not so fancy way of writing websites",
        "description": "Here maybe a long description. This may support an inline code like `code`",
        "featuredImage": "https://miro.medium.com/max/1152/1*D0wmwNaatVN9_hxYn7eflA.png",
        "author": "Unknown",
        "topic": "Web",
        "timestamp": 1640668386898,
        "id": 1640386898
    }
}
```