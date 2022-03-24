package client.utils;

import static client.utils.ActivityImageUtils.imageToByteArray;

import client.communication.AdminCommunication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.ActivityBankEntry;
import commons.ActivityImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ActivityBankUtils {
    private static final String pathToBankZip = "./src/main/data/";
    private static final String zipName = "oopp-activity-bank.zip";
    private static final String pathToResources = "./src/main/resources/client/images/";
    private static final String defaultImageName = "default-image.png";


    /**
     * Unzips activity bank archive to a predefined path.
     * @throws IOException - exception regarding file IO
     */
    public static void unzipActivityBank() throws IOException {
        String fileZip = pathToBankZip + zipName;
        File destDir = new File(pathToBankZip + "/unzipped");
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
        }
        zis.closeEntry();
        zis.close();
        /*
        userAlert("ERROR", "Unable to load archive",
                "Error occurred while trying to read an archive");
         */
    }

    /**
     * New file out of destination directory and a zipEntry.
     * @param destinationDir - directory to save outcome into
     * @param zipEntry - archive with activity bank
     * @return new File in destinationDir
     * @throws IOException - exception regarding file IO
     */
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    /**
     * Extract json into the database.
     * @throws IOException - exception regarding file IO
     */
    public static void jsonToActivityBankEntry() throws IOException {
        String jsonActivityBankArray = new String(Files.readAllBytes(
                Paths.get(pathToBankZip + "unzipped/activities.json")));
        ObjectMapper objectMapper = new ObjectMapper();
        List<ActivityBankEntry> activityBankEntryList = objectMapper.readValue(
                jsonActivityBankArray,
                new TypeReference<List<ActivityBankEntry>>() {});

        long imageId;
        for (ActivityBankEntry entry : activityBankEntryList) {
            imageId = uploadImage(pathToBankZip + "unzipped/" + entry.getImage_path());
            AdminCommunication.addActivityBankEntry(entry, imageId);
        }
    }

    /**
     * Uploads image to a database and returns id of an image.
     * @param path - path to image(for tranformation into byte[]).
     * @return id of the image saved into an image db
     */
    private static long uploadImage(String path) {
        try {
            return AdminCommunication
                    .addActivityImage(new ActivityImage(imageToByteArray(path)))
                    .readEntity(Long.class);
        } catch (IOException | CorruptImageException | ImageNotSupportedException ex) {
            return uploadImage(pathToResources + defaultImageName);
        }
    }
}
