package domain

import domain.models.TodoItem
import java.time.LocalDateTime
import java.util.*
import ports.TodoListRepo

class Domain(val todoListRepo: TodoListRepo) {
    fun getTodoList(todoId: String = ""): MutableList<TodoItem>  {
        val todoList: MutableList<TodoItem> = todoListRepo.getTodos()
        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
            it.id == todoId }.toMutableList()
        }
    }

    fun addTodo(todoName: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")
        val newTodoItem = TodoItem(createID(), timeStamp(), timeStamp(), todoName)
        todoList.add(newTodoItem)
        todoListRepo.updateTodos(todoList)
        return "'${todoName}' has been added as a todo."
    }

    fun updateTodoName(todoId: String, updatedTodoName: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.name = updatedTodoName
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
            }
        }
        todoListRepo.updateTodos(todoList)
        return "Your todo has been updated to '${updatedTodoName}'."
    }

    fun updateTodoStatus(todoId: String, updatedTodoStatus: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")
        var nameOfUpdatedTodo: String? = null
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.status = updatedTodoStatus
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                nameOfUpdatedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodos(todoList)
        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to '${updatedTodoStatus}'."
    }

    fun deleteTodo(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")
        var nameOfRemovedTodo: String? = null
        var todoToRemove: TodoItem? = null

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                nameOfRemovedTodo = todoItem.name
                todoToRemove = todoItem
            }
        }
        todoList.remove(todoToRemove)
        todoListRepo.updateTodos(todoList) // send updated list back to json file

        return "Your todo '${nameOfRemovedTodo}' has been deleted."
    }

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun timeStamp(): String {
        return LocalDateTime.now().toString()
    }


}



// cannot make this work!!
//data class TodoList(
//    val items: MutableList<TodoItem> = mutableListOf()
//)



