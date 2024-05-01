package domain.models

sealed class TodoClientView {

    data class FullClientView(
        val id: String,
        val name: String,
        val status: Status
    )

    data class FilteredByStatus(
        val id: String,
        val name: String,
    )
}


