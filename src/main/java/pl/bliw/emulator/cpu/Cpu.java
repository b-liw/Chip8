package pl.bliw.emulator.cpu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.io.Keyboard;
import pl.bliw.emulator.io.Screen;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.emulator.memory.StackMemory;
import pl.bliw.util.Constants;

import java.util.Random;

import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.V0;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.VF;
import static pl.bliw.util.Constants.BITS_IN_BYTE;
import static pl.bliw.util.Constants.DEFAULT_OPCODE_LENGTH;

public class Cpu {

    private static Log log = LogFactory.getLog(Cpu.class);
    private Registers registers;
    private Memory memory;
    private StackMemory stackMemory;
    private Screen screen;
    private Timers timers;
    private Keyboard keyboard;

    public Cpu(Registers registers, Memory memory, StackMemory stackMemory, Screen screen, Timers timers, Keyboard keyboard) {
        this.registers = registers;
        this.memory = memory;
        this.stackMemory = stackMemory;
        this.screen = screen;
        this.timers = timers;
        this.keyboard = keyboard;
    }

    public void run() {
        char opcode = fetchNextOpcode();
        Operation decodedOperation = decode(opcode);
        execute(decodedOperation);
    }

    private char fetchNextOpcode() {
        return (char) (memory.read(registers.getPC()) << 8 | memory.read(registers.getPC() + 1));
    }

