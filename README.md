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


## Swagger
The application provides Swagger UI that can be used to send requests to the application.
Swagger UI is available at the endpoint:
```http://localhost:8080/swagger-ui/index.html```

## Tests
The application provides unit tests for the service layer. The tests can be run using your IDE or by executing the command `mvn test` in the project directory.
There are over 20 unit tests in the project.

## Usage
The application provides REST API that can be used to fetch currency pairs from NBP API, convert currency pairs and save them to the database.
Here are some examples of how to use the API.


### • Fetching currency pairs from NBP API
To fetch currency pairs from NBP API send a GET request to the endpoint:
```http
GET /currency-exchange/api/fetch
```
The request will fetch all currency pairs from NBP API and save them to the database.



### • Getting all currency pairs from the database
To get all currency pairs from the database send a GET request to the endpoint:
```http
GET /currency-exchange/api/all
```


### • Getting currency pair by code
To get all currency pairs from the database by currency code send a GET request to the endpoint:
```http 
GET /currency-exchange/api/getRate
```
The request should contain the parameter:
- `code` - currency code

Example request:
```http
GET /currency-exchange/api/getRate?code=USD
```
The above request will return PLN/USD currency pair from the database.


### • Get 5 currency pairs with the highest PLN exchange rate

To get 5 currency pairs with the highest PLN exchange rate send a GET request to the endpoint:

```http
GET /currency-exchange/api/getTopFive
```

The request will return 5 currency pairs with the highest PLN exchange rate.

### • Get average exchange rate for a given currency code from last five days
```http
GET /currency-exchange/api/getAverageRateFiveDays
```
The request should contain the parameter:
- `code` - currency code

Example request:
```http
GET /currency-exchange/api/getAverageRateFiveDays?code=USD
```
The above request will return average exchange rate for USD from last five days.

## Technologies
- Java 17
- Spring Boot 3.1.5
- Hibernate 
- MySQL
- Maven
- JUnit 5 & Mockito
- Swagger (OpenAPI)

## API Reference
- [NBP API](http://api.nbp.pl/)