#### Register a new user

```http
  POST api/auth/register
```

| Parameter      | Type     | Description                         | Required |
|:---------------|:---------|:------------------------------------|:---------|
| `username`     | `string` | Your username                       | 👍       | 
| `password`     | `string` | Your password                       | 👍       | 
| `discoverable` | `0 or 1` | Account discoverable to other users | 👍       | 

#### Login an existing user

```http
  POST api/auth/login
```

| Parameter      | Type     | Description                         | Required |
|:---------------|:---------|:------------------------------------|:---------|
| `username`     | `string` | Registered username                 | 👍       |
| `password`     | `string` | Registered password                 | 👍       |

```
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "userId": "123456",
        "username": "jhon",
        "token": "09876543210987654321"
    }
}
```
