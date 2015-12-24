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
z:	dq 123456
y:	dq 654321
zpy:	dq 0
i:	dd 0
j:	dd 0
p:	dd 0
temp1: db "("
.len: equ	$ - temp1
_buff_7: dd 0
temp2: db "+"
.len: equ	$ - temp2
_buff_8: dd 0
temp3: db "i) + "
.len: equ	$ - temp3
temp4: db "("
.len: equ	$ - temp4
_buff_9: dd 0
temp5: db "+"
.len: equ	$ - temp5
_buff_10: dd 0
temp6: db "i) = "
.len: equ	$ - temp6
temp7: db "("
.len: equ	$ - temp7
_buff_11: dd 0
temp8: db "+"
.len: equ	$ - temp8
_buff_12: dd 0
temp9: db "i)"
.len: equ	$ - temp9
temp10: db " Z = "
.len: equ	$ - temp10
temp11: db " Y = "
.len: equ	$ - temp11
temp12: db " Z + Y = "
.len: equ	$ - temp12
temp13: db " Cycle:"
.len: equ	$ - temp13
temp14: db "j"
.len: equ	$ - temp14
temp15: db "i"
.len: equ	$ - temp15
temp16: db " IF TEST: "
.len: equ	$ - temp16
temp17: db "TRUE"
.len: equ	$ - temp17
temp18: db "FALSE"
.len: equ	$ - temp18
temp19: db " End "
.len: equ	$ - temp19
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
	xor rax,rax
	mov ecx, temp1
	mov edx, temp1.len
	call print_str
	mov eax,[comp_re]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_7]
	mov eax,[_buff_7]
	call print_num
	xor rax,rax
	mov ecx, temp2
	mov edx, temp2.len
	call print_str
	mov eax,[comp_im]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_8]
	mov eax,[_buff_8]
	call print_num
	xor rax,rax
	mov ecx, temp3
	mov edx, temp3.len
	call print_str
	xor rax,rax
	mov ecx, temp4
	mov edx, temp4.len
	call print_str
	mov eax,[comp2_re]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_9]
	mov eax,[_buff_9]
	call print_num
	xor rax,rax
	mov ecx, temp5
	mov edx, temp5.len
	call print_str
	mov eax,[comp2_im]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_10]
	mov eax,[_buff_10]
	call print_num
	xor rax,rax
	mov ecx, temp6
	mov edx, temp6.len
	call print_str
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
	xor rax,rax
	mov ecx, temp7
	mov edx, temp7.len
	call print_str
	mov eax,[comp3_re]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_11]
	mov eax,[_buff_11]
	call print_num
	xor rax,rax
	mov ecx, temp8
	mov edx, temp8.len
	call print_str
	mov eax,[comp3_im]
	mov ecx,eax
	mov [p],ecx
	xor rax,rax
	fld dword[p]
	fist dword[_buff_12]
	mov eax,[_buff_12]
	call print_num
	xor rax,rax
	mov ecx, temp9
	mov edx, temp9.len
	call print_str
	xor rax,rax
	mov ecx, temp10
	mov edx, temp10.len
	call print_str
	xor rax,rax
	mov rax,[z]
	call print_num
	xor rax,rax
	mov ecx, temp11
	mov edx, temp11.len
	call print_str
	xor rax,rax
	mov rax,[y]
	call print_num
	xor rax,rax
	mov ecx, temp12
	mov edx, temp12.len
	call print_str
	mov rax,[y]
	mov rbx,[z]
	add rax,rbx
	mov rcx,rax
	mov [zpy],rcx
	xor rax,rax
	mov rax,[zpy]
	call print_num
	xor rax,rax
	mov ecx, temp13
	mov edx, temp13.len
	call print_str
L2:	mov eax,3
	mov ebx,[i]
	cmp eax,ebx
	jna L3
	mov eax,0
	mov [j],eax
L0:	mov eax,3
	mov ebx,[j]
	cmp eax,ebx
	jna L1
	inc dword[j]
	xor rax,rax
	mov ecx, temp14
	mov edx, temp14.len
	call print_str
	jmp L0
L1:	inc dword[i]
	xor rax,rax
	mov ecx, temp15
	mov edx, temp15.len
	call print_str
	jmp L2
L3:	xor rax,rax
	mov ecx, temp16
	mov edx, temp16.len
	call print_str
	mov rax,777777
	mov rbx,[zpy]
	cmp rax,rbx
	jnz L4
	xor rax,rax
	mov ecx, temp17
	mov edx, temp17.len
	call print_str
	jmp L4E
L4:	xor rax,rax
	mov ecx, temp18
	mov edx, temp18.len
	call print_str
L4E:	xor rax,rax
	mov ecx, temp19
	mov edx, temp19.len
	call print_str

	mov	eax, 1
	mov	ebx, 0
	int	0x80