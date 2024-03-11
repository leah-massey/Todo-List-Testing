import domain.Domain
import ports.TodoListRepo
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val todoListRepo: TodoListRepo = TodoListRepoJSON()
    val domain = Domain(todoListRepo)

    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(HttpApi(domain).app)

    val server = printingApp.asServer(SunHttp(3000)).start()

    println("Server started on " + server.port())
}