package domain

import domain.models.TodoItem
import ports.TodoListRepo

class ReadDomain(val todoListRepo: TodoListRepo) {
    fun getTodoList(todoId: String = ""): List<TodoItem>  {
        val todoList: List<TodoItem> = todoListRepo.getTodos()
        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
                it.id == todoId }
        }
    }

    fun getItemsByStatus(status: String): List<TodoItem> {
        val todoList: List<TodoItem> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }
}