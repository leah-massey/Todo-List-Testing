package ports

import domain.models.TodoItem

interface TodoListRepo {
    fun getTodos(): List<TodoItem>
    fun updateTodoList(updatedTodoList: List<TodoItem>)

}