import adapters.HttpApi
import adapters.TodoListFileRepo
import domain.ReadDomain
import domain.WriteDomain
import ports.TodoListRepo
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val readDomain = ReadDomain(todoListRepo)
    val writeDomain = WriteDomain(todoListRepo, readDomain)
    val httpApi = HttpApi(readDomain, writeDomain)

    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(httpApi.app)

    val server = printingApp.asServer(SunHttp(3000)).start()

    println("Server started on " + server.port())
}
