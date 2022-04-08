package commons;

public class ActivityBankEntry {
    public String id;
    //CHECKSTYLE:OFF
    public String image_path;
    //CHECKSTYLE:ON
    public String title;
    //CHECKSTYLE:OFF
    public long consumption_in_wh;
    //CHECKSTYLE:ON
    public String source;



    public String getId() {
        return id;
    }


    /**
     * GETTER.
     * @return value
     */
    public String getImagePath() {
        //CHECKSTYLE:OFF
        return image_path;
        //CHECKSTYLE:ON
    }

    public String getTitle() {
        return title;
    }

    /**
     * GETTER.
     * @return value
     */
    public long getConsumptionInWh() {
        //CHECKSTYLE:OFF
        return consumption_in_wh;
        //CHECKSTYLE:ON
    }

    public String getSource() {
        return source;
    }
}
