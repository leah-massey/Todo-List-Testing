package ports

import domain.models.Todo

interface TodoListRepo {
    fun getTodos(): List<Todo>
    fun updateTodoList(updatedTodoList: List<Todo>)

}