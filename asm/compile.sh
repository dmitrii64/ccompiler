#!/bin/bash
nasm input.asm -f elf64 -o output.o
ld -m elf_x86_64 -s -o output output.o
