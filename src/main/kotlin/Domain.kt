import Interfaces.TodoListRepoInterface

class Domain(val todoListRepo: TodoListRepoInterface) {

    fun getTodoList(todoId: String): List<TodoItem>  {
        val todoList: List<TodoItem> = todoListRepo.getTodos()
        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter{
                it.id == todoId
            }
        }
    }

    // addTodo
    // updateTodo
    // deleteTodo
}

data class TodoItem(
    val id: String,
    val createdDate: String,
    val modifiedDate: String,
    var name: String,
    var status: String
)

data class TodoList(
     val items: List<TodoItem>
)
