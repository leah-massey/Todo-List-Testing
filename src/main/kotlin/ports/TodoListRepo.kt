package ports

import domain.TodoItem

interface TodoListRepo {
    fun getTodos(): MutableList<TodoItem>
    fun updateTodos(updatedTodoList: MutableList<TodoItem>)

}