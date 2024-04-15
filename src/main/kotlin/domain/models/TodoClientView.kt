package domain.models

data class TodoClientView(
    val id: String,
    val name: String,
    val status: Status
)
