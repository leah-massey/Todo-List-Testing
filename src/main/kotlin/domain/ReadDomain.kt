package domain

import domain.models.TodoEssentials
import domain.models.Todo
import ports.TodoListRepo

class ReadDomain(val todoListRepo: TodoListRepo) {

    fun getTodoListEssentials(todoId: String = ""): List<TodoEssentials> {
        return getTodoList(todoId).map{todo ->
            selectEssentialTodoFields(todo)
        }
    }

    fun getTodoListEssentialsByStatus(status: String): List<TodoEssentials> {
        return getTodosByStatus(status).map{todo ->
            selectEssentialTodoFields(todo)
        }
    }

    fun getTodosByStatus(status: String): List<Todo> {
        val todoList: List<Todo> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }

    fun getTodoList(todoId: String): List<Todo>  {
        val todoList: List<Todo> = todoListRepo.getTodos()

        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
                it.id == todoId }
        }
    }

    private fun selectEssentialTodoFields(todo: Todo): TodoEssentials {
        return TodoEssentials(id = todo.id, name = todo.name, status = todo.status)
    }

}