# <div style="text-align: center;">REST Repository Fetcher [![Build Status](https://travis-ci.com/tboba/rest-repository-fetcher.svg?branch=master)](https://travis-ci.com/tboba/rest-repository-fetcher) </div> 
  <div style="text-align: center;">Simple web application, which allows to fetch the data about the user's repositories from the GitHub service.</div> <br>
<div style="text-align: center;">Written in Java with Spring Framework.</div>
<hr>

## Endpoints

| Method                                             | Return type                                       | Success status codes   | Error status codes                         |
| -------------------------------------------------- | ------------------------------------------------- | ---------------------  | ------------------------------------------ |                   
| **GET  /repos/{user}**                             | A full list of provided user's repositories.      | 200                    | 404 (Thrown when user cannot be found)     |
| **GET  /stargazers/{user}**                        | A JSON response with provided user's data.        | 200                    | 404 (Thrown when user cannot be found)     |

## Installation
To build the application, simply follow these commands:
```
git clone https://github.com/tboba/rest-repository-fetcher
cd rest-repository-fetcher

./gradlew build (or gradlew.bat build, while using Windows.)
./java -jar repository-fetcher-1.0.0.jar
```
After executing those operations, an application should start shortly.

## Testing
To test the application, simply execute `./gradlew test` command (or `gradlew.bat test`, while using Windows) in the application directory.
The tests should run automatically.

## Plans to improve
In the future, it is possible to implement the ability to check user data with the use of
more vendors of the Version Control System (e.g. GitLab, BitBucket). 
In addition, the repository can also be improved by implementing the listing of the users, who starred shown repository and
extend the list of the endpoints with new replies in the future.

<hr>
<div style="text-align: right">Tymoteusz Boba | 2021</div>
