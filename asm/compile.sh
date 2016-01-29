#!/bin/bash
nasm input.asm -f elf64 -F dwarf -g -l output.lst -o output.o &&
    gcc -nostdlib -o output output.o
