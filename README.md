<div align="center">
<h1>LibraryOnline</h1>

|       ENDPOINT        | METHOD  |         REQUEST          |       RESPONSE       |                    FUNCTION                     |
|:---------------------:|:-------:|:------------------------:|:--------------------:|:-----------------------------------------------:|
|        /books         |  GET    |            -             |      JSON (books)    |                returns all books                |
|     /books/{uuid}     |  GET    |   PATH VARIABLE (uuid)   |      JSON (book)     |            returns book with given uuid         |
|        /genres        |  GET    |            -             |      JSON (genres)   |                returns all genres               |
|     /genres{uuid}     |  GET    |   PATH VARIABLE (uuid)   |      JSON (genre)    |            returns genre with given uuid        |


  <h4>Test accounts</h4>


  |ROLE     | EMAIL             | PASSWORD                 |
  |:-------:|:-----------------:|:------------------------:|
  |ADMIN    |admin@library.com  |       adminpass          |
  |EDITOR   | editor@library.com|       editorpass         |
  |USER     | user@library.com  |       userpass           |

</div>
