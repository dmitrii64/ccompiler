global _start

section .data
float_buff:dd 0
double_buff:dd 0,0
a:	dd 2.0
b:	dd 5.0
c:	dd 0
const_1: dd 2.0
temp1: db "Hello world!"
.len: equ	$ - temp1
_buff_2: dd 0
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
	fld dword[b]
	mov [float_buff],ecx
	fadd dword[float_buff]
	fst dword[float_buff]
	mov ecx,[float_buff]
	mov eax,[const_1]
	mov [float_buff],eax
	fld dword[float_buff]
	mov [float_buff],ecx
	fmul dword[float_buff]
	fst dword[float_buff]
	mov ecx,[float_buff]
	mov [c],ecx
	xor rax,rax
	mov ecx, temp1
	mov edx, temp1.len
	call print_str
	xor rax,rax
	fld dword[c]
	fist dword[_buff_2]
	mov eax,[_buff_2]
	call print_num

	mov	eax, 1 ; exit
	mov	ebx, 0
	int	0x80   ; exit(0)