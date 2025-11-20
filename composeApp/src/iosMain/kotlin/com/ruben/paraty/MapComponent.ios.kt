package com.ruben.paraty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.CValue
import platform.MapKit.MKMapView
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKPointAnnotation
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationCoordinate2D
import platform.Foundation.NSURL
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UIViewContentMode
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CALayer
import platform.darwin.NSObject
import kotlinx.cinterop.cValue
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfURL

data class HavanaMarker(
    val latitude: Double,
    val longitude: Double,
    val title: String
)


@OptIn(ExperimentalForeignApi::class)
class CustomAnnotationViewDelegate : NSObject(), MKMapViewDelegateProtocol {
    private val imageUrl = "https://media.istockphoto.com/id/486420378/es/foto/cabezal-de-natación-en-pista-de-baile.jpg?s=612x612&w=0&k=20&c=ejizPt_OXETjf2pzuIKlq6uF-oxIjXpYPgRHrOwiHZA="

    override fun mapView(
        mapView: MKMapView,
        viewForAnnotation: platform.MapKit.MKAnnotationProtocol
    ): MKAnnotationView? {
        if (viewForAnnotation !is MKPointAnnotation) return null
        
        val identifier = "CustomPin"
        val annotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(identifier)
        
        if (annotationView == null) {
            val newAnnotationView = MKAnnotationView(annotation = viewForAnnotation, reuseIdentifier = identifier)
            newAnnotationView.canShowCallout = true
            
            // Crear un UIImageView circular para el marcador
            val imageView = UIImageView(frame = CGRectMake(0.0, 0.0, 50.0, 50.0))
            
            // Intentar cargar la imagen desde la URL
            val url = NSURL.URLWithString(imageUrl)
            url?.let {
                val imageData = NSData.dataWithContentsOfURL(it)
                imageData?.let { data ->
                    val image = UIImage.imageWithData(data)
                    imageView.image = image
                }
            }
            
            // Configurar el imageView para que sea circular
            imageView.contentMode = UIViewContentMode.UIViewContentModeScaleAspectFill
            imageView.clipsToBounds = true
            imageView.layer.cornerRadius = 25.0
            imageView.layer.borderWidth = 3.0
            imageView.layer.borderColor = platform.UIKit.UIColor.whiteColor.CGColor
            
            // Añadir sombra para elegancia
            imageView.layer.shadowColor = platform.UIKit.UIColor.blackColor.CGColor
            imageView.layer.shadowOffset = CGSizeMake(0.0, 2.0)
            imageView.layer.shadowOpacity = 0.3f
            imageView.layer.shadowRadius = 3.0
            
            newAnnotationView.addSubview(imageView)
            newAnnotationView.setFrame(CGRectMake(0.0, 0.0, 50.0, 50.0))
            
            return newAnnotationView
        } else {
            annotationView.setAnnotation(viewForAnnotation)
            return annotationView
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapComponent(modifier: Modifier) {
    // Centro de La Habana, Cuba
    val havanaCenter = CLLocationCoordinate2DMake(23.1355, -82.3589)
    
    // Los mismos marcadores que en Android
    val markers = remember {
        listOf(
            HavanaMarker(23.1355, -82.3589, "Centro Habana"),
            HavanaMarker(23.1367, -82.3589, "Capitolio"),
            HavanaMarker(23.1420, -82.3530, "Malecón"),
            HavanaMarker(23.1330, -82.3830, "Vedado"),
            HavanaMarker(23.1390, -82.3510, "Habana Vieja"),
            HavanaMarker(23.1280, -82.3650, "Plaza de la Revolución"),
            HavanaMarker(23.1440, -82.3480, "Castillo del Morro"),
            HavanaMarker(23.1380, -82.3600, "Parque Central"),
            HavanaMarker(23.1340, -82.3750, "Universidad de La Habana"),
            HavanaMarker(23.1410, -82.3560, "Catedral de La Habana"),
            HavanaMarker(23.1320, -82.3680, "Cementerio de Colón"),
            HavanaMarker(23.1390, -82.3620, "El Prado"),
            HavanaMarker(23.1450, -82.3490, "Fortaleza de San Carlos"),
            HavanaMarker(23.1310, -82.3720, "Plaza de las Américas"),
            HavanaMarker(23.1400, -82.3540, "Museo de la Revolución")
        )
    }
    
    val delegate = remember { CustomAnnotationViewDelegate() }

    UIKitView(
        factory = {
            MKMapView().apply {
                // Establecer el delegado personalizado para los marcadores
                setDelegate(delegate)
                
                // Ocultar los POIs (puntos de interés) predeterminados del mapa
                showsPointsOfInterest = false
                
                // Establecer la región centrada en La Habana
                // La distancia de ~8,000 metros da un zoom más cercano
                val region = MKCoordinateRegionMakeWithDistance(
                    havanaCenter, 
                    8000.0,  // latitudinal meters
                    8000.0   // longitudinal meters
                )
                setRegion(region, animated = false)
                
                // Añadir todos los marcadores
                markers.forEach { marker ->
                    val annotation = MKPointAnnotation()
                    val coordinate = CLLocationCoordinate2DMake(
                        marker.latitude,
                        marker.longitude
                    )
                    annotation.setCoordinate(coordinate)
                    annotation.setTitle(marker.title)
                    addAnnotation(annotation)
                }
            }
        },
        modifier = modifier,
        update = { mapView ->
            // Update logic here if needed
        }
    )
}
