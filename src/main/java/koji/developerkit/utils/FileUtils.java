package koji.developerkit.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

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
                copy(paramInputStream, fileOutputStream);
                fileOutputStream.close();
            } finally {
                closeQuietly(fileOutputStream);
            }
        } finally {
            closeQuietly(paramInputStream);
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }


    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[1024 * 4]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
