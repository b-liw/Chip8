package pl.bliw.util;

public class Binary {

    public static void checkIfItIsUnsignedWordOrThrow(String message, int value) throws IllegalArgumentException {
        boolean isValid = value >= 0x0 && value <= 0xFFFF;
        if (!isValid) {
            throw new IllegalArgumentException(String.format("Expected unsigned word (2 byte value) but received %d%n in %s%n", value, message));
        }
    }

    public static void checkIfItIsUnsignedByteOrThrow(String message, int value) throws IllegalArgumentException {
        boolean isValid = value >= 0x0 && value <= 0xFF;
        if (!isValid) {
            throw new IllegalArgumentException(String.format("Expected unsigned byte but received %d in %s%n", value, message));
        }
    }

}