package pl.bliw.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The RomReader class provides static utility function that reads file as byte array.
 */
public class RomReader {
    /**
     * @param file File to read
     * @return read file represented as byte array
     * @throws IOException if provided file cannot be found or read
     */
    public static byte[] readRomAsBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
}
