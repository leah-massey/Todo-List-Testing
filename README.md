# TodoList

In this application a user can view, add, update and delete their todos. 

Each update is recorded using Event Sourcing.

Initially, the todo list is emt

### To note: 
This is a learning project, with some comments left in for personal reference / feedback

This project was built without the use of TDD, for reasons of speed, and no testing has been done. This is not best practise but for the focus of this exercise is deemed acceptable. 

For similar reasons, there are no architecture diagrams for this project, although Endpoints.md will demonstrate expected outcomes.


### Getting started  
 To run this project and run from your IDE:

```git clone https://github.com/leah-massey/Kotlin-Todo-List.git```


To start the server, run the main function located here: 
```src/main/kotlin/Main.kt```

When the server is running, the endpoints can be called from [Postman](https://www.postman.com/) (or the command line tools of your choice). 
For example, to get all todos, run a GET request of the following:
```http://localhost:3000/todos```

## Package
```
./gradlew distZip
```


