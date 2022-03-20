package commons;

public class ActivityBankEntry {
    private String id;
    private String image_path;
    private String title;
    private long consumption_in_wh;
    private String source;

    public String getId() {
        return id;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getTitle() {
        return title;
    }

    public long getConsumption_in_wh() {
        return consumption_in_wh;
    }

    public String getSource() {
        return source;
    }
}
