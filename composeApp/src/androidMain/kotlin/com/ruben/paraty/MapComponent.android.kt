package com.ruben.paraty

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class MarkerLocation(val position: LatLng, val title: String)

@Composable
actual fun MapComponent(modifier: Modifier) {
    val context = LocalContext.current
    
    // Centro de La Habana, Cuba - usando coordenadas del centro histórico
    val havanaCenter = LatLng(23.1355, -82.3589)
    
    // Marcadores en diferentes ubicaciones de La Habana
    val markers = remember {
        listOf(
            MarkerLocation(LatLng(23.1355, -82.3589), "Centro Habana"),
            MarkerLocation(LatLng(23.1367, -82.3589), "Capitolio"),
            MarkerLocation(LatLng(23.1420, -82.3530), "Malecón"),
            MarkerLocation(LatLng(23.1330, -82.3830), "Vedado"),
            MarkerLocation(LatLng(23.1390, -82.3510), "Habana Vieja"),
            MarkerLocation(LatLng(23.1280, -82.3650), "Plaza de la Revolución"),
            MarkerLocation(LatLng(23.1440, -82.3480), "Castillo del Morro"),
            MarkerLocation(LatLng(23.1380, -82.3600), "Parque Central"),
            MarkerLocation(LatLng(23.1340, -82.3750), "Universidad de La Habana"),
            MarkerLocation(LatLng(23.1410, -82.3560), "Catedral de La Habana"),
            MarkerLocation(LatLng(23.1320, -82.3680), "Cementerio de Colón"),
            MarkerLocation(LatLng(23.1390, -82.3620), "El Prado"),
            MarkerLocation(LatLng(23.1450, -82.3490), "Fortaleza de San Carlos"),
            MarkerLocation(LatLng(23.1310, -82.3720), "Plaza de las Américas"),
            MarkerLocation(LatLng(23.1400, -82.3540), "Museo de la Revolución")
        )
    }

    var customMarkerIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

    // La imagen personalizada del marcador
    val markerImageUrl = "https://media.istockphoto.com/id/486420378/es/foto/cabezal-de-natación-en-pista-de-baile.jpg?s=612x612&w=0&k=20&c=ejizPt_OXETjf2pzuIKlq6uF-oxIjXpYPgRHrOwiHZA="


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(havanaCenter, 13f) // Zoom 13 para ver la ciudad
    }

    // Cargar la imagen y crear el icono personalizado
    LaunchedEffect(Unit) {
        customMarkerIcon = loadCircularMarkerIcon(context, markerImageUrl)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false
        ),
        properties = MapProperties(
            isMyLocationEnabled = false,
            isTrafficEnabled = false,
            // Esto oculta los POIs (puntos de interés) predeterminados del mapa
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, com.ruben.paraty.R.raw.map_style)
        )
    ) {
        // Solo mostrar marcadores si el icono personalizado está cargado
        if (customMarkerIcon != null) {
            markers.forEach { markerLocation ->
                Marker(
                    state = MarkerState(position = markerLocation.position),
                    title = markerLocation.title,
                    icon = customMarkerIcon
                )
            }
        }
    }
}

// Función para cargar la imagen y crear un ícono circular
private suspend fun loadCircularMarkerIcon(
    context: Context,
    imageUrl: String
): BitmapDescriptor? = withContext(Dispatchers.IO) {
    try {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        
        val result = imageLoader.execute(request)
        
        if (result is SuccessResult) {
            val bitmap = result.image.toBitmap()
            val circularBitmap = createCircularBitmapWithBorder(bitmap, size = 120, borderWidth = 6)
            BitmapDescriptorFactory.fromBitmap(circularBitmap)
        } else {
            // Fallback a marcador por defecto
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
    }
}

// Función para crear un bitmap circular con borde blanco elegante
private fun createCircularBitmapWithBorder(
    source: Bitmap,
    size: Int = 120,
    borderWidth: Int = 6
): Bitmap {
    val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    
    val paint = Paint().apply {
        isAntiAlias = true
    }
    
    // Dibujar el borde blanco
    paint.color = android.graphics.Color.WHITE
    canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
    
    // Preparar el círculo interior para la imagen
    val rect = Rect(borderWidth, borderWidth, size - borderWidth, size - borderWidth)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    
    // Escalar la imagen al tamaño del círculo interior
    val scaledBitmap = Bitmap.createScaledBitmap(
        source,
        size - borderWidth * 2,
        size - borderWidth * 2,
        true
    )
    
    // Crear clip circular para la imagen
    paint.reset()
    paint.isAntiAlias = true
    canvas.drawCircle(size / 2f, size / 2f, (size - borderWidth * 2) / 2f, paint)
    
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(scaledBitmap, borderWidth.toFloat(), borderWidth.toFloat(), paint)
    
    return output
}
