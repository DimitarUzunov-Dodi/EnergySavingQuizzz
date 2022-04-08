package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityImageTest {
    private ActivityImage image1;
    private ActivityImage image2;

    @BeforeEach
    void setUp() {
        byte[] a = "Baba ti kozel".getBytes(StandardCharsets.UTF_8);
        image1 = new ActivityImage(1000,a);
        byte[] b = "Lets go".getBytes(StandardCharsets.UTF_8);
        image2 = new ActivityImage(2000,b);
    }

    @Test
    void getImageId() {
        assertEquals(image1.getImageId(),1000);
    }

    @Test
    void setImageId() {
        image1.setImageId(200);
        assertEquals(image1.getImageId(),200);

    }

    @Test
    void getImageData() {
        var a = image1.imageData;
        assertEquals(image1.getImageData(),a);
    }

    @Test
    void setImageData() {
        image1.setImageData("Lets go".getBytes(StandardCharsets.UTF_8));
        assertEquals(image1.getImageData(),image1.getImageData());

    }

    @Test
    void testEquals() {
        byte[] c = "Baba ti kozel".getBytes(StandardCharsets.UTF_8);
        var image3 = new ActivityImage(1000,c);
        assertEquals(image3,image1);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(image1,image2);
    }

    @Test
    void testHashCode() {
        byte[] c = "Baba ti kozel".getBytes(StandardCharsets.UTF_8);
        var image3 = new ActivityImage(1000,c);
        var hash = image3.hashCode();
        assertEquals(hash,image1.hashCode());

    }


}