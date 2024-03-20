package adapters

import domain.models.TodoCreatedEvent
import domain.models.TodoEvent
import domain.models.TodoNameUpdatedEvent
import org.http4k.format.Jackson.mapper
import ports.TodoListEventRepo
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class TodoListEventFileRepo(filePath: String): TodoListEventRepo {
    val eventFileRepo = File(filePath)
    override fun addEvent(event: TodoEvent) {
        val fileWriter = FileWriter(eventFileRepo, true) // FileWriter object created for eventFile. True param means that data will be appended to file if it already exists (false param would mean that the data in the file would be overwritten)
        val eventAsJsonString = mapper.writer().writeValueAsString(event)
        fileWriter.write(eventAsJsonString + "\n")
        fileWriter.close()
    }

//    override fun getEvents(): List<TodoEvent> {
//        val eventList = mutableListOf<TodoEvent>()
//        val fileReader = BufferedReader(FileReader(eventFileRepo))
//        var line: String?
//        while (fileReader.readLine().also { line = it } != null) {
//            val event: TodoEvent = mapper.readValue(line, TodoEvent::class.java) // second param is optional but specifies what I want to serialize my 'line' into
//            eventList.add(event)
//        }
//        fileReader.close()
//        return eventList
//    }

    override fun getEvents(): List<TodoEvent> {
        val eventList = mutableListOf<TodoEvent>()
        val fileReader = BufferedReader(FileReader(eventFileRepo))
        var line: String?

        while (fileReader.readLine().also { line = it } != null) {  // read each line, assigning each line to it for each loop. 'it' then represents an event
            val jsonNode = mapper.readTree(line) // parse the JSON string representation of the event into a JsonNode
            val eventType = jsonNode.get("eventType")?.asText()
            val event: TodoEvent? = when (eventType) {
                "TODO_CREATED" -> mapper.readValue(line, TodoCreatedEvent::class.java)
                "TODO_NAME_UPDATED" -> mapper.readValue(line, TodoNameUpdatedEvent::class.java)
                // Add more cases
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


}