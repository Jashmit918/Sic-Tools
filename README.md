
#### *New feature in this fork: Keyboard*
The Keyboard can be opened by navigating to *View > Keyboard*.  

Whenever a key is pressed (note that the Keyboard has to be focused), the key's character value is stored to a predefined location in the memory (default `0xC000`).  
In a SIC program that is utilising the Keyboard, the program should clear the memory location where character values are stored after they are read.

---

# SicTools
Tools for SIC/XE hypothetical computer from the Leland Beck's book System Software. Includes:
  * Assembler
  * Simulator
  * Linker

Assembler supports all instructions and directives described in the book. This includes load/store instructions, arithmetic instructions, jumps etc. And directives START, END, ORG, LTORG, BASE, NOBASE, CSECT, USE, EQU, RESB, RESW, EXTDEF, EXTREF. Some features:
  * immediate addressing, indirect addressing, simple addressing
  * PC-relative addressing, base addressing (BASE and NOBASE directive), indexed addressing
  * standard directives START, END, ORG
  * support for literals, LTORG directive
  * support for EQU expressions, full forward and backward references resolved via the algorithm described in the book
  * support for sections via CSECT directive
  * support for blocks via USE directive
  * assembler syntax is (see command line options) more flexbile and free than original
  * floating-point syntax extensions (FLOT and RESF directives)
  * syntax extension for specifying numbers in binary, octal, decimal and hexadecimal format
  * generates debugging-friendly listing file showing original source and corresponding address and generated code
  * generates log file showing code statistics, list of blocks, list of sections, list of symbols, list of literals, list of relocations
  * and more

Simulator is user-friendly GUI based application that loads asm or obj files. Features:
  * CPU view of registers and current instructions, shows changed registers in different color, supports changing registers values
  * disassembly view and breakpoints
  * memory view with full edit support in hexadecimal and character mode
  * textual screen support
  * devices 0, 1, 2 are redirected to standard input, output and error
  * detected pseudo HALT instruction (jump on itself)
  * automatic execution with set speed (from 1 Hz to 1 MHz)
  * and more

Linker supports linking .obj files produced by the assembler into one. Each object file can have multiple control sections and needs to be relative. Other features include:
  * a graphical interface for selecting .obj files and choosing the linker settings
  * inspecting, editing and reordering control sections or symbols in a gui or textual interface
  * partial linking when some of the references are not present
  * option to keep symbols in the output file to allow further linking
  * and more


See also http://jurem.github.io/SicTools/ for the main page as well as https://github.com/jurem/SicDemos for several examples.

Installation
------------

Download or clone source code and run make.

    git clone https://github.com/jurem/SicTools.git
    cd SicTools
    make jar

Usage
-----

To run simulator

    java -jar out/make/sictools.jar

To run assembler

    java -cp out/make/sictools.jar sic.Asm source.asm

where source.asm is the file to be compiled.

To get assembler help

    java -cp out/make/sictools.jar sic.Asm -help

To run linker

    java -cp out/make/sictools.jar sic.Link -o out.obj in1.obj in2.obj ...

where out.obj is the output file and in1, in2,... are .obj files to be linked.

To get linker help

    java -cp out/make/sictools.jar sic.Link -help

To get graphical linker interface

    java -cp out/make/sictools.jar sic.Link -g
