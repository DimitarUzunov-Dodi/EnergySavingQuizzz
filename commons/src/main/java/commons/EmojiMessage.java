package commons;

/**
 * Represents the object that is sent via ws to transmit emoji-related updates
 */
public class EmojiMessage {

    public String username;
    public String emojiID;

    public EmojiMessage(){

    }
    public EmojiMessage(String username, String emojiID) {
        this.username = username;
        this.emojiID = emojiID;
    }

    /**
     * Checks if an object is an identical EmojiMessage or not.
     * @param obj - the other object to compare with
     * @return true if equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(EmojiMessage.class)) {
            return false;
        } else {
            EmojiMessage m = (EmojiMessage) obj;
            return username.equals(m.username) && emojiID.equals(m.emojiID);
        }
    }
}
