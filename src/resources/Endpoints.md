## - API Docs:


### Get all todos: 

GET /todos

Response:

```
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
```

--------------

### Get todos by status:

GET /todos?status=DONE

Response:

```
{
    id: "567891",
    name: "jump a puddle",
    createdDate: "2024-05-27T13:48:43.999Z+1"
    modifiedDate: "2024-05-27T13:48:43.999Z+1"
    status: "DONE"
}
```

---------------

### Get todo by ID

GET /todos/123456

Response:

```
{
    id: "123456",
    name: "climb a drainpipe",
    createdDate: "2024-02-27T13:48:43.999Z+1"
    modifiedDate: "2024-03-17T15:48:43.9456+1"
    status: "NOT_DONE"
}
```

---------------

### Add a todo

POST /todos

```
{
    name: "sit on a log"
}
```

Response:

"'sit on a log' has been added as a todo."

---------------

### Update the name of a todo

PUT /todos/123456

```
{
    name: "climb a very long drainpipe",
}
```

Response:

"Your todo has been updated to 'climb a very long drainpipe'."

---------------

### Update the status of a todo

PUT /todos/123456

```
{
    status: "DONE"
}
```

Response:

"The status of your todo 'swim in jelly' has been updated to 'DONE'."

---------------

### Delete a todo 

DELETE /todos/123456

Response: 

"Your todo 'dance on marbles' has been deleted."





