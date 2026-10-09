package readability.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInteraction {
    public static boolean isReadable(String filename) {
        Path path = Path.of(filename);

        // Doing isRegularFile because isReadable returns true for directories too
        return Files.isRegularFile(path) && Files.isReadable(path);
    }

    public static String readFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }
}
