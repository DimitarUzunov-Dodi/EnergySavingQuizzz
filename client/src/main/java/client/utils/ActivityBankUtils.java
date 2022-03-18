package client.utils;

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

import static client.utils.ActivityImageUtils.imageToByteArray;

public class ActivityBankUtils {
    private static final String pathToBankZip = "./src/main/data/";
    private static final String zipName = "oopp-activity-bank.zip";

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
        // userAlert("ERROR", "Unable to load archive", "Error occurred while trying to read an archive");
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public static void jsonToActivityBankEntry() throws IOException {
        String jsonActivityBankArray = new String(Files.readAllBytes(Paths.get(pathToBankZip + "unzipped/activities.json")));;
        ObjectMapper objectMapper = new ObjectMapper();
        List<ActivityBankEntry> activityBankEntryList = objectMapper.readValue(jsonActivityBankArray, new TypeReference<List<ActivityBankEntry>>() {
        });

        long imageId;
        for(ActivityBankEntry entry : activityBankEntryList) {
            imageId = uploadImage(pathToBankZip + "unzipped/" + entry.getImage_path());
            AdminCommunication.addActivityBankEntry(entry, imageId);

        }
    }

    public static long uploadImage(String path) {
        try {
            return AdminCommunication
                    .addActivityImage(new ActivityImage(imageToByteArray(path)))
                    .readEntity(Long.class);
        } catch (IOException | ImageNotSupportedException | CorruptImageException e) {
            e.printStackTrace();
            return -1; // TODO change to default
        }
    }
}
