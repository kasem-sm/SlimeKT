```http
  GET /api/auth/authenticate 🔐
```

| Parameter      | Type     | Description                         | Required |
|:---------------|:---------|:------------------------------------|:---------|
| `userId`       | `string` | The Current User ID                 | 👎       | 


It verifies if the user still exists in our database.

```
{
    "success": true,
    "additionalMessage": null,
    "data": true/false
}
```