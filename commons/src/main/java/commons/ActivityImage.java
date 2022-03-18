package commons;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "activity_images")
public class ActivityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "image_id")
    private long imageId;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    public byte[] imageData;

    public ActivityImage() {

    }

    public ActivityImage(long imageId, byte[] imageData) {
        this.imageId = imageId;
        this.imageData = imageData;
    }

    public ActivityImage(byte[] imageData) {
        this.imageData = imageData;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityImage that = (ActivityImage) o;
        return imageId == that.imageId && Arrays.equals(imageData, that.imageData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(imageId);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityImage{" +
                "imageId=" + imageId +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
