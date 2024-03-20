package adapters

import com.fasterxml.jackson.module.kotlin.readValue
import domain.models.TodoEvent
import org.http4k.format.Jackson.mapper
import ports.TodoListEventRepo
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class TodoListEventFileRepo(filePath: String): TodoListEventRepo {
    val eventFile = File(filePath)
    override fun addEvent(event: TodoEvent) {
        val fileWriter = FileWriter(eventFile, true) // FileWriter object created for eventFile. True param means that data will be appended to file if it already exists (false param would mean that the data in the file would be overwritten)
        val eventAsJsonString = mapper.writer().writeValueAsString(event)
        fileWriter.write(eventAsJsonString + "\n")
        fileWriter.close()
    }

//    override fun getEvents(): List<TodoEvent> {
//        val fileReader = FileReader(eventFile)
//        val eventList: List<TodoEvent> = mapper.readValue(eventFile)
//        val objectReader = mapper.readValue(fileReader)
//        return eventList
//    }
    override fun getEvents(): List<TodoEvent> {
        val fileReader = FileReader(eventFile)
        val eventList: List<TodoEvent> = mapper.readValue(fileReader) // Corrected to use FileReader
        fileReader.close() // Don't forget to close the reader after use
        return eventList
    }

}