...................................................................
1 Регистрация нового пользователя
GET:     localhost:9966/api/v1/cryptoMarket/register
HEADERS:
(key)    (value)
"username" : "Maxim"
"email" : "Maxim@mail.ru"

Response:  oV!2z@aT
....................................................................
2 Просмотр баланса своего кошелька
GET: localhost:9966/api/v1/cryptoMarket/seeBalance
HEADERS:
(key)        (value)
"secret_key" : "nGCYrUfz"

Response:  {
"TON_wallet": 5000,
"BTC_wallet": 0.002,
"RUB_wallet": 25000
}
......................................................................
3 Пополнение кошелька
POST: localhost:9966/api/v1/cryptoMarket/topUpTheBalance
HEADERS:
(key)      (value)
"secret_key" : "nGCYrUfz"
"RUB_wallet" : "2121"

Response:{
"RUB_balance": 43863.920000
}
...................................................................... 
4 Вывод денег с биржи
POST: localhost:9966/api/v1/cryptoMarket/withdraw
HEADERS:
"secret_key" : "mH@2LOBy"
"currency" : "RUB"
"count" : "100"
"credit_card" : "1234 5678 9012 3456"

Response: {
"RUB_balance": 43763.920000
}
.......................................................................
5 Просмотр актуальных курсов валют
GET: localhost:9966/api/v1/cryptoMarket/seeTheExchangeRate
HEADERS:
"secret_key" : "mH@2LOBy"
"currency" : "TON"

RESPONSE:
{
"BTS": 0.000109,
"TON": 1.000000,
"RUB": 190.540000
}
.........................................................................
6 Обмен валют по установленному курсу
POST: localhost:9966/api/v1/cryptoMarket/exchangeCurrency
HEADERS:
"secret_key" : "mH@2LOBy"
"currency_from" : "RUB"
"currency_to" : "TON"
"amount" : "188.54"

RESPONSE:
{
"amount_from": "42898.300000",
"currency_to": "TON",
"currency_from": "RUB",
"amount_to": "4.970000"
}
........................................................................
7 Изменить курс валют (только админ)
POST: localhost:9966/api/v1/cryptoMarket/changeExchangeRate
HEADERS:
"secret_key" : "n?pGMLsP"
"base_currency" : "TON"
"BTC" : "0.0001087"
"TON" : "1"
"RUB" : "190.54"

RESPONSE:
{
"BTS": 0.000109,
"TON": 1.000000,
"RUB": 190.540000
}
..........................................................................
8 Посмотреть общую сумму на всех пользовательских счетах для указанной валюты (только админ)
GET: localhost:9966/api/v1/cryptoMarket/seeAmountOfSpecificCurrency
HEADERS:
"secret_key" : "nGCYrUfz"
"currency" : "RUB"
RESPONSE:
{
"RUB": 67898.300000
}
..........................................................................
9 Посмотреть количество операций, которые были проведены за указанный период (только админ)
GET: localhost:9966/api/v1/cryptoMarket/seeAmountOfOperations
HEADERS:
"secret_key" : "n?pGMLsP"
"date_from" : "28.02.2023"
"date_to" : "02.03.2023"
!!!!!!!!!!!!!!!!Результат по операциям выводится не включая переданные даты, для просмотра данных с включёнными данными
за 02.03.2023 необходимо указать "date_to" : "03.03.2023"!!!!!!!!!!!!!!!!!!!
RESPONSE:
{
"transaction_count": 8
}