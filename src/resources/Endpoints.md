Goal: learn about event sourcing in a hands-on manner.

1. Build a simple app without event sourcing

### User Story 1: 
As a user, I want to add an item to my todo list

#### - API Docs: 

POST /todos

{
name: "buy milk"
}

Response:
{
id: "123456",
name: "buy milk",
createdDate: "2024-02-27T13:45:43.999Z+1" // ISO8601
status: "NOT_DONE"
}

### User Story 2:
As a user, I want to be able to update a task description in my todo list

#### - API Docs:

PUT /todos/123456

{
name: "buy SOY milk",
status: "NOT_DONE"
}

Response:
{
id: "123456",
name: "buy SOY milk",
createdDate: "2024-02-27T13:48:43.999Z+1" // ISO8601
status: "NOT_DONE"
}

### User Story 2:
As a user, I want to be able to mark a task in my todo list as completed

#### - API Docs:

PUT /todos/123456

{
name: "buy soy milk",
status: "DONE"
}

Response:
{
id: "123456",
name: "buy soy milk",
createdDate: "2024-02-27T13:48:43.999Z+1", // ISO8601
modifiedDate: "2024-02-27T13:50:43.999Z+1", // ISO8601
status: "DONE"
}






### User Story 2:
As a user, I want to be able to view all my todos, most recently added first

#### - API Docs:

GET /todos

{
status: "NOT_DONE"
}

Response:
{
id: "123456",
name: "buy soy milk",
createdDate: "2024-02-27T13:48:43.999Z+1" // ISO8601
modifiedDate: "2024-02-27T13:50:43.999Z+1" // ISO8601
status: "DONE"
}

### User Story 2:
As a user, I want to be able to view all a specific todo

GET /todos/123456

Response: 


