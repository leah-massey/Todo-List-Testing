package com.example

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class TodoListDomainTest { // check naming

    @Nested
    inner class TodoPathTest {
        @Test
        fun`returns 200-ok on the root URI on a valid request`() {
            val response = app(Request(GET, "/todos"))
            assertEquals(OK, response.status)
        }

        @Test
        fun`initially returns an empty list of todos`() {

            val TodoListRepoThatReturnsAFixedEmptyTodoList = object: TodoListRepoInterface {
                override fun getTodos(): MutableList<TodoItem> {
                    return mutableListOf()
                }
            }
            val domain = Domain(TodoListRepoThatReturnsAFixedEmptyTodoList)
            val expected = "[]"
            val actual = domain.getTodoList()
            assertEquals(expected, actual)
        }

        @Test
        fun `sends a confirmation message when todo item is added`() {
            val fixedTimeStamp = object : TimeStampInterface {
                override fun timeOfTodoCreation(): String {
                    return "2024.08.31.08.32.16"
                }
            }

            val fixedUUID = object : UUIDInterface {
                override fun todoUUID(): String {
                    return "1234556"
                }
            }

            val todoData = """
            {
                "name": "buy a kitten",
            }
            """.trimIndent()

            val response  = app(Request(POST, "/todo").body(todoData))
            val expected: String = "your todo has been added"
            val actual: String = response.bodyString()

            assertEquals(expected, actual)
        }


//
//
//        TodoItem(
//        id = "123456",
//        name=  "buy soy milk",
//        createdDate =  "2024-02-27T13:48:43.999Z+1", // ISO8601
//        modifiedDate =  "2024-02-27T13:50:43.999Z+1", // ISO8601
//        status =  "DONE"
//        )

//        @Test
//        fun `todoList updated when item added`() {
//            val todoData = """
//            {
//                "id": 3,
//                "title": "buy a kitten",
//                "body": "make sure it's friendly",
//                "status": false
//            }
//        """.trimIndent()
//
//            app(Request(POST, "/todo").body(todoData))
//            val response  = app(Request(GET, "/todo"))
//            val expected: String = "[{\"id\":3,\"title\":\"buy a kitten\",\"body\":\"make sure it's friendly\",\"status\":false}]"
//            val actual: String = response.bodyString()
//
//            assertEquals(expected, actual)
//        }
//
//        //        @Test
////        fun`returns the list of todos`() {
////            val response = app(Request(GET, "/todo"))
////            val expected: String = "[{\"id\":1,\"title\":\"clean flat\",\"body\":\"notes on how to clean my flat\",\"status\":false},{\"id\":2,\"title\":\"go swimming\",\"body\":\"don't forget goggles\",\"status\":false}]"
////            val actual = response.body
////            assertEquals(expected, actual)
////        }
    }

//    @Test
//    fun `Ping test`() {
//        assertEquals(Response(OK).body("pong"), app(Request(GET, "/ping")))
//    }
//
//
//    @Test
//    fun `Check Hamkrest matcher for http4k work as expected`() {
//        val request = Request(GET, "/testing/hamkrest?a=b").body("http4k is cool").header("my header", "a value")
//
//        val response = app(request)
//
//        // response assertions
//        assertThat(response, hasStatus(OK))
//        assertThat(response, hasBody("Echo 'http4k is cool'"))
//
//
//        // other assertions
//        // query
//        assertThat(request, hasQuery("a", "b"))
//
//        // header
//        assertThat(request, hasHeader("my header", "a value"))
//
//        // body
//        assertThat(request, hasBody("http4k is cool"))
//        assertThat(request, hasBody(Body.string(TEXT_HTML).toLens(), equalTo("http4k is cool")))
//
//        // composite
//        assertThat(request, hasBody("http4k is cool").and(hasQuery("a", "b")))
//    }

}
