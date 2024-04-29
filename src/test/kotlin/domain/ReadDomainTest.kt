package domain

import adapters.TodoListEventFileRepo
import domain.models.Status
import domain.models.Todo
import domain.models.TodoClientView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ports.TodoListEventRepo

class ReadDomainTest {
    @Test
    fun `only the id, name and status of items in a todo List is returned`() {
        val mockEventRepo: TodoListEventRepo = mock(TodoListEventRepo::class.java)
        `when` (mockEventRepo.getTodoList()).thenReturn(listOf(Todo("123", createdTimestamp = "01/02/24", lastModifiedTimestamp ="01/02/24", name = "wash car", status = Status.NOT_DONE ), Todo("456", createdTimestamp = "02/02/24", lastModifiedTimestamp ="02/02/24", name = "feed cat", status = Status.DONE )))
        val readDomain = ReadDomain(mockEventRepo)

        val expected: List<TodoClientView> = listOf(TodoClientView(id = "123", name = "wash car", status = Status.NOT_DONE), TodoClientView(id = "456", name ="feed cat", status = Status.DONE))
        val actual: List<TodoClientView> = readDomain.getTodoListClientView(null)

        assertEquals(expected, actual)
    }


    @Test
    fun `when an id is given, only the relevant todo (consisting of id, name and status) is returned`() {
        val mockEventRepo: TodoListEventRepo = mock(TodoListEventRepo::class.java)
        `when` (mockEventRepo.getTodoList()).thenReturn(listOf(Todo("123", createdTimestamp = "01/02/24", lastModifiedTimestamp ="01/02/24", name = "wash car", status = Status.NOT_DONE ), Todo("456", createdTimestamp = "02/02/24", lastModifiedTimestamp ="02/02/24", name = "feed cat", status = Status.DONE )))
        val readDomain = ReadDomain(mockEventRepo)

        val expected: List<TodoClientView> = listOf(TodoClientView(id = "123", name = "wash car", status = Status.NOT_DONE))
        val actual: List<TodoClientView> = readDomain.getTodoListClientView("123")

        assertEquals(expected, actual)
    }
}