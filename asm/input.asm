global _start

section .data
float_buff:dd 0
double_buff:dd 0,0
comp_re:	dd 0
comp_im:	dd 0
_const_1: dd 3.2
_const_2: dd 8.2
comp2_re:	dd 0
comp2_im:	dd 0
_const_3: dd 2.7
_const_4: dd 2.3
comp3_re:	dd 0
comp3_im:	dd 0
_const_5: dd 2.1
_const_6: dd 3.4
i:	dd 0
p:	dd 0
temp1: db "s"
.len: equ	$ - temp1
temp2: db "RE: "
.len: equ	$ - temp2
_buff_7: dd 0
temp3: db " IM: "
.len: equ	$ - temp3
_buff_8: dd 0
temp4: db " End"
.len: equ	$ - temp4
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
	mov eax,[_const_3]
	mov [comp2_re],eax
	mov eax,[_const_4]
	mov [comp2_im],eax
	mov eax,[_const_5]
	mov [comp3_re],eax
	mov eax,[_const_6]
	mov [comp3_im],eax
	mov eax,dword[comp2_re]
	mov dword[float_buff],eax
	fld dword[float_buff]
	mov eax,dword[comp_re]
	mov dword[float_buff],eax
	fadd dword[float_buff]
	fst dword[float_buff]
	mov eax,dword[float_buff]
	mov ecx,eax
	shl rcx,32
	mov eax,dword[comp2_im]
	mov dword[float_buff],eax
	fld dword[float_buff]
	mov eax,[comp_im]
	mov dword[float_buff],eax
	fadd dword[float_buff]
	fst dword[float_buff]
	xor rax,rax
	mov eax,dword[float_buff]
	or rcx,rax
	mov rax,rcx
	shr rax,32
	mov [comp3_re],eax
	mov eax,ecx
	mov [comp3_im],eax
L0:	mov eax,100
	mov ebx,[i]
	cmp eax,ebx
	mov ecx,eax
	jna L1
	inc dword[i]
	xor rax,rax
	mov ecx, temp1
	mov edx, temp1.len
	call print_str
	jmp L0
L1:	xor rax,rax
	mov ecx, temp2
	mov edx, temp2.len
	call print_str
	mov eax,[comp3_re]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_7]
	mov eax,[_buff_7]
	call print_num
	xor rax,rax
	mov ecx, temp3
	mov edx, temp3.len
	call print_str
	mov eax,[comp3_im]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_8]
	mov eax,[_buff_8]
	call print_num
	xor rax,rax
	mov ecx, temp4
	mov edx, temp4.len
	call print_str

	mov	eax, 1
	mov	ebx, 0
	int	0x80