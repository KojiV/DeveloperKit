package koji.developerkit.utils;

import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static void loadFile(InputStream paramInputStream, File paramFile) throws IOException, InvalidConfigurationException {

        if (!paramFile.exists()) {
            FileUtils.copyInputStreamToFile(paramInputStream, paramFile);
        }
        YamlConfiguration.loadConfiguration(paramFile).load(paramFile);
    }

    public static FileOutputStream openOutputStream(File paramFile) throws IOException {
        return openOutputStream(paramFile, false);
    }

    public static FileOutputStream openOutputStream(File paramFile, boolean paramBoolean) throws IOException {
        if (paramFile.exists()) {
            if (paramFile.isDirectory())
                throw new IOException("File '" + paramFile + "' exists but is a directory");
            if (!paramFile.canWrite())
                throw new IOException("File '" + paramFile + "' cannot be written to");
        } else {
            File file = paramFile.getParentFile();
            if (file != null &&
                    !file.mkdirs() && !file.isDirectory())
                throw new IOException("Directory '" + file + "' could not be created");
        }
        return new FileOutputStream(paramFile, paramBoolean);
    }

    public static void copyInputStreamToFile(InputStream paramInputStream, File paramFile) throws IOException {
        try {
            FileOutputStream fileOutputStream = openOutputStream(paramFile);
            try {
                IOUtils.copy(paramInputStream, fileOutputStream);
                fileOutputStream.close();
            } finally {
                IOUtils.closeQuietly(fileOutputStream);
            }
        } finally {
            IOUtils.closeQuietly(paramInputStream);
        }
    }
}
