package domain

import domain.models.EssentialTodoValues
import domain.models.Todo
import ports.TodoListRepo

class ReadDomain(val todoListRepo: TodoListRepo) {

    fun getEssentialFieldsTodoList(): List<EssentialTodoValues> {
        return getTodoList().map{todo ->
            selectEssentialTodoValues(todo)
        }
    }

    fun getTodosByStatus(status: String): List<Todo> {
        val todoList: List<Todo> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }

    private fun getTodoList(todoId: String = ""): List<Todo>  {
        val todoList: List<Todo> = todoListRepo.getTodos()

        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
                it.id == todoId }
        }
    }

    private fun selectEssentialTodoValues(todo: Todo): EssentialTodoValues {
        return EssentialTodoValues(id = todo.id, name = todo.name, status = todo.status)
    }

}