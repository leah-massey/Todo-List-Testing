package Interfaces

import TodoItem

interface TodoListRepoInterface {
    fun getTodos(): MutableList<TodoItem>
    fun updateTodos(updatedTodoList: MutableList<TodoItem>): String
}