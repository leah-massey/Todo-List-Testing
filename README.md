# TodoList

In this application a user can view, add and update the name or status of their todos. 

Each update made by the user is recorded using Event Sourcing.


### To note: 
This is a learning project, with some comments left in for personal reference / feedback

The focus of the project is to add comprehensive testing to a previously built Todo project. The original project was built without the use of TDD, for reasons of speed, and no testing had been done.  

For similar reasons, there are no architecture diagrams for this project, although [Endpoints.md](src/resources/Endpoints.md) can be viewed to see expected outcomes.


### Getting started  
 To run this project and run from your IDE:

```git clone https://github.com/leah-massey/Todo-List-Event-Sourcing-Kotlin.git```


To start the server, run the main function located here: 
```src/main/kotlin/Main.kt```

When the server is running, the endpoints can be called from [Postman](https://www.postman.com/) (or the command line tools of your choice). 

See all possible endpoints, with examples [here](src/resources/Endpoints.md).

## Package
```
./gradlew distZip
```


