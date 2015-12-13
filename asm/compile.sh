#!/bin/bash
nasm input.asm -f aout -o output.o
ld -m elf_i386 -s -o output output.o
