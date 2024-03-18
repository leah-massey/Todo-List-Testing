import adapters.HttpApi
import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.ReadDomain
import domain.WriteDomain
import ports.TodoListRepo
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import ports.TodoListEventRepo

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val todoListEventRepo: TodoListEventRepo = TodoListEventFileRepo("./src/resources/todo_list_event_log.ndjson")
    val readDomain = ReadDomain(todoListRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)
    val httpApi = HttpApi(readDomain, writeDomain)

    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(httpApi.app)

    val server = printingApp.asServer(SunHttp(3000)).start()

    println("Server started on " + server.port())
}
