package client.scenes;

public class EmojiListCell {
    private String emoji;
    private String name;
    private String joker;

    /**
     * Default constructor used for creating a new cell in the list view.
     * @param emoji emoji code for displaying the emoji
     * @param name name of the user
     * @param joker joker that user might have used
     */
    public EmojiListCell(String emoji, String name, String joker) {
        this.emoji = emoji;
        this.name = name;
        this.joker = joker;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getName() {
        return name;
    }

    public String getJoker() {
        return joker;
    }
}
