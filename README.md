# Bookcomet REST API Project

To use the developed API, it's necessary to download the code, import it in Eclipse, NetBeans or a similar tool and run a Java Maven Web Application Project. Running the main class will start the server containing the API resources.

The API was developed using Java 8 version.

Grizzly was used to create the Http Server and Jakarta for the Web Services.

# Implemented Routes

@GET /books: lists all books
	-@QueryParam("authors"): filter books thas have the same author as the text specified here
 	-@QueryParam("publisher") filter books thas have the same publisher as the text specified here

@GET /books/{id}: returns specific book

@POST /books: creates a new book
	-@QueryParam("format"): parameter used to specify a format if the book is an ebook

@PUT /books: update a book

@DELETE /books: deletes a book

@GET /inventory: list all inventory items

@POST /inventory/{id}: creates a book inventory if it doesn't exist or increment its quantity if it does

@DELETE /inventory/{id}: decrements a book inventory quantity

# API Requests

Bookcomet.postman_collection.json file can be imported in Postman application to test the api. It will import a collection with all necessary requests. 


