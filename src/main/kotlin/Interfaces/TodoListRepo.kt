package Interfaces

import TodoItem

interface TodoListRepo {
    fun getTodos(): MutableList<TodoItem>
    fun updateTodos(updatedTodoList: MutableList<TodoItem>)

}