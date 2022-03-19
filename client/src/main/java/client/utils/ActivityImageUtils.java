package client.utils;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ActivityImageUtils {

    public static byte[] imageToByteArray(String path) throws IOException, CorruptImageException, ImageNotSupportedException {
        BufferedImage bImage = ImageIO.read(new FileInputStream(path));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if(bImage == null) {
            throw new CorruptImageException("Corrupt Image");
        }

        ImageIO.write(bImage, getFormat(path), outputStream);

        byte[] imageData = outputStream.toByteArray();
        if(imageData.length == 0) {
            return imageToByteArrayAlternative(path);
        }

        return imageData;
    }

    /**
     * converts image to byte array
     * @param path path of the file
     * @return byte array of the image
     * @throws IOException if file is not found or has any other issues related to IO
     */
    public static byte[] imageToByteArrayAlternative(String path) throws IOException, CorruptImageException {
        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
        }

        byte[] imageData = bos.toByteArray();
        if(imageData.length == 0)
            throw new CorruptImageException("Corrupt Image");

        return imageData;
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
