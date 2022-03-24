package commons;

import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "activity_images")
public class ActivityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    @SequenceGenerator(name = "image_seq", sequenceName = "image_seq_table")
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
        return "ActivityImage{"
                + "imageId=" + imageId
                + ", imageData=" + Arrays.toString(imageData)
                + '}';
    }
}
