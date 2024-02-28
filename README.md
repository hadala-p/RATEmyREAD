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




## Tech Stack
Code: <br>
![Static Badge](https://img.shields.io/badge/java_21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Static Badge](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=MySQL&logoColor=white)
<br>
FrontEnd: <br>
![HTML5](https://img.shields.io/badge/HTML5-E34F26.svg?style=for-the-badge&logo=HTML5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6.svg?style=for-the-badge&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=for-the-badge&logo=JavaScript&logoColor=black)
<br>
Other: <br>
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717.svg?style=for-the-badge&logo=GitHub&logoColor=white)


  ## How to build the project on your own
#### To build the project:
<ol>
<li>Clone the repository:</li>

```
git clone https://github.com/hadala-p/RATEmyREAD.git
```
<li>Go to the folder with cloned repository</li> 
<li>Run the command on all services:</li>

```
mvn package 
```
<li>In folder target you should find a file named: application-{version}-SNAPSHOT.jar</li>
</ol>

#### To build the docker image with Docker Compose:
<ol>
<li>Clone the repository:</li>

```
git clone https://github.com/hadala-p/RATEmyREAD.git
```
<li>Go to the folder with cloned repository</li> 
<li>Run the command:</li>

```
docker-compose build
```
<li>By using:

```
docker images
```
</li>
</ol>

  <h4>Test accounts</h4>


  |ROLE     | EMAIL             | PASSWORD                 |
  |:-------:|:-----------------:|:------------------------:|
  |ADMIN    |admin@library.com  |       adminpass          |
  |EDITOR   | editor@library.com|       editorpass         |
  |USER     | user@library.com  |       userpass           |


# Frontend
## Logo
![Logo](readme_images/header.png)
## Recommended
![Logo](readme_images/recomended.png)
## Top 10
![Logo](readme_images/top10.png)
## Book Details
![Logo](readme_images/bookDetails.png)
## Rating System
![Logo](readme_images/rating.png)
## Genres
![Logo](readme_images/genres.png)
## Admin Panel
![Logo](readme_images/adminPanel.png)
## Book Adding Form 
![Logo](readme_images/addBookForm.png)
## Genre Adding Form 
![Logo](readme_images/addGenreForm.png)
## Login Form
![Logo](readme_images/login.png)
## Error 404
![Logo](readme_images/error_404.png)
</div>
