# invest-microservice

# Операции
### Получение всех операций по аккаунту

## `` POST api/v1/invest/operations``

### Тело запроса

```bash
{
    "investAccountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d"
}
```

### Ответ

1. Успешное получение операций

```bash
[
      {
        "operationId": "05f05e3d-86c4-4c10-af23-a14d00716906",
        "instrumentType": "",
        "assetName": "Российский рубль",
        "figi": "RUB000UTSTOM",
        "currency": "RUB",
        "operationType": "OPERATION_TYPE_INPUT",
        "operationState": "OPERATION_STATE_EXECUTED",
        "quantity": 0,
        "payment": 100.550000000,
        "instrumentPrice": 0,
        "lotPrice": 0,
        "operationDate": "2024-03-14T12:20:04.650845"
    },
        {
        "operationId": "2d0de988-041b-4a31-8be7-8b73621daf8b",
        "instrumentType": "share",
        "assetName": "Polymetal",
        "figi": "BBG004PYF2N3",
        "currency": "rub",
        "operationType": "OPERATION_TYPE_BUY",
        "operationState": "OPERATION_STATE_EXECUTED",
        "quantity": 2,
        "payment": -704.800000000,
        "instrumentPrice": 352.400000000,
        "lotPrice": 352.400000000,
        "operationDate": "2024-03-13T22:33:41.833999"
    },
        {
        "operationId": "875c7a38-49f8-43d1-b6df-c638c970fc34",
        "instrumentType": "share",
        "assetName": "КАМАЗ",
        "figi": "BBG000LNHHJ9",
        "currency": "rub",
        "operationType": "OPERATION_TYPE_BUY",
        "operationState": "OPERATION_STATE_EXECUTED",
        "quantity": 30,
        "payment": -5508.000000000,
        "instrumentPrice": 183.600000000,
        "lotPrice": 1836.000000000,
        "operationDate": "2024-03-14T11:54:44.713586"
    }
]
```

2. Ошибка в получении операций

```bash
{
    "message": "Ошибка получения операций по счёту."
}
```

| Параметр       | Описание                                                           | Тип данных  |
|----------------|--------------------------------------------------------------------|-------------|  
| operationId    | Уникальный идентификатор операции                                  | String      |
| instrmentType  | Название инструмента. Варианты: "share", "bond", "etf", "currency" | String      |
| assetName      | Название актива                                                    | String      |
| figi           | Figi инструмента                                                   | String      |
| currency       | Валюта                                                             | String      |
| operationType  | Вид операции (пополнение, покупка, продажа и тд)                   | String      |
| operationState | Состояние операции (выполнена, активна и тд)                       | String      |
| quantity       | Количество активов в операции                                      | integer     |
| payment        | Сумма операции (+ пополнение, - покупка/вывод)                     | BigDecimal  |
| instrumentPrice | Цена инструмента                                                   | BigDecimal  |
| lotPrice       | Цена за лот                                                        | BigDecimal  |
| operationDate  | Дата совершения операции                                           | String      |

| Код | Описание              |
|-----|-----------------------|
| 200 | ОК                    |
| 400 | Элемент не существует |

---
# Акции
### Покупка акций
## `` POST api/v1/invest/stocks/buy``

### Тело запроса

```bash
{
    "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d",
    "figi": "TCS00A106YF0",
    "lot": 1,
    "orderType": "ORDER_TYPE_BESTPRICE"
}
```

### Ответ

1. Успешная покупка акции

```bash
{
    "orderId": "7781fc18-d501-4949-8537-a864dff46e7b",
    "executionStatus": "EXECUTION_REPORT_STATUS_FILL",
    "lotRequested": 1,
    "lotExecuted": 1,
    "asset": {
        "ticker": "VKCO",
        "figi": "TCS00A106YF0",
        "name": "ВК",
        "sector": "it",
        "currency": "rub",
        "countryOfRiskName": "Российская Федерация",
        "lots": 1
    },
    "initialOrderPrice": 637.600000000,
    "executedOrderPrice": 637.600000000,
    "totalOrderPrice": 637.600000000
}
```

2. Ошибка при невалидном accountId из запроса

```bash
{
    "message": "Счёт не найден по переданному account_id.Укажите корректный account_id."
}
```

3. Ошибка при невалидном figi из запроса

```bash
{
    "message": "Инструмент не найден.Укажите корректный идентификатор инструмента."
}
```

