package integrationTests

import adapters.TodoListEventFileRepo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ports.TodoListEventRepo
import unitTests.ReadDomain
import unitTests.models.EventType
import unitTests.models.Status
import unitTests.models.Todo
import unitTests.models.TodoCreatedEvent
import java.io.File

class TodoListEventFileRepoTest {


    private val testFilePath = "./src/resources/test-events.ndjson"

    @BeforeEach
    fun setUp() {
        clearTestFile()
    }

    private fun clearTestFile() {
        val testFile = File(testFilePath)
        testFile.writeText("")
    }

    @Test
    fun `a todo is created and added to the file`() {
        val todoListEventFileRepo: TodoListEventRepo =
            TodoListEventFileRepo(testFilePath)

        todoListEventFileRepo.addEvent(
            TodoCreatedEvent(
                eventType = EventType.TODO_CREATED,
                eventId = "8231e29c-76f9-4901-a2ce-d22344f8e846",
                eventCreatedDate = "2024-04-15T15:27:57.564129",
                entityId = "3e90673e-5b1b-4ae3-81d9-30f904003bdcc",
                eventDetails = Todo(
                    id = "3e90673e-5b1b-4ae3-81d9-30f904a3bdcc",
                    createdTimestamp = "2024-04-15T15:27:57.562332",
                    lastModifiedTimestamp = "2024-04-15T15:27:57.563202",
                    name = "play",
                    status = Status.NOT_DONE
                )
            )
        )

        val expected: List<Todo> = listOf(
            Todo(
                id = "3e90673e-5b1b-4ae3-81d9-30f904003bdcc",
                createdTimestamp = "2024-04-15T15:27:57.564129",
                lastModifiedTimestamp = "2024-04-15T15:27:57.564129",
                name = "play",
                status = Status.NOT_DONE
            )
        )
        val actual: List<Todo> = todoListEventFileRepo.getTodoList()

        assertEquals(expected, actual)

    }
}