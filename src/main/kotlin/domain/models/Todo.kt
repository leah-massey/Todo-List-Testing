package domain.models

data class Todo(
    val id: String,
    val createdTimestamp: String,
    var lastModifiedTimestamp: String,
    var name: String,
    var status: Status = Status.NOT_DONE
)

enum class Status {
    DONE,
    NOT_DONE
}

