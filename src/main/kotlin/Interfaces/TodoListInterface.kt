package Interfaces

import TodoItem

interface TodoListRepoInterface {
    fun getTodos(): List<TodoItem>
}