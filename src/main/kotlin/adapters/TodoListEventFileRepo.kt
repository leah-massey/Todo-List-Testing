package adapters

import domain.models.TodoEvent
import org.http4k.format.Jackson.mapper
import ports.TodoListEventRepo
import java.io.File
import java.io.FileWriter

class TodoListEventFileRepo(filePath: String): TodoListEventRepo {
    val eventFile = File(filePath)
    override fun addEvent(event: TodoEvent) {
        val fileWriter = FileWriter(eventFile, true) // FileWriter object created for eventFile. True param means that data will be appended to file if it already exists
        val sequenceWriter = mapper.writerWithDefaultPrettyPrinter().writeValues(fileWriter)
        sequenceWriter.write(event)
//        sequenceWriter.close()
    }

//    override fun getEvents(): List<TodoEvent> {
//        TODO("Not yet implemented")
//    }
}