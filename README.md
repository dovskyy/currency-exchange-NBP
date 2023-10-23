# Currency Exchange NBP API

## Description
Currency exchange rate converter based on reference rates published by the National Bank of Poland (NBP).
The currency pairs are converted using the exchange rate provided by API from NBP. All currencies are paired with PLN.

## Installation
1. Clone the repository
2. Install dependencies from pom.xml
3. Create a database in MySQL, set the database name, username and password in `application.properties` file. 
4. Run the application using your IDE or by executing the command `mvn spring-boot:run` in the project directory
5. Use Postman or other tool to send requests to the application

Example request:
```
GET localhost:8080/currency-exchange/api/fetch
```
The above request will fetch all currency pairs from NBP API and save them to the database.

## Usage
The application provides REST API that can be used to fetch currency pairs from NBP API, convert currency pairs and save them to the database.

### Fetching currency pairs from NBP API
To fetch currency pairs from NBP API send a GET request to the endpoint:
```
GET localhost:8080/currency-exchange/api/fetch
```
The request will fetch all currency pairs from NBP API and save them to the database.

[//]: # (### Converting currency pairs)

[//]: # (To convert currency pairs send a GET request to the endpoint:)

[//]: # (```)

[//]: # (GET localhost:8080/currency-exchange/api/convert)

[//]: # (```)

[//]: # (The request should contain the parameters:)

[//]: # (- `from` - currency code of the currency to be converted)

[//]: # (- `to` - currency code of the currency to be converted to)

[//]: # (- `amount` - amount of money to be converted)

[//]: # ()
[//]: # (Example request:)

[//]: # (```)

[//]: # (GET localhost:8080/currency-exchange/api/convert?from=EUR&to=PLN&amount=100)

[//]: # (```)

[//]: # (The above request will convert 100 EUR to PLN using the exchange rate from NBP API.)

[//]: # (### Saving currency pairs to the database)

[//]: # (To save currency pairs to the database send a GET request to the endpoint:)

[//]: # (```)

[//]: # (GET localhost:8080/currency-exchange/api/save)

[//]: # (```)

[//]: # (The request will save all currency pairs from NBP API to the database.)

### Getting all currency pairs from the database
To get all currency pairs from the database send a GET request to the endpoint:
```
GET localhost:8080/currency-exchange/api/get-all
```

### Getting all currency pairs from the database by currency code
To get all currency pairs from the database by currency code send a GET request to the endpoint:
```
GET localhost:8080/currency-exchange/api/get-by-code/{code}
```
The request should contain the parameter:
- `code` - currency code

Example request:
```
GET localhost:8080/currency-exchange/api/get-by-code/USD
```
The above request will return all currency pairs from the database with USD currency code.


## Technologies
- Java 17
- Spring Boot 3.1.5
- Hibernate 
- MySQL
- Maven
- Lombok
- JUnit 5
- Mockito

## API Reference
- [NBP API](http://api.nbp.pl/)

