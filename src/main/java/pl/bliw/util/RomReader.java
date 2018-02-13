package pl.bliw.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RomReader {

    public static byte[] readRomAsBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

}
