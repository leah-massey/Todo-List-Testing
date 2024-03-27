## - API Docs:
Below are example request/response scenarios for all endpoints created for this project.

### Get all todos: 

```GET /todos```

Response:

```
 {
    id: "123456",
    name: "climb a drainpipe",
    status: "NOT_DONE"
},
{
    id: "567891",
    name: "jump a puddle",
    status: "DONE"
} 
```

--------------

### Get todos by status:

```GET /todos?status=DONE```

Response:

```
{
    id: "567891",
    name: "jump a puddle",
}
```

---------------

### Get todo by ID

```GET /todos/123456```

Response:

```
{
    id: "123456",
    name: "climb a drainpipe",
    status: "NOT_DONE"
}
```

---------------

### Add a todo

```POST /todos```

Request:

```
{
    name: "sit on a log"
}
```

Response:

```
{
    id: "456789",
    name: "sit on a log",
    status: "NOT_DONE"
}
```

---------------

### Update the name of a todo

```PATCH /todos/123456```

Request:

```
{
    name: "climb a very long drainpipe",
}
```

Response:

```
{
id: "123456",
name: "climb a very long drainpipe",
status: "NOT_DONE"
}
```

---------------

### Update the status of a todo

```PATCH /todos/123456```

Request: 

```
{
    status: "DONE"
}
```

Response:

```
{
id: "123456",
name: "climb a very long drainpipe",
status: "DONE"
}
```

---------------





