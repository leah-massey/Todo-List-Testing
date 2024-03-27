package adapters

import domain.models.*
import org.http4k.format.Jackson.mapper
import ports.TodoListEventRepo
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class TodoListEventFileRepo(filePath: String) : TodoListEventRepo {
    val eventFileRepo = File(filePath)
    override fun addEvent(event: TodoEvent) {
        val fileWriter = FileWriter( // FileWriter object created for eventFile. True param means that data will be appended to file if it already exists (false param would mean that the data in the file would be overwritten)
            eventFileRepo,
            true
        )
        val eventAsJsonString = mapper.writer().writeValueAsString(event)
        fileWriter.write(eventAsJsonString + "\n")
        fileWriter.close()
    }

    override fun getEvents(): List<TodoEvent> {
        val eventList = mutableListOf<TodoEvent>()
        val fileReader = BufferedReader(FileReader(eventFileRepo))
        var line: String?

        while (fileReader.readLine()
                .also { line = it } != null // read each line, assigning each line to it for each loop. 'it' then represents an event
        ) {
            val jsonNode = mapper.readTree(line) // parse the JSON string representation of the event into a JsonNode
            val eventType = jsonNode.get("eventType")?.asText()
            val event: TodoEvent? = when (eventType) {
                "TODO_CREATED" -> mapper.readValue(line, TodoCreatedEvent::class.java)
                "TODO_NAME_UPDATED" -> mapper.readValue(line, TodoNameUpdatedEvent::class.java)
                "TODO_STATUS_UPDATED" -> mapper.readValue(line, TodoStatusUpdatedEvent::class.java)
                else -> null
            }
            if (event != null) {
                eventList.add(event)
            } else {
                println("Error: handle")
            }
        }
        fileReader.close()

        return eventList
    }

    override fun getTodoList(): List<Todo> {
        val todoListEvents: List<TodoEvent> = getEvents()
        val todoList: MutableList<Todo> = mutableListOf()
        var newTodo: Todo? = null

        todoListEvents.forEach { event ->
            val idToCheck: String = event.entityId
            val todoExistsAlready: Boolean = todoList.any { it.id == idToCheck }

            if (todoExistsAlready) {
                todoList.forEach { todo ->
                    if (todo.id == idToCheck) { // Find matching todo and update accordingly
                        if (event is TodoNameUpdatedEvent) { // cast type to TodoNameUpdatedEvent so that I can call .eventDetails
                            todo.name = event.eventDetails.name
                            todo.lastModifiedTimestamp = event.eventCreatedDate
                        } else if (event is TodoStatusUpdatedEvent) {
                            todo.status = event.eventDetails.status
                            todo.lastModifiedTimestamp = event.eventCreatedDate
                        }
                    }
                }
            } else {
                if (event is TodoCreatedEvent) { // cast type to TodoNameUpdatedEvent so that I can call .eventDetails
                    todoList.add(
                        Todo(
                            id = event.entityId,
                            createdTimestamp = event.eventCreatedDate,
                            lastModifiedTimestamp = event.eventCreatedDate,
                            name = event.eventDetails.name
                        )
                    )
                }
            }
        }
        return todoList
    }
}



