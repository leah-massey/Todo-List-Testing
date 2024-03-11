package ports

import domain.models.TodoItem

interface TodoListRepo {
    fun getTodos(): MutableList<TodoItem>
    fun updateTodos(updatedTodoList: MutableList<TodoItem>)

}