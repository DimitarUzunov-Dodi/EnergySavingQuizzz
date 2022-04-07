package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EmojiMessageTest {


    @Test
    void testEquals() {
        EmojiMessage emojiMessage1 = new EmojiMessage("Dodi","emoji1");
        EmojiMessage emojiMessage2 = new EmojiMessage("Dodi","emoji1");
        assertEquals(emojiMessage1,emojiMessage2);
    }
}