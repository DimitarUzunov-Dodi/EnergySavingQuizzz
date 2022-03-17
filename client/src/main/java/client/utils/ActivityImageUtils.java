package client.utils;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ActivityImageUtils {

    /**
     * converts image to byte array
     * @param path path of the file
     * @return byte array of the image
     * @throws IOException if file is not found or has any other issues related to IO
     * @throws ImageNotSupportedException if image format is not supported
     */
    public static byte[] imageToByteArray(String path) throws IOException, ImageNotSupportedException, CorruptImageException {
        BufferedImage bImage = ImageIO.read(new FileInputStream(path));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if(bImage == null)
            throw new CorruptImageException("Corrupt Image!");

        ImageIO.write(bImage, getFormat(path), outputStream);

        return outputStream.toByteArray();
    }

    /**
     *
     * @param imageData - byte array with data about image
     * @return image applicable to JavaFX ImageView
     */
    public static Image imageFromByteArray(byte[] imageData) {
        return new Image(new ByteArrayInputStream(imageData));
    }

    /**
     * Gets format for writing image data into byte array
     * @param path path to the file
     * @return format(png, jpg, jpeg)
     * @throws ImageNotSupportedException if format is not supported
     */
    public static String getFormat(String path) throws ImageNotSupportedException {
        int index = path.lastIndexOf('.');
        if(index > 0) {
            return path.substring(index + 1).trim();
        }

        throw new ImageNotSupportedException("Wrong image extension!");
    }
}
