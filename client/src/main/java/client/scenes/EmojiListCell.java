package client.scenes;

public class EmojiListCell {
    private String emoji;
    private String name;
    private String joker;

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
