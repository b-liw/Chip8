package pl.bliw.emulator.io;

import java.util.HashMap;
import java.util.Map;

public class Keyboard {

    private static final int NUM_OF_AVAILABLE_KEYS = 16;
    private boolean[] keys = new boolean[NUM_OF_AVAILABLE_KEYS];
    private Map<String, Integer> keyCodeMap = new HashMap<>();

    public Keyboard() {
        initializeKeyCodeMap();
    }

    public void keyPressed(String code) {
        if (keyCodeMap.containsKey(code)) {
            keys[keyCodeMap.get(code)] = true;
        }
    }

    public void keyReleased(String code) {
        if (keyCodeMap.containsKey(code)) {
            keys[keyCodeMap.get(code)] = false;
        }
    }

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

}
