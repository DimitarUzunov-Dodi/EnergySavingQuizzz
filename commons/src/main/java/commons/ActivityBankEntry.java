package commons;

public class ActivityBankEntry {
    private String id;
    private String imagePath;
    private String title;
    private long consumptionInWh;
    private String source;

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public long getConsumptionInWh() {
        return consumptionInWh;
    }

    public String getSource() {
        return source;
    }
}
