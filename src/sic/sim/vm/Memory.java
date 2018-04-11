package sic.sim.vm;

import sic.common.Conversion;
import sic.common.Logger;
import sic.common.SICXE;
import sic.sim.breakpoints.MemoryBreakpoints;
import sic.sim.breakpoints.ReadMemoryBreakpointException;
import sic.sim.breakpoints.WriteMemoryBreakpointException;

import java.util.Arrays;

/**
 * Memory
 * @author jure
 */
public class Memory {

    public final byte[] memory;

    public MemoryBreakpoints memoryBreakpoints = new MemoryBreakpoints();

    // returns true if invalid
    private boolean checkAddress(int address) {
        boolean invalid = address < 0 || address >= memory.length;
        if (invalid)
            Logger.fmterr("Invalid memory address '%s', %d", Conversion.addrToHex(address), address);
        return invalid;
    }

    public int getByte(int address) throws ReadMemoryBreakpointException {
        if (checkAddress(address)) return 0;
        memoryBreakpoints.checkRead(address);
        return ((int)memory[address]) & 0xFF;
    }

    public void setByte(int address, int value) throws WriteMemoryBreakpointException {
        if (checkAddress(address)) return;
        memoryBreakpoints.checkWrite(address);
        memory[address] = (byte)(value & 0xFF);
    }

    public int getWord(int address) throws ReadMemoryBreakpointException {
        return getByte(address + 2) | getByte(address + 1) << 8 | getByte(address) << 16;
    }

    public void setWord(int address, int value) throws WriteMemoryBreakpointException {
        setByte(address, value >> 16);
        setByte(address + 1, value >> 8);
        setByte(address + 2, value);
    }

    public double getFloat(int address) throws ReadMemoryBreakpointException {
        long bits  =  (long)getByte(address)  << 40 | (long)getByte(address+1) << 32 |
                      (long)getByte(address+2) << 24 | getByte(address+3) << 16 |
                      getByte(address+4) << 8  | getByte(address+5);
        return SICXE.bitsToFloat(bits);
    }

    public void setFloat(int address, double value) throws WriteMemoryBreakpointException {
        long bits = SICXE.floatToBits(value);
        setByte(address, (int)(bits >> 40));
        setByte(address + 1, (int)(bits >> 32));
        setByte(address + 2, (int)(bits >> 24));
        setByte(address + 3, (int)(bits >> 16));
        setByte(address + 4, (int)(bits >> 8));
        setByte(address + 5, (int)(bits));
    }

    public void reset() {
        Arrays.fill(memory, (byte)0);
    }

    public Memory(int capacity) {
        this.memory = new byte[capacity];
    }


    public int getByteNoBreakpoint(int address)  {
        if (checkAddress(address)) return 0;
        return ((int)memory[address]) & 0xFF;
    }

    public void setByteNoBreakpoint(int address, int value){
        if (checkAddress(address)) return;
        memory[address] = (byte)(value & 0xFF);
    }

}
