package commons;

public class ActivityBankEntry {
    private String id;
    private String imagePath;
    private String title;
    private long consumptionInWh;
    private String source;

    /**
     * Constructor, used only for testing, because in app ActivityBankEntry
     * can be created only with ObjectMapper, which doesnt require constructor.
     * @param id id attribute
     * @param imagePath imagePath attribute
     * @param title title attribute
     * @param consumptionInWh consumptionInWh attribute
     * @param source source attribute
     */
    public ActivityBankEntry(String id, String imagePath, String title,
                             long consumptionInWh, String source) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
        this.source = source;
    }

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
