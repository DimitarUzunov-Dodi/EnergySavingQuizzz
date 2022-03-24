package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "activity_id")
    private long activityId;
    @Column(name = "activity_text", length = 512)
    private String activityText;
    @Column(name = "value")
    private long value;
    @Column(name = "source", length = 512)
    private String source;
    @Column(name = "image_id")
    private long imageId;

    public Activity() {
    }

    /**
     * Basic constructor for Activity entity.
     * @param activityId activity id
     * @param activityText activity text
     * @param value activity value
     * @param source activity source
     * @param imageId activity image id
     */
    public Activity(long activityId, String activityText, long value, String source, long imageId) {
        this.activityId = activityId;
        this.activityText = activityText;
        this.value = value;
        this.source = source;
        this.imageId = imageId;
    }

    /**
     * Constructor for Activity entity without an id.
     * @param activityText activity text
     * @param value activity value
     * @param source activity source
     * @param imageId activity image id
     */
    public Activity(String activityText, long value, String source, long imageId) {
        this.activityText = activityText;
        this.value = value;
        this.source = source;
        this.imageId = imageId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityText() {
        return activityText;
    }

    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }

        Activity activity = (Activity) o;

        if (getActivityId() != activity.getActivityId()) {
            return false;
        }
        if (getValue() != activity.getValue()) {
            return false;
        }
        if (getImageId() != activity.getImageId()) {
            return false;
        }
        if (!getActivityText().equals(activity.getActivityText())) {
            return false;
        }
        return getSource().equals(activity.getSource());
    }

    @Override
    public int hashCode() {
        int result = (int) (getActivityId() ^ (getActivityId() >>> 32));
        result = 31 * result + getActivityText().hashCode();
        result = 31 * result + (int) getValue();
        result = 31 * result + getSource().hashCode();
        result = 31 * result + (int) (getImageId() ^ (getImageId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Activity{"
                + "activityId=" + activityId
                + ", activityText='" + activityText + '\''
                + ", value=" + value
                + ", source='" + source + '\''
                + ", imageId=" + imageId
                + '}';
    }

}
