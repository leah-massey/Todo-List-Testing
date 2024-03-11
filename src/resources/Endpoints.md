Goal: learn about event sourcing in a hands-on manner.

1. Build a simple app without event sourcing

### User Story 1: 

As a user, I want to get my list of todos

#### - API Docs:
GET /todos

Response:
{
id: "123456",
name: "climb a drainpipe",
createdDate: "2024-02-27T13:48:43.999Z+1" 
modifiedDate: "2024-03-17T15:48:43.9456+1"
status: "NOT_DONE"
},
{
id: "567891",
name: "jump a puddle",
createdDate: "2024-05-27T13:48:43.999Z+1"
modifiedDate: "2024-05-27T13:48:43.999Z+1"
status: "DONE"
}

--------------

### User Story 2:

As a user, I want to add an item to my todo list

#### - API Docs: 

POST /todos

{
name: "sit on a log"
}

Response:
"'sit on a log' has been added as a todo."

---------------

### User Story 3:

As a user, I want to view a specific todo with given ID

#### - API Docs:

GET /todos/123456

Response:
{
id: "123456",
name: "climb a drainpipe",
createdDate: "2024-02-27T13:48:43.999Z+1"
modifiedDate: "2024-03-17T15:48:43.9456+1"
status: "NOT_DONE"
}

---------------

### User Story 4:

As a user, I want to update a todo name in my todo list

#### - API Docs:

PUT /todos/123456

{
name: "climb a very long drainpipe",
}

Response:
"Your todo has been updated to 'climb a very long drainpipe'."

---------------

### User Story 5:
As a user, I want to update the status of a todo

#### - API Docs:

PUT /todos/123456

{
status: "DONE"
}

Response:
"The status of your todo 'swim in jelly' has been updated to 'DONE'."

---------------

### User Story 6:
As a user, I want to be able to delete a todo by ID

#### - API Docs:

DELETE /todos/123456

Response:
{
id: "123456",
name: "buy soy milk",
createdDate: "2024-02-27T13:48:43.999Z+1" // ISO8601
modifiedDate: "2024-02-27T13:50:43.999Z+1" // ISO8601
status: "DONE"
}



