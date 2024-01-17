package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import netscape.javascript.JSObject
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasQuery
import org.http4k.hamkrest.hasStatus
import org.http4k.lens.string
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TodoListTest {

    @Nested
    inner class TodoPathTest {
        @Test
        fun`returns 200-ok on the root URI on a valid request`() {
            val response = app(Request(GET, "/todo"))
            assertEquals(OK, response.status)
        }

        @Test
        fun`initially returns an empty list of todos`() {
            val response = app(Request(GET, "/todo"))
            val expected: String = "[]"
            val actual: String = response.body.stream.reader().readText()
            assertEquals(expected, actual)
        }

        @Test
        fun `sends a confirmation message when todo item is added`() {
            val todoData = """
            {
                "id": 3,
                "title": "buy a kitten",
                "body": "make sure it's friendly",
                "status": false
            }
        """.trimIndent()

            val response  = app(Request(POST, "/todo").body(todoData))
            val expected: String = "your todo has been added"
            val actual: String = response.bodyString()

            assertEquals(expected, actual)
        }

        //        @Test
//        fun`returns the list of todos`() {
//            val response = app(Request(GET, "/todo"))
//            val expected: String = "[{\"id\":1,\"title\":\"clean flat\",\"body\":\"notes on how to clean my flat\",\"status\":false},{\"id\":2,\"title\":\"go swimming\",\"body\":\"don't forget goggles\",\"status\":false}]"
//            val actual = response.body
//            assertEquals(expected, actual)
//        }
    }

    @Test
    fun `Ping test`() {
        assertEquals(Response(OK).body("pong"), app(Request(GET, "/ping")))
    }


    @Test
    fun `Check Hamkrest matcher for http4k work as expected`() {
        val request = Request(GET, "/testing/hamkrest?a=b").body("http4k is cool").header("my header", "a value")
    
        val response = app(request)
    
        // response assertions
        assertThat(response, hasStatus(OK))
        assertThat(response, hasBody("Echo 'http4k is cool'"))
    
    
        // other assertions
        // query
        assertThat(request, hasQuery("a", "b"))
    
        // header
        assertThat(request, hasHeader("my header", "a value"))
    
        // body
        assertThat(request, hasBody("http4k is cool"))
        assertThat(request, hasBody(Body.string(TEXT_HTML).toLens(), equalTo("http4k is cool")))
    
        // composite
        assertThat(request, hasBody("http4k is cool").and(hasQuery("a", "b")))
    }

}
