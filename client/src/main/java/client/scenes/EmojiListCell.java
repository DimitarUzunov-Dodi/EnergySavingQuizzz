package client.scenes;

public class EmojiListCell {
    private String emoji;
    private String name;

    public EmojiListCell(String emoji, String name) {
        this.emoji = emoji;
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getName() {
        return name;
    }

}
