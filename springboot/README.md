# Esercizio Fabrick
Application that allows you to manage the following operations on the account:
1. Balance reading;
2. List of transactions;
3. Money transfer;
4. Account info; 

## Table of Contents
1. [General Info](#general-info)
2. [Requirements](#requirements)
3. [Technologies](#technologies)
4. [Running the application locally](#running-the-application-locally)
5. [Collaboration](#collaboration)
6. [FAQs](#faqs)

### General Info

Credentials and inputs

The exercise must be developed using the Sandbox environment with the following credentials:

BaseUrl: 		https://sandbox.platfr.io
Auth-Schema: 	S2S
Api-Key: 		*****************************
Id chiave: 		3202
accountId: 		14537780

Properties / Application constants
* {accountId}: Long, is the reference account number, in the API it is always indicated as {accountId}, use value 14537780

Operations: 
* Operation: API balance reading: 

	https://docs.fabrick.com/platform/apis/gbs-banking-account-cash-v4.0 
	Output: View the balance

* Operation: Transfer API:

	API: https://docs.fabrick.com/platform/apis/gbs-banking-payments-moneyTransfers-v4.0
	Output:Status of the operation, the transfer will result in a KO due to a limitation of the test account. The expected output must be: "code": "API000", "description": "Technical error The BP049 condition is not provided for the account id 14537780"

* Operation: Read API Transactions:

	 https://docs.fabrick.com/platform/apis/gbs-banking-account-cash-v4.0
	 Output: List of transactions, transactions are present in the suggested dates on the example.

### Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/it/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3.6.1](https://maven.apache.org)

## Technologies

A list of technologies used within the project:
* [Spring Boot](https://spring.io/projects/spring-boot): Version 2.5.6
* [Maven](https://maven.apache.org/): Version 3.6.1
* [Model mapper](http://modelmapper.org/user-manual/spring-integration/): Version 2.4.4
* [Jackson Annotation](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations) Version 2.12


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Collaboration

...

## FAQs

1. **Question**

	Answer of the first question with _italic words_. 