### Поля запроса/ответа

1. Запрос

| Параметр  | Описание                                           | Тип данных |
|-----------|----------------------------------------------------|------------|  
| accountId | Уникальный идентификатор аккаунта пользователя     | String     |
| figi      | Figi инструмента                                   | String     |
| lot       | Колличество лотов для покупки                      | integer    |
| orderType | Вид заявки (по лучшей цене, по рыночной, лимитная) | String     |

2. Ответ

| Параметр          | Описание                             | Тип данных |
|-------------------|--------------------------------------|------------|  
| orderId           | Уникальный идентификатор заявки      | String     |
| excecutionStatus  | Текущий статус заявки                | String     |
| assetName         | Название актива                      | String     |
| lotRequested      | Запрошено лотов на покупку           | integer    |
| lotExecuted       | Колличество исполненных лотов        | integer    |
| asset             | Информация об инструменте (акции)    | object     |
| ticker            | Тикер акции                          | String     |
| figi              | Фиги акции                           | String     |
| name              | Название акции                       | String     |
| sector            | Сектор акции (энергетика, it и тд)   | String     |
| currency          | Валюта акции                         | String     |
| countryOfRiskName | Старна, где акция зарегестрированна  | String     |
| lots              | Лотность акции (1, 10, 10000 и т.п.) | integer    |
| initialOrderPrice       | Начальная цена заявки                | BigDecimal |
| executedOrderPrice      | Выполненная цена заявки              | BigDecimal |
| totalOrderPrice       | Общая стоимость заявки               | BigDecimal |

| Код  | Описание              |
|------|-----------------------|
| 200  | ОК                    |
| 400  | Элемент не существует |
| 404  | Не найдено            |

---

### Продажа акций
## `` POST api/v1/invest/stocks/sale``

### Тело запроса

```bash
{
    "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d",
    "figi": "TCS00A106YF0",
    "lot": 1,
    "orderType": "ORDER_TYPE_BESTPRICE"
}
```

### Ответ

2. Успешная продажа акции

