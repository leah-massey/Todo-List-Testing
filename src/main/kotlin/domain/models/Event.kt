package domain.models

sealed class TodoEvent {
    abstract val eventType: EventType // these abstract properties cannot be instantiated.
    abstract val eventId: String
    abstract val eventCreatedDate: String
    abstract val entityId: String
}

class TodoCreatedEvent(
    override val eventType: EventType = EventType.TODO_CREATED,
    override val eventId: String,
    override val eventCreatedDate: String,
    override val entityId: String,
    val eventDetails: Todo
): TodoEvent()

class TodoNameUpdatedEvent(
    override val eventType: EventType = EventType.TODO_NAME_UPDATED,
    override val eventId: String,
    override val eventCreatedDate: String,
    override val entityId: String,
    val eventDetails: TodoNameUpdate
): TodoEvent()

class TodoStatusUpdatedEvent(
    override val eventType: EventType = EventType.TODO_STATUS_UPDATED,
    override val eventId: String,
    override val eventCreatedDate: String,
    override val entityId: String,
    val eventDetails: TodoStatusUpdate
): TodoEvent()

enum class EventType {
    TODO_CREATED,
    TODO_NAME_UPDATED,
    TODO_STATUS_UPDATED
}