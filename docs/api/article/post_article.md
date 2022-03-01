```http
  POST api/article/create 🔐
```

| Header Key      | Header Value               |
|:----------------|:---------------------------|
| `Authorization` | Bearer Token <User Token>` |

Creates a new Article provided that the user is authenticated. If the user passes a topic name which doesn't exists, the server would automatically create a new topic by that name.

Create Request:
```
{
    "title": "the title of your article",
    "description": "long __description__ that supports *markdown*",
    "featuredImage": "thumbnail_img_url.jpg",
    "author": "writer's name",
    "topic": "topic/category name"
}
```