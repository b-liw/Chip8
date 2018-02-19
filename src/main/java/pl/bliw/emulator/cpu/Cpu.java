package pl.bliw.emulator.cpu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.io.Keyboard;
import pl.bliw.emulator.io.Screen;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.emulator.memory.StackMemory;
import pl.bliw.util.Constants;

import java.util.Random;

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
        log.info(Integer.toHexString(opcode).toUpperCase());
        try {
            switch (getInstructionID(opcode)) {
                case 0x0000:
                    switch (extract00NN(opcode)) {
                        case 0x00E0:
                            return () -> {
                                System.exit(0);
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
                        int addressToJump = extract0NNN(opcode);
                        registers.setPC(addressToJump);
                    };
                case 0x2000:
                    return () -> {
                        int address = extract0NNN(opcode);
                        stackMemory.push(registers.getPC() + Constants.DEFAULT_OPCODE_LENGTH);
                        registers.setPC(address);
                    };
                case 0x3000:
                    return () -> {
                        if (registers.get(extract0X00(opcode)) == extract00NN(opcode)) {
                            registers.incrementPC(4); // if vx == NN then skip 4 bytes
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0x4000:
                    return () -> {
                        if (registers.get(extract0X00(opcode)) != extract00NN(opcode)) {
                            registers.incrementPC(4); // if vx != NN then skip 4 bytes
                        } else {
                            registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                        }
                    };
                case 0x6000:
                    return () -> {
                        int registerIndex = extract0X00(opcode);
                        registers.set(registerIndex, extract00NN(opcode));
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0x7000:
                    return () -> {
                        int value = extract00NN(opcode);
                        int registerIndex = extract0X00(opcode);
                        registers.set(registerIndex, (registers.get(registerIndex) + value) & 0xFF);
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0x8000:
                    switch (extract000N(opcode)) {
                        case 0x0000:
                            return () -> {
                                int x = extract0X00(opcode);
                                int y = extract00Y0(opcode);
                                registers.set(x, registers.get(y));
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0002:
                            return () -> {
                                int x = extract0X00(opcode);
                                int vx = registers.get(extract0X00(opcode));
                                int vy = registers.get(extract00Y0(opcode));
                                registers.set(x, vx & vy);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0004:
                            return () -> {
                                int x = extract0X00(opcode);
                                int vx = registers.get(extract0X00(opcode));
                                int vy = registers.get(extract00Y0(opcode));
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
                                int x = extract0X00(opcode);
                                int vx = registers.get(extract0X00(opcode));
                                int vy = registers.get(extract00Y0(opcode));
                                int result = vx - vy;
                                if (result > 0) {
                                    registers.set(VF, 1);
                                } else {
                                    registers.set(VF, 0);
                                }
                                registers.set(x, result & 0xFF);
                                registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                            };

                        default:
                            throw new IllegalArgumentException();
                    }
                case 0xa000:
                    return () -> {
                        registers.setI(extract0NNN(opcode));
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0xc000:
                    return () -> {
                        int registerIndex = extract0X00(opcode);
                        int secondValue = extract0NNN(opcode);
                        int randomValue = (new Random().nextInt(256)) & secondValue;
                        registers.set(registerIndex, randomValue);
                        registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                    };
                case 0xd000:
                    return () -> {
                        registers.set(VF, 0);
                        int xSpriteCoord = registers.get(extract0X00(opcode));
                        int ySpriteCoord = registers.get(extract00Y0(opcode));
                        int heightOfSprite = extract000N(opcode);
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
                        registers.incrementPC(DEFAULT_OPCODE_LENGTH);
                    };
                case 0xe000: {
                    switch (extract00NN(opcode)) {
                        case 0x009e:
                            return () -> {
                                if (keyboard.isPressed(registers.get(extract0X00(opcode)))) {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH * 2);
                                } else {
                                    registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                                }
                            };
                        case 0x00a1:
                            return () -> {
                                if (!keyboard.isPressed(registers.get(extract0X00(opcode)))) {
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
                                registers.set(extract0X00(opcode), timers.getDelayTimer());
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0015:
                            return () -> {
                                timers.setDelayTimer(registers.get(extract0X00(opcode)));
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0018:
                            return () -> {
                                timers.setSoundTimer(registers.get(extract0X00(opcode)));
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0029:
                            return () -> {
                                int x = registers.get(extract0X00(opcode));
                                registers.setI(Constants.FONT_SET_OFFSET_IN_MEMORY + x * 5);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0033:
                            return () -> {
                                int x = registers.get(extract0X00(opcode));
                                memory.write(registers.getI() + 2, x % 10); // BCD code
                                memory.write(registers.getI() + 1, (x / 10) % 10);
                                memory.write(registers.getI(), (x / 100) % 10);
                                registers.incrementPC(Constants.DEFAULT_OPCODE_LENGTH);
                            };
                        case 0x0065:
                            return () -> {
                                int x = extract0X00(opcode);
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
            throw new IllegalArgumentException("NOT SUPPORTED OPCODE 0x" + Integer.toHexString((int) opcode));
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