    private Operation decode(char opcode) {
        /**
         * // https://en.wikipedia.org/wiki/CHIP-8#Opcode_table
         * NNN: address
         * NN: 8-bit constant
         * N: 4-bit constant
         * X and Y: 4-bit register identifier
         * PC : Program Counter
         * I : 16bit register (For memory address)
         */
        log.info(Integer.toHexString(opcode).toUpperCase());
        int x = extract0X00(opcode);
        int y = extract00Y0(opcode);
        int vx = registers.get(x);
        int vy = registers.get(y);
        int n = extract000N(opcode);
        int nn = extract00NN(opcode);
        int nnn = extract0NNN(opcode);
        try {
            switch (getInstructionID(opcode)) {
                case 0x0000:
                    switch (extract00NN(opcode)) {
                        case 0x00E0:
                            return () -> {
                                screen.clear();
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x00EE:
                            return () -> {
                                int returnAddress = stackMemory.pop();
                                registers.setPC(returnAddress);
                            };
                        default:
                            throw new IllegalArgumentException();
                    }
                case 0x1000:
                    return () -> {
                        registers.setPC(nnn);
                    };
                case 0x2000:
                    return () -> {
                        stackMemory.push(registers.getPC() + Constants.DEFAULT_OPCODE_LENGTH);
                        registers.setPC(nnn);
                    };
                case 0x3000:
                    return () -> {
                        if (vx == nn) {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH * 2); // if vx == NN then skip 4 bytes
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0x4000:
                    return () -> {
                        if (vx != nn) {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH * 2); // if vx != NN then skip 4 bytes
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0x5000:
                    return () -> {
                        if (vx == vy) {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH * 2);
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0x6000:
                    return () -> {
                        registers.set(x, nn);
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0x7000:
                    return () -> {
                        registers.set(x, (registers.get(x) + nn) & 0xFF);
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0x8000:
                    switch (extract000N(opcode)) {
                        case 0x0000:
                            return () -> {
                                registers.set(x, registers.get(y));
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0001:
                            return () -> {
                                registers.set(x, vx | vy);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0002:
                            return () -> {
                                registers.set(x, vx & vy);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0003:
                            return () -> {
                                registers.set(x, vx ^ vy);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0004:
                            return () -> {
                                int result = vx + vy;
                                if (result > 255) {
                                    registers.set(VF, 1);
                                } else {
                                    registers.set(VF, 0);
                                }
                                registers.set(x, result & 0xFF);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0005:
                            return () -> {
                                int result = vx - vy;
                                if (result > 0) {
                                    registers.set(VF, 1);
                                } else {
                                    registers.set(VF, 0);
                                }
                                registers.set(x, result & 0xFF);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0006:
                            return () -> {
                                registers.set(VF, vx & 1);
                                registers.set(x, vx >> 1);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0007:
                            return () -> {
                                int result = vy - vx;
                                if (result <= 0) {
                                    registers.set(VF, 0);
                                } else {
                                    registers.set(VF, 1);
                                }
                                registers.set(x, result & 0xFF);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x000e:
                            return () -> {
                                registers.set(VF, (vx >> 7) & 1);
                                registers.set(x, vx << 1);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        default:
                            throw new IllegalArgumentException();
                    }
                case 0x9000:
                    return () -> {
                        if (vx != vy) {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH * 2);
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0xa000:
                    return () -> {
                        registers.setI(nnn);
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0xb000:
                    return () -> {
                        registers.setPC(nnn + registers.get(V0));
                    };
                case 0xc000:
                    return () -> {
                        int randomValue = (new Random().nextInt(256)) & nnn;
                        registers.set(x, randomValue);
                        registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                    };
                case 0xd000:
                    return () -> {
                        registers.set(VF, 0);
                        int xSpriteCoord = vx;
                        int ySpriteCoord = vy;
                        int heightOfSprite = n;
                        for (int yOffset = 0; yOffset < heightOfSprite; yOffset++) {
                            int currentLineOfSprite = memory.read(registers.getI() + yOffset);
                            for (int xOffset = 0; xOffset < BITS_IN_BYTE; xOffset++) {
                                int currentPixel = (currentLineOfSprite >> BITS_IN_BYTE - xOffset - 1) & 1;
                                if (currentPixel == 1) {
                                    int newOffsetInScreenBuffer = ((xSpriteCoord + xOffset) % Screen.getWidth()) + ((ySpriteCoord + yOffset) % Screen.getHeight()) * Screen.getWidth();
                                    if (newOffsetInScreenBuffer < Screen.getWidth() * Screen.getHeight()) {
                                        if (screen.getPixel(newOffsetInScreenBuffer)) {
                                            registers.set(VF, 1);
                                        }
                                        screen.flipPixel(newOffsetInScreenBuffer);
                                    }
                                }
                            }
                        }
                        screen.setCanvasUpdated(true);
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0xe000: {
                    switch (extract00NN(opcode)) {
                        case 0x009e:
                            return () -> {
                                if (keyboard.isPressed(vx)) {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH * 2);
                                } else {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                                }
                            };
                        case 0x00a1:
                            return () -> {
                                if (!keyboard.isPressed(vx)) {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH * 2);
                                } else {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                                }
                            };
                        default:
                            throw new IllegalArgumentException();
                    }
                }
                case 0xf000:
                    switch (extract00NN(opcode)) {
                        case 0x0007:
                            return () -> {
                                registers.set(x, timers.getDelayTimer());
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x000a:
                            return () -> {
                                boolean[] keys = keyboard.getKeys();
                                for (int i = 0; i < keys.length; i++) {
                                    if (keys[i]) {
                                        registers.set(x, i);
                                        registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                                        break;
                                    }
                                }
                            };
                        case 0x0015:
                            return () -> {
                                timers.setDelayTimer(registers.get(x));
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0018:
                            return () -> {
                                timers.setSoundTimer(registers.get(x));
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x001e:
                            return () -> {
                                registers.setI(registers.getI() + registers.get(x));
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0029:
                            return () -> {
                                registers.setI(Constants.FONT_SET_OFFSET_IN_MEMORY + vx * 5);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0033:
                            return () -> {
                                memory.write(registers.getI() + 2, vx % 10); // BCD code
                                memory.write(registers.getI() + 1, (vx / 10) % 10);
                                memory.write(registers.getI(), (vx / 100) % 10);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0055: {
                            return () -> {
                                for (int i = 0; i <= x; i++) {
                                    memory.write(registers.getI() + i, registers.get(i));
                                }
                                registers.setI(registers.getI() + x + 1);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        }
                        case 0x0065:
                            return () -> {
                                for (int i = 0; i <= x; i++) {
                                    registers.set(i, memory.read(registers.getI() + i));
                                }
                                registers.setI(registers.getI() + x + 1);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        default:
                            throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("NOT SUPPORTED OPCODE 0x" + Integer.toHexString((int) opcode).toUpperCase());
        }
    }

    private void execute(Operation operation) {
        operation.execute();
    }

    private int getInstructionID(char opcode) {
        return opcode & 0xF000;
    }

    private int extract0NNN(char opcode) {
        return opcode & 0x0FFF;
    }

    private int extract0X00(char opcode) {
        return (opcode & 0x0F00) >> 8;
    }

    private int extract00NN(char opcode) {
        return opcode & 0x00FF;
    }

    private int extract000N(char opcode) {
        return opcode & 0x000F;
    }

    private int extract00Y0(char opcode) {
        return (opcode & 0x00F0) >> 4;
    }

}
