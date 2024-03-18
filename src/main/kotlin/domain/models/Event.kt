package domain.models

open class TodoEvent(
    val eventType: String,
    val eventId: String,
    val eventCreatedDate: String,
    val entityId: String,
)

class TodoCreatedEvent(
    eventType: String = "TODO_CREATED", // not sure why naming was not accepted at eventType, also.
    eventId: String,
    eventCreatedDate: String,
    entityId: String,
    val eventDetails: Todo
): TodoEvent(eventType, eventId, eventCreatedDate, entityId)

data class TodoNameUpdatedEvent(
    val todoEventType: String = "TODO_NAME_UPDATED",
    val todoEventId: String,
    val todoEventCreatedDate: String,
    val todoEntityId: String,
    val eventDetails: TodoNameUpdate
): TodoEvent(eventType = todoEventType, eventId = todoEventId, eventCreatedDate = todoEventCreatedDate, entityId = todoEntityId)

data class TodoStatusUpdatedEvent(
    val todoEventType: String = "TODO_NAME_UPDATED",
    val todoEventId: String,
    val todoEventCreatedDate: String,
    val todoEntityId: String,
    val eventDetails: TodoStatusUpdate
): TodoEvent(eventType = todoEventType, eventId = todoEventId, eventCreatedDate = todoEventCreatedDate, entityId = todoEntityId)



