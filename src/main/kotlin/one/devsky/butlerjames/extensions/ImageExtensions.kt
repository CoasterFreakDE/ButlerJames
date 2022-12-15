package one.devsky.butlerjames.extensions

import java.awt.AlphaComposite
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

// Overlay a image on another and resize it to the base image with the same aspect ratio and opacity
fun BufferedImage?.overlayImage(foreground: BufferedImage, opacity: Float): BufferedImage {
    val background = this ?: BufferedImage(foreground.width, foreground.height, BufferedImage.TYPE_INT_ARGB)
    // Create a new image with the same dimensions as the background
    val overlay = BufferedImage(background.width, background.height, BufferedImage.TYPE_INT_ARGB)

    // Get the graphics context for the new image
    val g = overlay.createGraphics()

    // Draw the background onto the new image
    g.drawImage(background, 0, 0, null)

    // Set the alpha composite of the graphics context to use the given opacity
    val composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
    g.composite = composite

    // Draw the foreground onto the new image with the given opacity
    val scaleX = background.width.toFloat() / foreground.width.toFloat()
    val scaleY = background.height.toFloat() / foreground.height.toFloat()
    val transform = AffineTransform.getScaleInstance(scaleX.toDouble(), scaleY.toDouble())
    g.drawImage(foreground, transform, null)

    // Return the new image
    return overlay
}