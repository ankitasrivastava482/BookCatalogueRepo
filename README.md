# BookCatalogue
## Requirements

For building and running the application you need:

1- JDK 1.8 and above

2- Maven 3.8.1 

3- ActiveMQ

4- Spring boot framework 2.4.5

5- Postman to run the endpoints

## Running the application locally

Execute the main method in the BookCatalogApplication.Java class from your ID

or by using mvn spring-boot:run on command prompt


## API Endpoints

**To get all the books**

http://localhost:8080/books

**To search book by title and author**

http://localhost:8080/SearchByTitleAuthor/{title}/{author}

**To search book by ISBN**

http://localhost:8080/SearchByTitleAuthor/{isbn}

**To add a book**

http://localhost:8080/addbook

sample json structure

{"isbn": "9780596009205","title": "Head First Java","author": ["Kathy Sierra","Bert Bates"],"publicationDate": "2017-12-27"}

**To delete a book**

http://localhost:8080/deletebook/{isbn}

**To update a book**

http://localhost:8080/updatebook

sample json sturcture

{"isbn": "9780596009205","title": "Head First Java1","author": ["Kathy Sierra","Bert Bates"],"publicationDate": "2017-12-27"}



## Message Queue
