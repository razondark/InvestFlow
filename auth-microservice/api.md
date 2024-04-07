#auth-microservice

### Вход

## `` POST api/v1/auth/login ``

Тело запроса

```bash
{
   
  "username": "почта или имя пользователя",
  "password": "пароль"

}
```

Ответ

```bash
{
  "id": 1,
  "username": "string"
  "acessToken": "string",
  "refreshToken": "string",
}
```

Коды ответа

| Код | Описание |
|-----| ------------- |
| 200 | ОК. Возврат токена |
| 400 | Некорректные данные |
| 401 | Пользователь незарегистрирован |


### Регистрация

## `` POST api/v1/auth/register ``

Тело запроса

```bash
{
    "name": "Yaroslav Yankov",
    "username": "yaroslav@gmail.com",
    "password": "password"
}
```

Ответ

```bash
{
  "id": 1,
  "username": "имя пользователя",
  "email": "почта пользователя",
  "password": "пароль",
  "roles": [
      "ROLE_USER"
  ]
}
```

Коды ответа

| Код | Описание |
|-----| ------------- |
| 200 | ОК. Возврат токена |
| 400 | Некорректные данные|
| 409 | Пользователь уже зарегистрирован |