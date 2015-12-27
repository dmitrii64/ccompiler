global _start

section .data
float_buff:dd 0
double_buff:dd 0,0
comp_re:	dd 0
comp_im:	dd 0
_const_1: dd 3.2
_const_2: dd 8.2
p:	dd 666.1
z:	dd 0
const_3: dd 1.0
temp1: db " End "
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
	push rax
	push rbx
	push rcx
	push rdx
	push r8
	call clean_buf
	call itoa
	mov eax, 4
    mov ebx, 1
    mov ecx, printbuf
    mov edx, 10
    int 0x80
    pop r8
    pop rdx
    pop rcx
    pop rbx
    pop rax
    ret

print_str:
	mov eax, 4
    mov ebx, 1
    int 0x80
    ret
_start:
	mov eax,[_const_1]
	mov [comp_re],eax
	mov eax,[_const_2]
	mov [comp_im],eax
L0:	mov eax,0
	mov ebx,[p]
	cmp eax,ebx
	mov eax,[const_3]
	mov [float_buff],eax
	fld dword[float_buff]
	fld dword[p]
	fadd dword[float_buff]
	fst dword[float_buff]
	mov edx,[float_buff]
	mov [p],edx
	jmp L0
L1:	xor rax,rax
	mov ecx, temp1
	mov edx, temp1.len
	call print_str

	mov	eax, 1
	mov	ebx, 0
	int	0x80