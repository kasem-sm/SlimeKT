```http
  POST api/article/create 🔐
```

| Header Key      | Header Value               |
|:----------------|:---------------------------|
| `Authorization` | Bearer Token <User Token>` |

It creates a new article provided that the user is authenticated. If the user passes a topic name that doesn't exist, the server will automatically create a new topic with that name.
Currently this is disabled in production to avoid abuse.

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