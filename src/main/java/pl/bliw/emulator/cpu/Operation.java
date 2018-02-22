package pl.bliw.emulator.cpu;

/**
 * Base interface for all operations which can be executed on chip8 CPU.
 */
public interface Operation {
    /**
     * Executes operation
     */
    void execute();

}
