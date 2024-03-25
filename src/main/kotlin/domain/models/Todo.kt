package domain.models

data class Todo(
    val id: String,
    val createdTimestamp: String,
    var lastModifiedTimestamp: String,
    var name: String,
    var status: String = "NOT_DONE"
)
