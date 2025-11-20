package com.ruben.paraty

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruben.paraty.auth.AuthButton
import com.ruben.paraty.auth.AuthTextField
import com.ruben.paraty.auth.ImageIcon
import com.ruben.paraty.auth.LocationIcon
import com.ruben.paraty.model.Event
import com.ruben.paraty.model.EventLocation
import com.ruben.paraty.model.EventType
import com.ruben.paraty.theme.ParatyBlue

/**
 * Pantalla para agregar un nuevo evento (solo para usuarios tipo NEGOCIO)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    modifier: Modifier = Modifier,
    onEventCreated: (Event) -> Unit = {}
) {
    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var selectedEventType by remember { mutableStateOf(EventType.CONCIERTO) }
    var selectedLocation by remember { mutableStateOf<EventLocation?>(null) }
    var coverImageUrl by remember { mutableStateOf<String?>(null) }
    var showEventTypePicker by remember { mutableStateOf(false) }
    var showLocationPicker by remember { mutableStateOf(false) }
    var showImagePicker by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun createEvent() {
        val event = Event(
            name = eventName,
            description = description,
            price = price.toDoubleOrNull() ?: 0.0,
            date = date,
            startTime = startTime,
            endTime = endTime,
            eventType = selectedEventType,
            location = selectedLocation ?: EventLocation(),
            coverImageUrl = coverImageUrl
        )
        onEventCreated(event)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nuevo Evento",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Cover Image Selector
            Text(
                text = "Foto del evento",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            CoverImageSelector(
                imageUrl = coverImageUrl,
                onSelectImage = { showImagePicker = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Event Name
            Text(
                text = "Nombre del evento",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuthTextField(
                value = eventName,
                onValueChange = { eventName = it },
                placeholder = "Ej: Festival de Música",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            Text(
                text = "Descripción",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Describe tu evento...") },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ParatyBlue,
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Event Type
            Text(
                text = "Tipo de evento",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            EventTypeSelector(
                selectedType = selectedEventType,
                onClick = { showEventTypePicker = true }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Location
            Text(
                text = "Ubicación",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            LocationSelector(
                location = selectedLocation,
                onClick = { showLocationPicker = true }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Date
            Text(
                text = "Fecha",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuthTextField(
                value = date,
                onValueChange = { date = it },
                placeholder = "DD/MM/AAAA",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Time Range
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hora de inicio",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5F6368)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AuthTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        placeholder = "HH:MM",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hora de fin",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5F6368)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AuthTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        placeholder = "HH:MM",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Price
            Text(
                text = "Precio",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6368)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuthTextField(
                value = price,
                onValueChange = { price = it },
                placeholder = "0.00",
                keyboardType = KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Create Event Button
            AuthButton(
                text = "Crear Evento",
                onClick = ::createEvent,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Event Type Picker Dialog
    if (showEventTypePicker) {
        EventTypePickerDialog(
            selectedType = selectedEventType,
            onDismiss = { showEventTypePicker = false },
            onTypeSelected = { type ->
                selectedEventType = type
                showEventTypePicker = false
            }
        )
    }

    // Location Picker (placeholder - will be implemented with map)
    if (showLocationPicker) {
        LocationPickerDialog(
            onDismiss = { showLocationPicker = false },
            onLocationSelected = { location ->
                selectedLocation = location
                showLocationPicker = false
            }
        )
    }

    // Image Picker (placeholder)
    if (showImagePicker) {
        ImagePickerDialog(
            onDismiss = { showImagePicker = false },
            onImageSelected = { url ->
                coverImageUrl = url
                showImagePicker = false
            }
        )
    }
}

/**
 * Selector de imagen de portada
 */
@Composable
private fun CoverImageSelector(
    imageUrl: String?,
    onSelectImage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (imageUrl == null) Color(0xFFF5F5F5) else Color.Transparent
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onSelectImage),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageIcon(
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF9E9E9E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Agregar foto de portada",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF9E9E9E)
                    )
                )
            }
        } else {
            // TODO: Display actual image
            Text("Imagen seleccionada", color = Color(0xFF5F6368))
        }
    }
}

/**
 * Selector de tipo de evento
 */
@Composable
private fun EventTypeSelector(
    selectedType: EventType,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(28.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = selectedType.displayName,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF1F1F1F)
            )
        )
    }
}

/**
 * Selector de ubicación
 */
@Composable
private fun LocationSelector(
    location: EventLocation?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(28.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocationIcon(
                modifier = Modifier.size(20.dp),
                tint = if (location == null) Color(0xFF9E9E9E) else ParatyBlue
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = location?.address ?: "Seleccionar ubicación",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = if (location == null) Color(0xFF9E9E9E) else Color(0xFF1F1F1F)
                )
            )
        }
    }
}

/**
 * Diálogo para seleccionar tipo de evento
 */
@Composable
private fun EventTypePickerDialog(
    selectedType: EventType,
    onDismiss: () -> Unit,
    onTypeSelected: (EventType) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Selecciona tipo de evento",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        text = {
            Column {
                EventType.entries.forEach { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTypeSelected(type) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = type == selectedType,
                            onClick = { onTypeSelected(type) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ParatyBlue
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = type.displayName,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar", color = ParatyBlue)
            }
        }
    )
}

/**
 * Diálogo para seleccionar ubicación (placeholder - implementar con mapa)
 */
@Composable
private fun LocationPickerDialog(
    onDismiss: () -> Unit,
    onLocationSelected: (EventLocation) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar ubicación",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        text = {
            // TODO: Implementar mapa para selección de ubicación
            Text("Selector de mapa (por implementar)")
        },
        confirmButton = {
            TextButton(onClick = {
                // Ubicación de ejemplo
                onLocationSelected(
                    EventLocation(
                        latitude = 40.416775,
                        longitude = -3.703790,
                        address = "Madrid, España"
                    )
                )
            }) {
                Text("Seleccionar", color = ParatyBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF5F6368))
            }
        }
    )
}

/**
 * Diálogo para seleccionar imagen (placeholder)
 */
@Composable
private fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onImageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar imagen",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        text = {
            // TODO: Implementar galería de imágenes
            Text("Selector de imagen (por implementar)")
        },
        confirmButton = {
            TextButton(onClick = {
                // Imagen de ejemplo
                onImageSelected("https://example.com/image.jpg")
            }) {
                Text("Seleccionar", color = ParatyBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF5F6368))
            }
        }
    )
}
