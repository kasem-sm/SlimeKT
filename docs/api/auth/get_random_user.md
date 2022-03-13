```http
  GET api/auth/randomAuthor 🔐
```

| Header Key      | Header Value               |
|:----------------|:---------------------------|
| `Authorization` | Bearer Token <User Token>` |


Fetches a random author to be displayed in the Explore section.

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "username": "jerry_the_author",
        "id": "484831"
    }
}
```