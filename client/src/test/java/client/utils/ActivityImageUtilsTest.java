package client.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import static client.utils.ActivityImageUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ActivityImageUtilsTest {

    private static final String pathToResources = "./src/main/resources/client/images/test_images/";
    private final List<String> images = new ArrayList<>(Arrays.asList(
            "1.jpg",
            "2.jpg",
            "3.jpeg",
            "4.jpg",
            "5.png",
            "6.png"
    ));

    @Test
    void fileToByteArrayTest() {
        for (String filename : images) {
            try {
                String path = pathToResources + filename;
                System.out.println(path);
                BufferedImage imageIn = ImageIO.read(new FileInputStream(path));

                byte[] imageToByteArray = fileToByteArray(path);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageToByteArray);
                BufferedImage imageOut = ImageIO.read(bis);

                assertTrue(bufferedImagesEqual(imageIn, imageOut));


            } catch (IOException | CorruptImageException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void getFormatTest() {
        Map<String, String> formatMap = new HashMap<>();
        formatMap.put("file.jpg", "jpg");
        formatMap.put("file.png", "png");
        formatMap.put("file.jpeg", "jpeg");
        formatMap.put("1.jpg  ", "jpg");
        formatMap.put(". txt ", "txt");
        formatMap.put("12ewe3732&6732.a", "a");
        formatMap.put("abc123ABC. bmp", "bmp");
        formatMap.put("file.o1", "o1");
        formatMap.put("file.test.jpg", "jpg");

        for (String key : formatMap.keySet()) {
            try {
                assertEquals(getFormat(key), formatMap.get(key));
            } catch (ImageNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}