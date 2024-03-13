package domain.models

data class Todo(
    val id: String,
    val createdDate: String,
    var lastModifiedDate: String,
    var name: String,
    var status: String = "NOT_DONE"
)
