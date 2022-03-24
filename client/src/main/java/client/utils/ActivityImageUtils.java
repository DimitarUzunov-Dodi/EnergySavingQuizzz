package client.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class ActivityImageUtils {

    /**
     * Transforms images to byte arrays.
     * @param path - path to image
     * @return byte array from image
     * @throws IOException - file exception
     * @throws CorruptImageException - image cannot be read
     * @throws ImageNotSupportedException - image is in the wrong format
     */
    public static byte[] imageToByteArray(String path)
            throws IOException, CorruptImageException, ImageNotSupportedException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(path));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (bufferedImage == null) {
            throw new CorruptImageException("Corrupt Image");
        }

        ImageIO.write(bufferedImage, getFormat(path), outputStream);

        byte[] imageData = outputStream.toByteArray();
        if (imageData.length == 0) {
            return fileToByteArray(path);
        }

        return imageData;
    }

    /**
     * converts image that cannot be processed by ImageIO to byte array.
     * @param path path of the file
     * @return byte array of the image
     * @throws IOException if file is not found or has any other issues related to IO
     */
    public static byte[] fileToByteArray(String path) throws IOException, CorruptImageException {
        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
        }

        byte[] imageData = bos.toByteArray();
        if (imageData.length == 0) {
            throw new CorruptImageException("Corrupt Image");
        }

        return imageData;
    }

    /**
     * Restores image for JavaFX from byte array.
     * @param imageData - byte array with data about image
     * @return image applicable to JavaFX ImageView
     */
    public static Image imageFromByteArray(byte[] imageData) {
        return new Image(new ByteArrayInputStream(imageData));
    }

    /**
     * Gets format for writing image data into byte array.
     * @param path path to the file
     * @return format(png, jpg, jpeg)
     * @throws ImageNotSupportedException if format is not supported
     */
    public static String getFormat(String path) throws ImageNotSupportedException {
        int index = path.lastIndexOf('.');
        if (index > 0) {
            return path.substring(index + 1).trim();
        }

        throw new ImageNotSupportedException("Wrong image extension!");
    }
}
