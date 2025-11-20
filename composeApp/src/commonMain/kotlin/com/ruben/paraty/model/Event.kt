package com.ruben.paraty.model

/**
 * Modelo de un evento
 */
data class Event(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val coverImageUrl: String? = null,
    val eventType: EventType = EventType.CONCIERTO,
    val price: Double = 0.0,
    val date: String = "", // Formato: "yyyy-MM-dd"
    val startTime: String = "", // Formato: "HH:mm"
    val endTime: String = "", // Formato: "HH:mm"
    val location: EventLocation = EventLocation()
)

/**
 * Ubicación del evento
 */
data class EventLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
)

/**
 * Tipos de eventos disponibles
 */
enum class EventType(val displayName: String) {
    CONCIERTO("Concierto"),
    FIESTA("Fiesta"),
    FESTIVAL("Festival"),
    TEATRO("Teatro"),
    DEPORTIVO("Deportivo"),
    CONFERENCIA("Conferencia"),
    EXPOSICION("Exposición"),
    OTRO("Otro")
}
