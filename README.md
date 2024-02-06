<div align="center">



<h1>LibraryOnline</h1>

![Logo](web-service/src/main/resources/static/img/RateMyReadLogo.png)

|       ENDPOINT        | METHOD  |         REQUEST          |       RESPONSE       |                    FUNCTION                     |
|:---------------------:|:-------:|:------------------------:|:--------------------:|:-----------------------------------------------:|
|       api/auth        |  POST   |  JSON BODY (credentials) |    JSON (JWT token)  | returns token after successfully authorization  |
|       api/books       |  GET    |            -             |      JSON (books)    |                returns all books                |
|       api/books       |  POST   |    JSON BODY (book)      |      JSON (uuid)     |                creates new book                 |
|   api/books/{uuid}    |  GET    |   PATH VARIABLE (uuid)   |      JSON (book)     |            returns book with given uuid         |
|   api/recommended     |  GET    |            -             |      JSON (books)    |            returns all recommended books        |
|       api/genres      |  GET    |            -             |      JSON (genres)   |                returns all genres               |
|       api/genres      |  POST   |    JSON BODY (genre)     |      JSON (uuid)     |                returns new genres               |
|   api/genres{name}    |  GET    |   PATH VARIABLE (name)   |      JSON (genre)    |            returns genre with given name        |


  <h4>Test accounts</h4>


  |ROLE     | EMAIL             | PASSWORD                 |
  |:-------:|:-----------------:|:------------------------:|
  |ADMIN    |admin@library.com  |       adminpass          |
  |EDITOR   | editor@library.com|       editorpass         |
  |USER     | user@library.com  |       userpass           |

</div>
