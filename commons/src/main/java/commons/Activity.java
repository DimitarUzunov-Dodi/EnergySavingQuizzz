package commons;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "activity_id")
    private long activityId;
    @Column(name = "activity_text")
    private String activityText;
    @Column(name = "value")
    private long value;
    @Column(name = "source")
    private String source;
    @Column(name = "image_id")
    private long imageId;

    public Activity() {
    }

    public Activity(long activityId, String activityText, long value, String source, long imageId) {
        this.activityId = activityId;
        this.activityText = activityText;
        this.value = value;
        this.source = source;
        this.imageId = imageId;
    }

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
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;

        Activity activity = (Activity) o;

        if (getActivityId() != activity.getActivityId()) return false;
        if (getValue() != activity.getValue()) return false;
        if (getImageId() != activity.getImageId()) return false;
        if (!getActivityText().equals(activity.getActivityText())) return false;
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
}
