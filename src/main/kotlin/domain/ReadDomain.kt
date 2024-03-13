package domain

import domain.models.EssentialTodoItemValues
import domain.models.EssentialTodoItemValuesByStatus
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

    private fun selectEssentialTodoItemValues(todo: TodoItem): EssentialTodoItemValues {
        return EssentialTodoItemValues(id = todo.id, name = todo.name, status = todo.status)
    }

    fun getEssentialTodoList(): List<EssentialTodoItemValues> {
        return getTodoList().map{todo ->
            selectEssentialTodoItemValues(todo)
        }
    }


    fun getTodosByStatus(status: String): List<TodoItem> {
        val todoList: List<TodoItem> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }

//    fun getTodoList(todoId: String = ""): List<EssentialTodoItemValues>  {
//        val todoList: List<TodoItem> = todoListRepo.getTodos()
//
//        if (todoId == "") {
//            return todoList.map{todo ->
//                selectEssentialTodoItemValues(todo)
//            }
//        } else {
//            return todoList.filter {
//                it.id == todoId }.map { todo ->
//                selectEssentialTodoItemValues(todo)
//            }
//
//        }
//    }
//
//    private fun selectEssentialTodoItemValues(todo: TodoItem): EssentialTodoItemValues {
//        return EssentialTodoItemValues(id = todo.id, name = todo.name, status = todo.status)
//    }
//
//    fun
//
//    fun getItemsByStatus(status: String): List<EssentialTodoItemValuesByStatus> {
//        val todoList: List<EssentialTodoItemValues> = getTodoList("")
//        val todoListByStatus: List<TodoItem> = todoList.filter { todo ->
//            todo.status == status
//        }
//
//        return .map {todo ->
//            selectEssentialTodoItemValues(todo)
//        }
//    }
}