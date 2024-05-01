package domain


import domain.models.*
import ports.TodoListEventRepo

class ReadDomain(val todoListEventRepo: TodoListEventRepo) {

    fun getTodoListClientView(todoId: String?): List<TodoClientView.FullClientView> {
        if (todoId == null) {
            return getTodoList().map { todo ->
                readDomainTodoClientView(todo)
            }
        } else {
            return getTodoList().filter { todo ->
                todo.id == todoId
            }.map { todo ->
                readDomainTodoClientView(todo)
            }
        }
    }

    fun getTodoListByStatusDoneClientView(): List<TodoClientView.FilteredByStatus> {
        return getTodoListByStatusDone().map { todo ->
            readDomainTodoClientViewByStatus(todo)
        }
    }

    fun getTodoListByStatusNotDoneClientView(): List<TodoClientView.FilteredByStatus> {
        return getTodoListByStatusNotDone().map { todo ->
            readDomainTodoClientViewByStatus(todo)
        }
    }

    private fun getTodoList(todoId: String = ""): List<Todo> {
        if (todoId == "") {
            return todoListEventRepo.getTodoList()
        } else {
            return todoListEventRepo.getTodoList().filter { todo ->
                todo.id == todoId
            }
        }
    }

    private fun getTodoListByStatusDone(): List<Todo> {
        return getTodoList().filter { todo ->
            todo.status == Status.DONE
        }
    }

    private fun getTodoListByStatusNotDone(): List<Todo> {
        return getTodoList().filter { todo ->
            todo.status == Status.NOT_DONE
        }
    }

    private fun readDomainTodoClientView(todo: Todo): TodoClientView.FullClientView {
        return TodoClientView.FullClientView(id = todo.id, name = todo.name, status = todo.status)
    }

    private fun readDomainTodoClientViewByStatus(todo: Todo): TodoClientView.FilteredByStatus {
        return TodoClientView.FilteredByStatus(id = todo.id, name = todo.name)
    }
}



