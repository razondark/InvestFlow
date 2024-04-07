# crud-microservice

### Регистрация

## `` POST api/v1/crud/users ``

Тело запроса

```bash
{
  "name": "имя",
  "username": "почта или имя пользователя",
  "password": "пароль"

}
```

Ответ

```bash
{
  "id": 3,
  "name": "Vera"
  "username": "tupayavera",
  "password": "2fasdads=321312312fasdf"б
  "roles": [
    "ROLE_USER"
  ]
}
```

Коды ответа

| Код | Описание  |
|-----|-----------|
| 200 | Успешно   |
| 409 | Неуспешно |


### Вход

## `` POST api/v1/crud/users/login ``

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
  "id": 3,
  "name": "Vera"
  "username": "tupayavera",
  "password": "2fasdads=321312312fasdf"б
  "roles": [
    "ROLE_USER"
  ]
}
```

Коды ответа

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |
| 404 | Пользователь не найден |

### Обновление пользовательских данных

## `` PUT api/v1/crud/users ``

Тело запроса

```bash
{
  "id": 1
  "name": "имя"
  "username": "почта или имя пользователя",
  "password": "пароль в захешированном виде"
}
```

Ответ

```bash
{
  "id": 3,
  "name": "Vera"
  "username": "tupayavera",
  "password": "2fasdads=321312312fasdf"б
  "roles": [
    "ROLE_USER", "ROLE_ADMIN"
  ]
}
```

Коды ответа

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |
| 404 | Пользователь не найден |


### Получение пользователя по id

## `` GET api/v1/crud/users/{id} ``

Параметры

userId | Идентефикатор пользователя 

Пример запроса
`` GET api/v1/crud/users/1  ``

Ответ

```bash
{
  "id": 1,
  "name": "Vera"
  "username": "tupayavera",
  "password": "2fasdads=321312312fasdf"б
  "roles": [
    "ROLE_USER"
  ]
}
```

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |
| 404 | Пользователь не найден |

### Получение пользователя по username

## `` GET api/v1/crud/users?username={username} ``

Параметры

username | Username пользователя

Пример запроса
`` GET api/v1/crud/users?username=Vera  ``

Ответ

```bash
{
  "id": 3,
  "name": "Vera"
  "username": "tupayavera",
  "password": "2fasdads=321312312fasdf"б
  "roles": [
    "ROLE_USER"
  ]
}
```

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |
| 404 | Пользователь не найден |


### Удаление пользователя по id

## `` DELETE api/v1/crud/users/{id} ``

Параметры

username | Username пользователя

Пример запроса
`` GET api/v1/crud/users/1  ``

Ответ

```bash
{
  "message": "Пользователь успешно удалён"
}
```

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |



### Создание акаунта в инвестициях

## `` POST api/v1/crud/accounts``

Тело запроса

```bash
{
  "investAccountId": "id"
  "user": {
    "id": 1
  },
  "balance": 0.0
}
```

Ответ

Body
```bash
{
  "investAccountId": "id"
  "user": {
    "id": 1,
    "name": "имя",
    "username": "username",
    "password": "пароль в хеше",
    "roles": [
        "ROLE_USER"
    ]
  },
  "balance": 0.0
}
```
Header

```bash
{
  "error-message": "Сообщение об ошибке"
}
```

Коды ответа

| Код | Описание  |
|-----|-----------|
| 200 | Успешно   |
| 409 | Неуспешно |

### Обновление аккаунта пользователя

## `` PUT api/v1/crud/accounts ``

Тело запроса

```bash
{
  "investAccountId": "id"
  "user": {
    "id": 1
  },
  "balance": 510.30
}
```

Ответ

```bash
{
  "investAccountId": "id"
  "user": {
    "id": 1,
    "name": "имя",
    "username": "username",
    "password": "пароль в хеше",
    "roles": [
        "ROLE_USER"
    ]
  },
  "balance": 510.30
}
```

Коды ответа

| Код | Описание               |
|-----|------------------------|
| 200 | Успешно                |


### Получение аккаунта по id

## `` GET api/v1/crud/users/{accountId} ``

Параметры

accountId | Идентефикатор аккаунта

Пример запроса
`` GET api/v1/crud/users/232321-312adsfd-fadf1-sfsdffa  ``

Ответ


Body
```bash
{
  "investAccountId": "232321-312adsfd-fadf1-sfsdffa"
  "user": {
    "id": 1,
    "name": "имя",
    "username": "username",
    "password": "пароль в хеше",
    "roles": [
        "ROLE_USER"
    ]
  },
  "balance": 510.30
}
```

Header

```bash
{
  "error-message": "Сообщение об ошибке"
}
```

| Код | Описание          |
|-----|-------------------|
| 200 | Успешно           |
| 409 | Аккаунт не найден |

