package pl.bliw.util;

/**
 * The Binary class provides methods for checking if provided value is valid unsigned type.
 */
public class Binary {
    /**
     * The method checks if value is correct unsigned word
     *
     * @param message message for exception
     * @param value   value to check
     * @throws IllegalArgumentException if value isn't correct unsigned word.
     */
    public static void checkIfItIsUnsignedWordOrThrow(String message, int value) throws IllegalArgumentException {
        boolean isValid = value >= 0x0 && value <= 0xFFFF;
        if (!isValid) {
            throw new IllegalArgumentException(String.format("Expected unsigned word (2 byte value) but received %d%n in %s%n", value, message));
        }
    }

    /**
     * The method checks if value is correct unsigned byte
     * @param message message for exception
     * @param value value to check
     * @throws IllegalArgumentException if value isn't correct unsigned byte.
     */
    public static void checkIfItIsUnsignedByteOrThrow(String message, int value) throws IllegalArgumentException {
        boolean isValid = value >= 0x0 && value <= 0xFF;
        if (!isValid) {
            throw new IllegalArgumentException(String.format("Expected unsigned byte but received %d in %s%n", value, message));
        }
    }
}