```bash
{
  "orderId": "string",
  "executionStatus": "string",
  "lotRequested": 0,
  "lotExecuted": 0,
  "asset": {
    "ticker": "string",
    "figi": "string",
    "name": "string",
    "sector": "string",
    "currency": "string",
    "countryOfRiskName": "string",
    "lots": 0,
    "price": 0,
    "brandLogo": {
      "logo": "string",
      "logoColor": "string",
      "textColor": "string",
      "logoUrl": "string"
    }
  },
  "initialOrderPrice": 0,
  "executedOrderPrice": 0,
  "totalOrderPrice": 0
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации об акции по имени
## `` GET api/v1/invest/stocks?name=<value>``

### Ответ

1. Получение информации об акции

```bash
[
  {
    "ticker": "YNDX",
    "figi": "BBG006L8G4H1",
    "name": "Яндекс",
    "sector": "it",
    "currency": "rub",
    "countryOfRiskName": "Королевство Нидерландов",
    "lots": 1,
    "price": 3829.4,
    "brandLogo": {
      "logo": "NL000980552.png",
      "logoColor": "#ff0000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/NL000980552x320.png"
    }
  },
  {
    "ticker": "YNDX@US",
    "figi": "TCS609805522",
    "name": "Яндекс",
    "sector": "it",
    "currency": "usd",
    "countryOfRiskName": "Королевство Нидерландов",
    "lots": 1,
    "price": 21.47,
    "brandLogo": {
      "logo": "NL000980552.png",
      "logoColor": "#ff0000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/NL000980552x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```


### Получение информации об акции
## `` GET api/v1/invest/stocks/sector/<str value>?count=<int value>``

## ``<str value>`` - название сектора на русском языке (с сайта tinkoff invest)
### Ответ

1. Получение информации об акции по сектору

```bash
[
  {
    "ticker": "YNDX",
    "figi": "BBG006L8G4H1",
    "name": "Яндекс",
    "sector": "it",
    "currency": "rub",
    "countryOfRiskName": "Королевство Нидерландов",
    "lots": 1,
    "price": 3829.4,
    "brandLogo": {
      "logo": "NL000980552.png",
      "logoColor": "#ff0000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/NL000980552x320.png"
    }
  },
  {
    "ticker": "YNDX@US",
    "figi": "TCS609805522",
    "name": "Яндекс",
    "sector": "it",
    "currency": "usd",
    "countryOfRiskName": "Королевство Нидерландов",
    "lots": 1,
    "price": 21.47,
    "brandLogo": {
      "logo": "NL000980552.png",
      "logoColor": "#ff0000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/NL000980552x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации о дивидендах акции
## `` GET api/v1/invest/stocks/dividends?figi=<figi>``

### Ответ

1. Получение информации о дивидендах

```bash
{
  "figi": "BBG004730RP0",
  "dividends": [
    {
      "date": "2022-10-07T03:00:00",
      "paymentPerShare": 51.03,
      "currency": "rub",
      "interestIncome": 25.34
    },
    {
      "date": "2021-07-13T03:00:00",
      "paymentPerShare": 12.55,
      "currency": "rub",
      "interestIncome": 4.26
    },
    {
      "date": "2020-07-14T03:00:00",
      "paymentPerShare": 15.24,
      "currency": "rub",
      "interestIncome": 7.83
    } 
  ]
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение списка акций
## `` GET api/v1/invest/stocks/all?page=<value>&count=<value>``

### page - страница (от 1 до ...)
### count - количество отображаемых акций (от 1 до ...)

### Ответ

1. Получение информации об акциях

```bash
[
  {
    "ticker": "APA",
    "figi": "BBG00YTS96G2",
    "name": "APA Corporation",
    "sector": "energy",
    "currency": "usd",
    "countryOfRiskName": "Соединенные Штаты Америки",
    "lots": 1,
    "price": 40,
    "brandLogo": {
      "logo": "US0374111054.png",
      "logoColor": "#3d7771",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/US0374111054x320.png"
    }
  },
  {
    "ticker": "CNX",
    "figi": "BBG000CKVSG8",
    "name": "CNX Resources Corporation",
    "sector": "energy",
    "currency": "usd",
    "countryOfRiskName": "Соединенные Штаты Америки",
    "lots": 1,
    "price": 22.72,
    "brandLogo": {
      "logo": "US12653C1080.png",
      "logoColor": "#000000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/US12653C1080x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```
# Облигации
### Продажа облигации
## `` POST api/v1/invest/bonds/sale``

### Тело запроса

```bash
{
    "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d",
    "figi": "BBG00NTXZ6S3",
    "lot": 1,
    "orderType": "ORDER_TYPE_BESTPRICE"
}
```

### Ответ

2. Успешная продажа облигации

```bash
{
  "orderId": "string",
  "executionStatus": "string",
  "lotRequested": 0,
  "lotExecuted": 0,
  "asset": {
    "ticker": "string",
    "figi": "string",
    "name": "string",
    "lots": 0,
    "currency": "string",
    "couponQuantityPerYear": 0,
    "maturityDate": "2024-03-24T23:03:21.549Z",
    "placementPrice": 0,
    "countryOfRiskName": "string",
    "sector": "string",
    "riskLevel": "RISK_LEVEL_UNSPECIFIED",
    "price": 0,
    "brandLogo": {
      "logo": "string",
      "logoColor": "string",
      "textColor": "string",
      "logoUrl": "string"
    }
  },
  "initialOrderPrice": 0,
  "executedOrderPrice": 0,
  "totalOrderPrice": 0
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Покупка облигации
## `` POST api/v1/invest/bonds/buy``

### Тело запроса

```bash
{
    "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d",
    "figi": "BBG00NTXZ6S3",
    "lot": 1,
    "orderType": "ORDER_TYPE_BESTPRICE"
}
```

### Ответ

2. Успешная покупка облигации

```bash
{
  "orderId": "string",
  "executionStatus": "string",
  "lotRequested": 0,
  "lotExecuted": 0,
  "asset": {
    "ticker": "string",
    "figi": "string",
    "name": "string",
    "lots": 0,
    "currency": "string",
    "couponQuantityPerYear": 0,
    "maturityDate": "2024-03-24T23:04:52.606Z",
    "placementPrice": 0,
    "countryOfRiskName": "string",
    "sector": "string",
    "riskLevel": "RISK_LEVEL_UNSPECIFIED",
    "price": 0,
    "brandLogo": {
      "logo": "string",
      "logoColor": "string",
      "textColor": "string",
      "logoUrl": "string"
    }
  },
  "initialOrderPrice": 0,
  "executedOrderPrice": 0,
  "totalOrderPrice": 0
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации об облигациях по имени
## `` GET api/v1/invest/bonds?name=<value>``

### Ответ

1. Получение информации об облигациях

```bash
[
  {
    "ticker": "RU000A1008V9",
    "figi": "BBG00NTXZ6S3",
    "name": "Роснано выпуск 8",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 2,
    "maturityDate": "2028-03-27T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "financial",
    "riskLevel": "RISK_LEVEL_MODERATE",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A100ER0.png",
      "logoColor": "#FAB837",
      "textColor": "#000000",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A100ER0x320.png"
    }
  },
  {
    "ticker": "RU000A101KK0",
    "figi": "BBG00SXFWRX7",
    "name": "Роснано БО-002P выпуск 4",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 4,
    "maturityDate": "2024-03-26T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "financial",
    "riskLevel": "RISK_LEVEL_HIGH",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A100ER0.png",
      "logoColor": "#FAB837",
      "textColor": "#000000",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A100ER0x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```


### Получение информации об облигации по сектору
## `` GET api/v1/invest/bonds/sector/<str value>?count=<int value>``

## ``<str value>`` - название сектора на русском языке (с сайта tinkoff invest)
### Ответ

1. Получение информации об облигации по сектору

```bash
[
  {
    "ticker": "RU000A1068S9",
    "figi": "TCS00A1068S9",
    "name": "Лайфстрим БО-П02",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 4,
    "maturityDate": "2028-05-11T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "it",
    "riskLevel": "RISK_LEVEL_MODERATE",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A102QX8.png",
      "logoColor": "#000000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A102QX8x320.png"
    }
  },
  {
    "ticker": "RU000A107548",
    "figi": "TCS01A107548",
    "name": "КИВИ Финанс 001Р-02",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 4,
    "maturityDate": "2027-10-26T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "it",
    "riskLevel": "RISK_LEVEL_HIGH",
    "price": 1000,
    "brandLogo": {
      "logo": "US74735M1080.png",
      "logoColor": "#0023A0",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/US74735M1080x320.png"
    }
  },
  {
    "ticker": "RU000A1071R5",
    "figi": "TCS00A1071R5",
    "name": "Т1 001Р-01",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 4,
    "maturityDate": "2025-10-14T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "it",
    "riskLevel": "RISK_LEVEL_LOW",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A1071R5.png",
      "logoColor": "#FFFFFF",
      "textColor": "#000000",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A1071R5x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации о купонах облигации
## `` GET api/v1/invest/bonds/dividends?figi=<figi>``

### Ответ

1. Получение информации о купонах

```bash
{
  "figi": "BBG00NTXZ6S3",
  "nextPaymentDate": "2024-04-01T03:00:00",
  "couponsPaid": 9,
  "couponsCount": 18,
  "dividends": [
    {
      "couponDate": "2028-03-27T03:00:00",
      "accumulatedCouponIncome": 64.39,
      "couponNumber": 18,
      "payment": 0,
      "currency": "",
      "interestIncome": 0
    },
    {
      "couponDate": "2027-09-27T03:00:00",
      "accumulatedCouponIncome": 64.39,
      "couponNumber": 17,
      "payment": 0,
      "currency": "",
      "interestIncome": 0
    },
    {
      "couponDate": "2027-03-29T03:00:00",
      "accumulatedCouponIncome": 64.39,
      "couponNumber": 16,
      "payment": 0,
      "currency": "",
      "interestIncome": 0
    }
  ]
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение списка облигаций
## `` GET api/v1/invest/stocks/all?page=<value>&count=<value>``

### page - страница (от 1 до ...)
### count - количество отображаемых акций (от 1 до ...)

### Ответ

1. Получение информации об облигациях

```bash
[
  {
    "ticker": "RU000A105WZ4",
    "figi": "TCS00A105WZ4",
    "name": "Лизинг-Трейд 1P-07",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 12,
    "maturityDate": "2028-02-09T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "financial",
    "riskLevel": "RISK_LEVEL_HIGH",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A101CB6.png",
      "logoColor": "#000000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A101CB6x320.png"
    }
  },
  {
    "ticker": "RU000A101228",
    "figi": "BBG00QXGFHS6",
    "name": "МОЭК выпуск 3",
    "lots": 1,
    "currency": "rub",
    "couponQuantityPerYear": 2,
    "maturityDate": "2025-11-07T03:00:00",
    "placementPrice": 1000,
    "countryOfRiskName": "Российская Федерация",
    "sector": "energy",
    "riskLevel": "RISK_LEVEL_MODERATE",
    "price": 1000,
    "brandLogo": {
      "logo": "RU000A101XS6.png",
      "logoColor": "#000000",
      "textColor": "#ffffff",
      "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A101XS6x320.png"
    }
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```

# Аккаунты
### Получение информации об аккаунтах (hardcode, not working now)
## `` GET api/v1/invest/accounts``

### Ответ

1. Получение аккаунта

```bash
{
  "investAccountId": "string",
  "userDto": {
    "id": 0,
    "name": "string",
    "username": "string",
    "password": "string",
    "roles": [
      "ROLE_USER"
    ]
  }
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Изменение баланса
## `` PUT api/v1/invest/accounts``
### Тело запроса

```bash
{
  "accountId": "string",
  "moneyToPay": 0
}
```
### Ответ

1. Успешное пополнение баланса

```bash
{
  "balance": 0,
  "addedMoney": 0
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

## TODO: change
### Открытие аккаунта песочницы
## `` PUT api/v1/invest/accounts/<userid>``

### Ответ

1. Успешное создание аккаунта

```bash
{
  "investAccountId": "string",
  "userDto": {
    "id": 0,
    "name": "string",
    "username": "string",
    "password": "string",
    "roles": [
      "ROLE_USER"
    ]
  }
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

# Портфолио
### Получение информации о портфолио пользователя
## `` POST api/v1/invest/portfolios``
Тело запроса
```bash
{
  "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d"
}
```

### Ответ

1. Получение портфеля

```bash
{
  "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d",
  "expectedYield": -0.9007,
  "totalAmountShares": 6188095.1,
  "totalAmountBonds": 1714.4,
  "totalAmountCurrencies": 163257412.88,
  "totalAmountPortfolio": 169447222.38,
  "positions": [
    {
      "instrumentType": "bond",
      "asset": {
        "ticker": "RU000A1008V9",
        "figi": "BBG00NTXZ6S3",
        "name": "Роснано выпуск 8",
        "lots": 1,
        "currency": "rub",
        "couponQuantityPerYear": 2,
        "maturityDate": "2028-03-27T03:00:00",
        "placementPrice": 1000,
        "countryOfRiskName": "Российская Федерация",
        "sector": "financial",
        "riskLevel": "RISK_LEVEL_MODERATE",
        "price": 1000,
        "brandLogo": {
          "logo": "RU000A100ER0.png",
          "logoColor": "#FAB837",
          "textColor": "#000000",
          "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A100ER0x320.png"
        }
      },
      "averagePositionPrice": 500000,
      "expectedYield": 0,
      "currentPrice": 857.2,
      "currency": "rub",
      "quantity": 2,
      "totalPrice": 1714.4
    },
    {
      "instrumentType": "share",
      "asset": {
        "ticker": "KMAZ",
        "figi": "BBG000LNHHJ9",
        "name": "КАМАЗ",
        "sector": "industrials",
        "currency": "rub",
        "countryOfRiskName": "Российская Федерация",
        "lots": 10,
        "price": null,
        "brandLogo": {
          "logo": "RU0008959580.png",
          "logoColor": "#0D53A0",
          "textColor": "#ffffff",
          "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU0008959580x320.png"
        }
      },
      "averagePositionPrice": 183.433333,
      "expectedYield": 0,
      "currentPrice": 178.1,
      "currency": "rub",
      "quantity": 90,
      "totalPrice": 16029
    }
  ]
}
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации о валютах пользователя
## `` PUT api/v1/invest/portfolios/withdraw``
### Тело запроса

```bash
{
  "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d"
}
```
### Ответ

1. Возврат информации о валютах пользователя

```bash
[
  {
    "currency": "usd",
    "amount": 1670100
  },
  {
    "currency": "rub",
    "amount": 8280483.38
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```

### Получение информации о ценных бумагах пользователя
## `` PUT api/v1/invest/portfolios/positions``
### Тело запроса

```bash
{
  "accountId": "a7c911bb-5b01-41bf-9db7-3767ac46385d"
}
```
### Ответ

1. Возврат информации о валютах пользователя

```bash
[
  {
    "instrumentType": "bond",
    "asset": {
      "ticker": "RU000A1008V9",
      "figi": "BBG00NTXZ6S3",
      "name": "Роснано выпуск 8",
      "lots": 1,
      "currency": "rub",
      "couponQuantityPerYear": 2,
      "maturityDate": "2028-03-27T03:00:00",
      "placementPrice": 1000,
      "countryOfRiskName": "Российская Федерация",
      "sector": "financial",
      "riskLevel": "RISK_LEVEL_MODERATE",
      "price": 1000,
      "brandLogo": {
        "logo": "RU000A100ER0.png",
        "logoColor": "#FAB837",
        "textColor": "#000000",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU000A100ER0x320.png"
      }
    },
    "averagePositionPrice": 500000,
    "expectedYield": 0,
    "currentPrice": 857.2,
    "currency": "rub",
    "quantity": 2,
    "totalPrice": 1714.4
  },
  {
    "instrumentType": "currency",
    "asset": {
      "name": "Доллар США",
      "currency": "rub",
      "figi": "BBG0013HGFT4",
      "ticker": "USD000UTSTOM",
      "countryOfRiskName": "",
      "brandLogo": {
        "logo": "USD1.png",
        "logoColor": "#F6F8FA",
        "textColor": "#000000",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/USD1x320.png"
      }
    },
    "averagePositionPrice": 0,
    "expectedYield": 0,
    "currentPrice": 92.795,
    "currency": "rub",
    "quantity": 1670100,
    "totalPrice": 154976929.5
  },
  {
    "instrumentType": "currency",
    "asset": {
      "name": "Российский рубль",
      "currency": "rub",
      "figi": "RUB000UTSTOM",
      "ticker": "RUB000UTSTOM",
      "countryOfRiskName": "",
      "brandLogo": {
        "logo": "ruble.png",
        "logoColor": "#000000",
        "textColor": "#ffffff",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/rublex320.png"
      }
    },
    "averagePositionPrice": 0,
    "expectedYield": 0,
    "currentPrice": 0,
    "currency": "",
    "quantity": 8280483,
    "totalPrice": 0
  },
  {
    "instrumentType": "share",
    "asset": {
      "ticker": "POLY",
      "figi": "BBG004PYF2N3",
      "name": "Polymetal",
      "sector": "materials",
      "currency": "rub",
      "countryOfRiskName": "",
      "lots": 1,
      "price": null,
      "brandLogo": {
        "logo": "JE00B6T5S470.png",
        "logoColor": "#EEEEEE",
        "textColor": "#000000",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/JE00B6T5S470x320.png"
      }
    },
    "averagePositionPrice": 352.957594,
    "expectedYield": 0,
    "currentPrice": 324.5,
    "currency": "rub",
    "quantity": 19009,
    "totalPrice": 6168420.5
  },
  {
    "instrumentType": "share",
    "asset": {
      "ticker": "VKCO",
      "figi": "TCS00A106YF0",
      "name": "ВК",
      "sector": "it",
      "currency": "rub",
      "countryOfRiskName": "Российская Федерация",
      "lots": 1,
      "price": null,
      "brandLogo": {
        "logo": "vkc.png",
        "logoColor": "#000000",
        "textColor": "#ffffff",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/vkcx320.png"
      }
    },
    "averagePositionPrice": 626.2,
    "expectedYield": 0,
    "currentPrice": 607.6,
    "currency": "rub",
    "quantity": 6,
    "totalPrice": 3645.6
  },
  {
    "instrumentType": "share",
    "asset": {
      "ticker": "KMAZ",
      "figi": "BBG000LNHHJ9",
      "name": "КАМАЗ",
      "sector": "industrials",
      "currency": "rub",
      "countryOfRiskName": "Российская Федерация",
      "lots": 10,
      "price": null,
      "brandLogo": {
        "logo": "RU0008959580.png",
        "logoColor": "#0D53A0",
        "textColor": "#ffffff",
        "logoUrl": "https://invest-brands.cdn-tinkoff.ru/RU0008959580x320.png"
      }
    },
    "averagePositionPrice": 183.433333,
    "expectedYield": 0,
    "currentPrice": 178.1,
    "currency": "rub",
    "quantity": 90,
    "totalPrice": 16029
  }
]
```
2. Ошибки

```bash
{
  "message": "string"
}
```