package pl.bliw.emulator.io;

import java.util.HashMap;
import java.util.Map;

/**
 * The class Keyboard represents chip8 keyboard which has only 16 buttons. 1-9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF.
 */
public class Keyboard {
    /**
     * The number of buttons on keyboard.
     */
    private static final int NUM_OF_AVAILABLE_KEYS = 16;

    /**
     * Internal array used for storing state of key.
     * true - key is pressed
     * false - key is released
     */
    private boolean[] keys = new boolean[NUM_OF_AVAILABLE_KEYS];

    /**
     * Map used to translate normal PC keys into chip8 keyboard keys.
     */
    private Map<String, Integer> keyCodeMap = new HashMap<>();

    /**
     * Constructs new keyboard.
     */
    public Keyboard() {
        initializeKeyCodeMap();
    }

    /**
     * Changes state of given key to pressed.
     *
     * @param code pressed key value.
     */
    public void keyPressed(String code) {
        if (keyCodeMap.containsKey(code)) {
            keys[keyCodeMap.get(code)] = true;
        }
    }

    /**
     * Changes state of given key to released.
     * @param code released key value.
     */
    public void keyReleased(String code) {
        if (keyCodeMap.containsKey(code)) {
            keys[keyCodeMap.get(code)] = false;
        }
    }

    /**
     * Returns an array with keys state.
     * @return an array with keys state
     */
    public boolean[] getKeys() {
        return keys;
    }

    /**
     * Creates entries in hash map, which translates standard PC keyboard codes into chip8 keyboard codes.
     */
    private void initializeKeyCodeMap() {
        // https://cdn.hackaday.io/images/5663601486879354232.png
        keyCodeMap.put("1", 1);
        keyCodeMap.put("2", 2);
        keyCodeMap.put("3", 3);
        keyCodeMap.put("4", 0xc);
        keyCodeMap.put("Q", 4);
        keyCodeMap.put("W", 5);
        keyCodeMap.put("E", 6);
        keyCodeMap.put("R", 0xd);
        keyCodeMap.put("A", 7);
        keyCodeMap.put("S", 8);
        keyCodeMap.put("D", 9);
        keyCodeMap.put("F", 0xe);
        keyCodeMap.put("Z", 0xa);
        keyCodeMap.put("X", 0x0);
        keyCodeMap.put("C", 0xb);
        keyCodeMap.put("V", 0xf);
    }

    /**
     * The method checks if given key is pressed on chip8 keyboard.
     * Only keys with codes 1-9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF can be checked.
     * @param keyID key code
     * @return boolean which indicates if key is pressed
     * @throws IllegalArgumentException if given key is invalid for chip8 keyboard
     */
    public boolean isPressed(int keyID) {
        if (keyID < 0 || keyID >= NUM_OF_AVAILABLE_KEYS) {
            throw new IllegalArgumentException("Key ID is incorrect, use one which is valid for chip8 keyboard");
        }
        return keys[keyID];
    }
}
