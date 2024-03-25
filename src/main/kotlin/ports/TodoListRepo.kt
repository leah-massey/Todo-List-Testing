package ports

import domain.models.Todo

interface TodoListRepo {
    fun getTodoList(): List<Todo>
    fun updateTodoList(updatedTodoList: List<Todo>)

}