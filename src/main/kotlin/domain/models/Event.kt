package domain.models

open class TodoEvent(
    val eventType: String,
    val eventId: String,
    val eventCreatedDate: String,
    val entityId: String,
)

class TodoCreatedEvent(
    eventType: String = "TODO_CREATED",
    eventId: String,
    eventCreatedDate: String,
    entityId: String,
    val eventDetails: Todo
): TodoEvent(eventType, eventId, eventCreatedDate, entityId)

class TodoNameUpdatedEvent(
    eventType: String = "TODO_NAME_UPDATED",
    eventId: String,
    eventCreatedDate: String,
    entityId: String,
    val eventDetails: TodoNameUpdate
): TodoEvent(eventType, eventId, eventCreatedDate, entityId)

class TodoStatusUpdatedEvent(
    eventType: String = "TODO_STATUS_UPDATED",
    eventId: String,
    eventCreatedDate: String,
    entityId: String,
    val eventDetails: TodoStatusUpdate
): TodoEvent(eventType, eventId, eventCreatedDate, entityId)



