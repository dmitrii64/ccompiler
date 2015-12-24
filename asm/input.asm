global _start

section .data
float_buff:dd 0
double_buff:dd 0,0
d_re:	dd 0
d_im:	dd 0
_const_1: dd 33
_const_2: dd 22
k_re:	dd 0
k_im:	dd 0
_const_3: dd 0.1
_const_4: dd 123.1
y_re:	dd 0
y_im:	dd 0
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
	mov eax,[_const_1]
	mov [d_re],eax
	mov eax,[_const_2]
	mov [d_im],eax
	mov eax,[_const_3]
	mov [k_re],eax
	mov eax,[_const_4]
	mov [k_im],eax
	mov [y],ecx

	mov	eax, 1
	mov	ebx, 0
	int	0x80