global _start

section .data
a:	dd 0
b:	dd 0
c:	dd 0
temp1: db "\n"
.len: equ	$ - temp1
section .bss
	printbuf resb 10
section .text
itoa:
	enter 4,0
	lea r8,[printbuf+10]
	mov rcx,10
	mov [rbp-4],dword 0

	.divloop:
	xor rdx,rdx
	idiv rcx
	add rdx,0x30
	dec r8
	mov byte [r8],dl
	inc dword [rbp-4]

	cmp rax,0
	jnz .divloop

	mov rax,r8
	mov rcx,[rbp-4]

	leave
	ret

clean_buf:
	lea r8,[printbuf+10]
	mov rcx,10
	.clear_loop:
	mov byte[r8],0
	dec r8
	dec rcx
	cmp rcx,0
	jnz .clear_loop
	xor rcx,rcx
	xor r8,r8
	ret

print_num:
	call clean_buf
	call itoa
	mov eax, 4
    mov ebx, 1
    mov ecx, printbuf
    mov edx, 10
    int 0x80
    ret

print_str:
	mov eax, 4
    mov ebx, 1
    int 0x80
    ret

_start:
	mov eax,2
	mov [b],eax
	mov eax,5
	mov [c],eax
	mov eax,3
	mov ebx,[b]
	add eax,ebx
	mov ecx,eax
	mov eax,20
	mov ebx,ecx
	imul eax,ebx
	mov ecx,eax
	mov [a],ecx
	mov eax,101
	mov ebx,[a]
	cmp eax,ebx
	mov ecx,eax
	jnz L0
	mov eax,1337
	mov [c],eax
	jmp L0E
L0:	mov eax,666
	mov [c],eax
L0E:	xor rax,rax
	mov ecx, temp1
	mov edx, temp1.len
	call print_str
	xor rax,rax
	mov eax,[c]
	call print_num
	xor rax,rax
	mov eax,[a]
	call print_num

	mov	eax, 1 ; exit
	mov	ebx, 0
	int	0x80   ; exit(0)