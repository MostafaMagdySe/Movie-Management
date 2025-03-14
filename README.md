# Movie-Management
This Project is an online Movies Management website. it's a place where you can watch Movies online. the only thing it's missing is its content (the movies itself). However, they can be added Later by linking the Movies' media to hosted 3rd party services. (This is Backend only.. i've also implemented the front end at this repo: [Front End Repo](https://github.com/MostafaMagdySe/Movie-Management-Front-End/)

## Features of this Project

1-This Project basicly implements OmdbApi, which fetches Movies' info from Imdb's Website.. then uses that details in this project where the Admins of the website can add movies to let users watch them. and instead of manually mapping the Movie's Details.. it's being done Automatically!

2- Also, Improved how OmdbApi Works.. for example if you searched for "harry potter".. you didn't specifiy which "harry potter" movie you are looking for.. there are 8 movies in total.. in OmdbApi offical Website, if you searched for "Harry Potter".. you will get a response containing details for "Harry Potter and the Deathly Hallows: Part 2" , so, in this scenario .. you are getting wrong movie details which is considered bad data to data Base.. so, that was stopped in this project, if you tried to add a movie called "harry potter" your request will be rejected and you will get a suggestion of the title for the movie that represents the Title you entered.

This Pic Makes it better for understanding: [Movie_Mismatch.png](https://www.mediafire.com/view/dtu8cwuftllzwgj)

As you see, you got a mismatch response and it suggests to add the movie that corresponds to the name which OmdbApi will give you, but if you tried to add "Harry Potter and the Deathly Hallows: Part 2" it will work normally.

3-Made the website more user-friendly.. for a user who Heard about a movie and wants to watch it in the website, instead of Having to open the website and search for that movie, the user can just copy the movie's full title from imdb or any other place.. and just paste it in the Website's Url. for example, a user wanted to watch a movie called "Spider-Man: Across the Spider-Verse" .. all the user have to do is hitting the website's url and paste the movie name.. the movie name will be converted making a valid url.

for example: hitting a url like that "http://localhost:8080/Movies/Spider-Man: Across the Spider-Verse" is possible! no need to care about spaces or any other special characters, it will get converted automatically to a valid url.. if the website contains that movie.. the user will see that movie's details.. if not, the user will see a message says that this movie isn't available at the website yet!

4-Also, the project Have Role-based access control. which means there are two types of users, normal users and admin users. every new registered user will automatically get a role of "User" , the only way to have admin user, is by changing the role of a particual user from the database and to change the password for that user because the password which is stored in the databse is encoded, and it has information about the role of that user.

this way is the best way to have admin user, the admin is a person who has access to modifiy the databse.. also, it's possible to implement a method to make a Super admin who can create other admins.. but it depends on the business logic.. to keep it simple for now, to get admin user, you have to do that manually!

5- Implemented Pagination to make it more user friendly. and also not Hammering on the servers by sending a lot of responses the user might not be needed.

6- Also, Added Rating system.. now for each movie.. the user can rate the movie from 1-10 and give a review of that movie.. it was decided that the best prctise is that the user cannot modifiy his rating.. but he still can delete his rating and add a new rating. and of course the user can rate the single movie one time only not to Spoil the overall Rating.

7- To Make it easier for Admins, Batch Processing is added, which means Admins can add/remove several movies at once by providing their titles as a list. Also, not to Hammer on the Server, it's only Possible to Add up to 10 Movies at once, if an Admin tried to add more then that in one request, it will get rejected.

8- Also, search is implemented making it possible to search for any movie by title, and return a list of all movies having the same keyword you have searched for.

9-Added Swagger UI to Make it Easier to Visualize and test the Endpoints.

10- Implemented Global Exception Handler using @ControllerAdvice. so that, the website never get crashed, it will keep working and if something unexpected happened it will shows the erorr message making it easier to know what causes the issue.


## Prerequisites
**Java:** The project is built using Java 17. Make sure you have Java version 17 or later installed.

**DataBase:** if you are not sure about it, just install and run PostgreSQL not to have to edit the Sql Scripts.

**Maven:** install latest version of maven dependency management

**IDE (Optional)**: While the project was developed using IntelliJ IDEA, you can use any IDE that supports Java and Maven, or run the project directly from the command line.

## Setup Instructions
clone the project to your local repository or just download the project to your pc and follow the next steps:
### Preparing Database
1- create a new database in PostgreSQL or any database management.

2- under Project/resources , you will find Sql Scripts for the project called "movie Sql Schema.sql" just copy its content and run them inside you Postgresql

3- open application.properties in project files and edit the url to match the database name (i.e spring.datasource.url=jdbc:postgresql://localhost:5432/your Database Name , and also change the username and password to match your credentials. i.e(  spring.datasource.username=Your User name and spring.datasource.password=your Password

### preparing the project
1- Open pom.xml file in your ide and let maven resolve and download all dependencies if needed.

2- open application.properties and under spring.mail.. put your gmail, and for password, you need to search how to get app password for your gmail and put it there. to make it easier, sample email and app password were provided 

3- also, you have to get api key to use th OmdbApi .. after that, in application.properties.. put your key under omdb.key="your Api key" .. and to make it easier.. a ke was provided so you can get started quickly!

3- Now you are ready to run the project and also make sure that Postgres is running.

## Logging In
1-As the project uses JWt, you need to get a token to start.. this is only possible by logging in.. so, to get started you need to create an account.. to hit the register endpoint go to register endpoint "/register" (eg: http://localhost:8080/register)

2- You can register with username and password only. but, if you forgot the password, there is no way to recover it.. so, it's better to add your email so that you can always recover your account back.. you can add phone as well or you can add phone and email later. it's totally up to you.

3- After registering, hit the login api (eg: http://localhost:8080/login) after providing valid credentials you will get a response containing a token, you can use this token to access the other endpoints.


## Adding Movies to the database
1- Make sure you are logged in as Admin User, and Using Post Request, send a list of titles to ("/movieInsertion") endpoint, i.e(localhost:8080/movieInsertion) 
2- The Movies will be processed one by one, if a Movie exist or its name is wrong it will get rejected and not stored in databse, if the title makes OmdbApi sends a request containing that movie's details, if the name on OmdbApi don't match the provided title, it will get rejected and suggest ou to use the name Omdb Returned in the response.. and of course if a movie's title matches the title sent in the response from OmdbApi, the movie's info will get stored in Databse Normally.

 ## Removing Movies to the database
1- Make sure you are logged in as Admin User, and Using Delete Request, send a list of titles to ("/movieDeletion") endpoint, i.e(localhost:8080/movieDeletion) 
2- The Movies will be processed one by one, if a Movie exist, it will be deleted from the Data Base, if not.. it it will return: Failed to delete Movie: "Movie's Title"
## Dashboard 
1- Both Admin users and Normal Users have the Access to see the Movies from the Database, the homepage that have the list of all movies in the website, only shows title and poster of each movie.. and after clicking on the movie, in front end it it will redirect you to the movie's details. to see all movies in the website just hit (localhost:8080/)

as pagination is implemented you will see 5 movies only at once.. to see the following movies, ou have to proceed to the next page

2-To see details of a specific movie, you should hit endpoint: "/Movies/{name}" i.e (http://localhost:8080/Movies/{Movie'sTitle})

3- to search for a movie, send a keyword to ("/search") endpoint, i.e: (http://localhost:8080/Movies/search)

4- it's also possible to edit your profile's details by hitting ("/updateProfile") , i.e. (http://localhost:8080/updateProfile)

## Rating Movies

1- Ever User Has the Ability to rate each movie once per movie, you aren't allowed to rate more than one time. to maintain consistency, users cannot edit their reviews, however, it's possible to delete your rating and rate the movie again!

2- this is possible by hitting endpoint ("/rateMovie") i.e (http://localhost:8080/rateMovie) , where you should provide your rating 1-10 and the review and provide movie id, the front end will handle that movie id part don't worry!

3- Also, to delete our rating, you should visit ("/deleteMovieRating") , i.e. (http://localhost:8080/deleteMovieRating) and make sure than the request is of tpe delete and provide the movie id.

## Password Reseting
1- if you had forgotten your password, don't be worried.. it's possible to recover your account back! to do that, you should first vist
this endpoint: ("/ResetPassword") i.e (http://localhost:8080/ResetPassword) , after that you have to provide your email, and if it's stored inside the databse, the email service will send you a randomly generated verfication code. you to correctl pass it to the next endpoint or you won't be able to retrieve our account back!

2- After Getting the verfication Code, you should visit ("/verifyCode") , i.e" (http://localhost:8080/verifyCode) , where ou will be asked to provide our verification code, if it is correct, you should be proceed to change your password

3-to change your password you should hit ("/UpdatePassword") i.e. (http://localhost:8080/UpdatePassword), where you will provide your new password!
## Swagger UI
if you are familiar with Testing your project and add documentation for the project.. swagger is embedded to the project.











