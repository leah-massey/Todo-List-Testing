package com.example

import com.example.formats.JacksonMessage
import com.example.formats.jacksonMessageLens
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST

import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.util.Objects
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.core.*
import org.http4k.format.Json
import org.http4k.format.write
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class TodoItem(
    val id: Int,
    var title: String,
    var body: String,
    var status: Boolean
)

val mapper = ObjectMapper().registerModule(KotlinModule())

// list starts empty
val toDoList: MutableList<TodoItem> = mutableListOf()
//Draft list : TodoItem(1, "clean flat", "notes on how to clean my flat", false), TodoItem(2, "go swimming", "don't forget goggles", status = false)

val app: HttpHandler = routes(
    // view task list as a json string
    "/todo" bind GET to {request: Request ->
        val toDoListAsJson: String = mapper.writeValueAsString(toDoList) // returns object as string

        val taskId: Int? = request.query("id")?.toInt()

        // if no query parameter, return entire list
        if (taskId == null) {
            Response(OK).body(toDoListAsJson)
        // if taskId declared, return task matching the id
        } else {
            val singleTask: List<TodoItem> = toDoList.filter {
                it.id == taskId }
                    Response(OK).body(mapper.writeValueAsString(singleTask))
                }
    },

    // add an item to task list
    "/todo" bind POST to {request: Request ->
        val jsonString = request.bodyString()
        val newTodo = mapper.readValue(jsonString, TodoItem::class.java) // telling the object mapper to instantiate the ToDoItem class when it parses the jsonString
        toDoList.add(newTodo)
        val toDoAsJson = mapper.writeValueAsString(toDoList)
        Response(OK).body("your todo has been added" )
    }



//    "/formats/json/jackson" bind GET to {
//        Response(OK).with(jacksonMessageLens of JacksonMessage("Barry", "Hello there!"))
//    },
//
//    "/testing/hamkrest" bind GET to {request ->
//        Response(OK).body("Echo '${request.bodyString()}'")
//    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}
