package client.Entities;

import javafx.scene.image.ImageView;

public class GameScreenLeaderboardEntry {
    private String username;
    private ImageView emoji;

    public GameScreenLeaderboardEntry(String username) {
        this.username = username;
    }

    public ImageView getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
//        if(emoji.equals(null)){
//            return username;
//        }
        return  username ;//+ " " + emoji;
    }

    public void setEmoji(ImageView emoji) {
        this.emoji = emoji;
    }
}
