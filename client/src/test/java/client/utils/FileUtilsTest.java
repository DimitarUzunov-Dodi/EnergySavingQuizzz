package client.utils;

import static client.utils.FileUtils.readNickname;
import static client.utils.FileUtils.writeNickname;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileUtilsTest {

    private static final String pathToUserData = "./src/main/data/";
    private static final String fileName = "Test.userdata";

    private static final File testUserData = new File(pathToUserData + fileName);

    @Test
    void testNicknameIO() {
        List<String> nicknames = new ArrayList<>(Arrays.asList(
                "user",
                "user1",
                "user2",
                "USER12323",
                "Nickname3828",
                "_Nick__Name___493$$$",
                "23903290320",
                "+^%*$#&)()(+=~@"
        ));

        for (String nickname : nicknames) {
            writeNickname(testUserData, nickname);
            assertEquals(nickname, readNickname(testUserData));
        }
    }

}