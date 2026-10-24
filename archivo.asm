IMPRIME Macro Mensaje
Mov Ah, 09h
mov dx, offset Mensaje
int 21h
EndM
LEER Macro Entrada
mov Ah,0Ah
mov dx, offset Entrada
int 21h
EndM
.MODEL SMALL
.CODE
Inicio:
mov Ax,@Data
mov Ds,Ax

mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_1
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_1
dest_conv_non_zero_1:
xor cx, cx
dest_div_loop_1:
cmp ax, 0
je dest_pop_loop_1
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_1
dest_pop_loop_1:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_1
mov byte ptr [si], '$'
dest_conv_end_1:

EtiMientras1:
; CICLO MIENTRAS VALIDO == 0
mov ax, VALIDO_num
mov bx, 0
cmp ax, bx
jne EtiFinMientras1
IMPRIME Msg2
IMPRIME Salto

IMPRIME Salto
LEER A
xor ax, ax
xor cx, cx
mov cl, [A + 1]
cmp cl, 0
je EtiParseEnd_A
mov si, offset A + 2
EtiParseLoop_A:
mov dx, 10
mul dx
xor bx, bx
mov bl, [si]
sub bl, 30h
add ax, bx
inc si
loop EtiParseLoop_A
EtiParseEnd_A:
mov A_num, ax
xor bx, bx
mov bl, [A + 1]
mov byte ptr [A + 2 + bx], '$'
mov Al, [A + 2]
mov A, Al
IMPRIME Salto

IMPRIME Msg3
IMPRIME Salto

IMPRIME Salto
LEER B
xor ax, ax
xor cx, cx
mov cl, [B + 1]
cmp cl, 0
je EtiParseEnd_B
mov si, offset B + 2
EtiParseLoop_B:
mov dx, 10
mul dx
xor bx, bx
mov bl, [si]
sub bl, 30h
add ax, bx
inc si
loop EtiParseLoop_B
EtiParseEnd_B:
mov B_num, ax
xor bx, bx
mov bl, [B + 1]
mov byte ptr [B + 2 + bx], '$'
mov Al, [B + 2]
mov B, Al
IMPRIME Salto

IMPRIME Msg4
IMPRIME Salto

IMPRIME Salto
LEER C
xor ax, ax
xor cx, cx
mov cl, [C + 1]
cmp cl, 0
je EtiParseEnd_C
mov si, offset C + 2
EtiParseLoop_C:
mov dx, 10
mul dx
xor bx, bx
mov bl, [si]
sub bl, 30h
add ax, bx
inc si
loop EtiParseLoop_C
EtiParseEnd_C:
mov C_num, ax
xor bx, bx
mov bl, [C + 1]
mov byte ptr [C + 2 + bx], '$'
mov Al, [C + 2]
mov C, Al
IMPRIME Salto

mov ax, 1
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_5
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_5
dest_conv_non_zero_5:
xor cx, cx
dest_div_loop_5:
cmp ax, 0
je dest_pop_loop_5
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_5
dest_pop_loop_5:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_5
mov byte ptr [si], '$'
dest_conv_end_5:

; CONDICION SI A < 1
mov ax, A_num
mov bx, 1
cmp ax, bx
jge EtiSi1
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_6
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_6
dest_conv_non_zero_6:
xor cx, cx
dest_div_loop_6:
cmp ax, 0
je dest_pop_loop_6
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_6
dest_pop_loop_6:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_6
mov byte ptr [si], '$'
dest_conv_end_6:

jmp EtiFin1
EtiSi1:

EtiFin1:

; CONDICION SI A > 255
mov ax, A_num
mov bx, 255
cmp ax, bx
jle EtiSi2
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_7
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_7
dest_conv_non_zero_7:
xor cx, cx
dest_div_loop_7:
cmp ax, 0
je dest_pop_loop_7
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_7
dest_pop_loop_7:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_7
mov byte ptr [si], '$'
dest_conv_end_7:

jmp EtiFin2
EtiSi2:

EtiFin2:

; CONDICION SI B < 1
mov ax, B_num
mov bx, 1
cmp ax, bx
jge EtiSi3
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_8
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_8
dest_conv_non_zero_8:
xor cx, cx
dest_div_loop_8:
cmp ax, 0
je dest_pop_loop_8
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_8
dest_pop_loop_8:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_8
mov byte ptr [si], '$'
dest_conv_end_8:

jmp EtiFin3
EtiSi3:

EtiFin3:

; CONDICION SI B > 255
mov ax, B_num
mov bx, 255
cmp ax, bx
jle EtiSi4
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_9
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_9
dest_conv_non_zero_9:
xor cx, cx
dest_div_loop_9:
cmp ax, 0
je dest_pop_loop_9
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_9
dest_pop_loop_9:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_9
mov byte ptr [si], '$'
dest_conv_end_9:

jmp EtiFin4
EtiSi4:

EtiFin4:

; CONDICION SI C < 1
mov ax, C_num
mov bx, 1
cmp ax, bx
jge EtiSi5
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_10
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_10
dest_conv_non_zero_10:
xor cx, cx
dest_div_loop_10:
cmp ax, 0
je dest_pop_loop_10
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_10
dest_pop_loop_10:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_10
mov byte ptr [si], '$'
dest_conv_end_10:

jmp EtiFin5
EtiSi5:

EtiFin5:

; CONDICION SI C > 255
mov ax, C_num
mov bx, 255
cmp ax, bx
jle EtiSi6
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_11
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_11
dest_conv_non_zero_11:
xor cx, cx
dest_div_loop_11:
cmp ax, 0
je dest_pop_loop_11
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_11
dest_pop_loop_11:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_11
mov byte ptr [si], '$'
dest_conv_end_11:

jmp EtiFin6
EtiSi6:

EtiFin6:

; CONDICION SI VALIDO == 1
mov ax, VALIDO_num
mov bx, 1
cmp ax, bx
jne EtiSi7
mov ax, 0
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_12
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_12
dest_conv_non_zero_12:
xor cx, cx
dest_div_loop_12:
cmp ax, 0
je dest_pop_loop_12
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_12
dest_pop_loop_12:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_12
mov byte ptr [si], '$'
dest_conv_end_12:

; CONDICION SI A < B
mov ax, A_num
mov bx, B_num
cmp ax, bx
jge EtiSi8
; CONDICION SI B < C
mov ax, B_num
mov bx, C_num
cmp ax, bx
jge EtiSi9
mov ax, C_num
mov MAYOR_num, ax
mov si, offset MAYOR
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_13
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_13
dest_conv_non_zero_13:
xor cx, cx
dest_div_loop_13:
cmp ax, 0
je dest_pop_loop_13
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_13
dest_pop_loop_13:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_13
mov byte ptr [si], '$'
dest_conv_end_13:

mov ax, B_num
mov MEDIO_num, ax
mov si, offset MEDIO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_14
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_14
dest_conv_non_zero_14:
xor cx, cx
dest_div_loop_14:
cmp ax, 0
je dest_pop_loop_14
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_14
dest_pop_loop_14:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_14
mov byte ptr [si], '$'
dest_conv_end_14:

mov ax, A_num
mov MENOR_num, ax
mov si, offset MENOR
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_15
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_15
dest_conv_non_zero_15:
xor cx, cx
dest_div_loop_15:
cmp ax, 0
je dest_pop_loop_15
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_15
dest_pop_loop_15:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_15
mov byte ptr [si], '$'
dest_conv_end_15:

mov ax, 1
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_16
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_16
dest_conv_non_zero_16:
xor cx, cx
dest_div_loop_16:
cmp ax, 0
je dest_pop_loop_16
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_16
dest_pop_loop_16:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_16
mov byte ptr [si], '$'
dest_conv_end_16:

jmp EtiFin9
EtiSi9:

EtiFin9:

jmp EtiFin8
EtiSi8:

EtiFin8:

; CONDICION SI A > B
mov ax, A_num
mov bx, B_num
cmp ax, bx
jle EtiSi10
; CONDICION SI B > C
mov ax, B_num
mov bx, C_num
cmp ax, bx
jle EtiSi11
mov ax, A_num
mov MAYOR_num, ax
mov si, offset MAYOR
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_17
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_17
dest_conv_non_zero_17:
xor cx, cx
dest_div_loop_17:
cmp ax, 0
je dest_pop_loop_17
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_17
dest_pop_loop_17:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_17
mov byte ptr [si], '$'
dest_conv_end_17:

mov ax, B_num
mov MEDIO_num, ax
mov si, offset MEDIO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_18
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_18
dest_conv_non_zero_18:
xor cx, cx
dest_div_loop_18:
cmp ax, 0
je dest_pop_loop_18
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_18
dest_pop_loop_18:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_18
mov byte ptr [si], '$'
dest_conv_end_18:

mov ax, C_num
mov MENOR_num, ax
mov si, offset MENOR
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_19
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_19
dest_conv_non_zero_19:
xor cx, cx
dest_div_loop_19:
cmp ax, 0
je dest_pop_loop_19
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_19
dest_pop_loop_19:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_19
mov byte ptr [si], '$'
dest_conv_end_19:

mov ax, 1
mov VALIDO_num, ax
mov si, offset VALIDO
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_20
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_20
dest_conv_non_zero_20:
xor cx, cx
dest_div_loop_20:
cmp ax, 0
je dest_pop_loop_20
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_20
dest_pop_loop_20:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_20
mov byte ptr [si], '$'
dest_conv_end_20:

jmp EtiFin11
EtiSi11:

EtiFin11:

jmp EtiFin10
EtiSi10:

EtiFin10:

jmp EtiFin7
EtiSi7:

EtiFin7:

; CONDICION SI VALIDO == 0
mov ax, VALIDO_num
mov bx, 0
cmp ax, bx
jne EtiSi12
IMPRIME Msg21
IMPRIME Salto

jmp EtiFin12
EtiSi12:

EtiFin12:

jmp EtiMientras1
EtiFinMientras1:

mov ax, 0
mov OPCION_num, ax
mov si, offset OPCION
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_22
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_22
dest_conv_non_zero_22:
xor cx, cx
dest_div_loop_22:
cmp ax, 0
je dest_pop_loop_22
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_22
dest_pop_loop_22:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_22
mov byte ptr [si], '$'
dest_conv_end_22:

EtiMientras2:
; CICLO MIENTRAS OPCION <> 7
mov ax, OPCION_num
mov bx, 7
cmp ax, bx
je EtiFinMientras2
IMPRIME Msg23
IMPRIME Salto

IMPRIME Msg24
IMPRIME Salto

IMPRIME Msg25
IMPRIME Salto

IMPRIME Msg26
IMPRIME Salto

IMPRIME Msg27
IMPRIME Salto

IMPRIME Msg28
IMPRIME Salto

IMPRIME Msg29
IMPRIME Salto

IMPRIME Msg30
IMPRIME Salto

IMPRIME Msg31
IMPRIME Salto

IMPRIME Salto
LEER OPCION
xor ax, ax
xor cx, cx
mov cl, [OPCION + 1]
cmp cl, 0
je EtiParseEnd_OPCION
mov si, offset OPCION + 2
EtiParseLoop_OPCION:
mov dx, 10
mul dx
xor bx, bx
mov bl, [si]
sub bl, 30h
add ax, bx
inc si
loop EtiParseLoop_OPCION
EtiParseEnd_OPCION:
mov OPCION_num, ax
xor bx, bx
mov bl, [OPCION + 1]
mov byte ptr [OPCION + 2 + bx], '$'
mov Al, [OPCION + 2]
mov OPCION, Al
IMPRIME Salto

; CONDICION SI OPCION == 1
mov ax, OPCION_num
mov bx, 1
cmp ax, bx
jne EtiSi13
; OPERACION +
xor ax, ax
mov ax, MAYOR_num
mov bx, MEDIO_num
add ax, bx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_32
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_32
dest_conv_non_zero_32:
xor cx, cx
dest_div_loop_32:
cmp ax, 0
je dest_pop_loop_32
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_32
dest_pop_loop_32:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_32
mov byte ptr [si], '$'
dest_conv_end_32:
IMPRIME Msg33
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin13
EtiSi13:

EtiFin13:

; CONDICION SI OPCION == 2
mov ax, OPCION_num
mov bx, 2
cmp ax, bx
jne EtiSi14
; OPERACION -
xor ax, ax
mov ax, MAYOR_num
mov bx, MENOR_num
sub ax, bx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_34
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_34
dest_conv_non_zero_34:
xor cx, cx
dest_div_loop_34:
cmp ax, 0
je dest_pop_loop_34
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_34
dest_pop_loop_34:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_34
mov byte ptr [si], '$'
dest_conv_end_34:
IMPRIME Msg35
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin14
EtiSi14:

EtiFin14:

; CONDICION SI OPCION == 3
mov ax, OPCION_num
mov bx, 3
cmp ax, bx
jne EtiSi15
; OPERACION *
xor ax, ax
mov ax, MAYOR_num
mov bx, MEDIO_num
mul bx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_36
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_36
dest_conv_non_zero_36:
xor cx, cx
dest_div_loop_36:
cmp ax, 0
je dest_pop_loop_36
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_36
dest_pop_loop_36:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_36
mov byte ptr [si], '$'
dest_conv_end_36:
IMPRIME Msg37
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin15
EtiSi15:

EtiFin15:

; CONDICION SI OPCION == 4
mov ax, OPCION_num
mov bx, 4
cmp ax, bx
jne EtiSi16
; OPERACION /
xor ax, ax
mov ax, MAYOR_num
mov bx, MENOR_num
xor dx, dx
div bx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_38
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_38
dest_conv_non_zero_38:
xor cx, cx
dest_div_loop_38:
cmp ax, 0
je dest_pop_loop_38
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_38
dest_pop_loop_38:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_38
mov byte ptr [si], '$'
dest_conv_end_38:
IMPRIME Msg39
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin16
EtiSi16:

EtiFin16:

; CONDICION SI OPCION == 5
mov ax, OPCION_num
mov bx, 5
cmp ax, bx
jne EtiSi17
; OPERACION %
xor ax, ax
mov ax, MAYOR_num
mov bx, MEDIO_num
xor dx, dx
div bx
mov ax, dx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_40
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_40
dest_conv_non_zero_40:
xor cx, cx
dest_div_loop_40:
cmp ax, 0
je dest_pop_loop_40
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_40
dest_pop_loop_40:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_40
mov byte ptr [si], '$'
dest_conv_end_40:
IMPRIME Msg41
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin17
EtiSi17:

EtiFin17:

; CONDICION SI OPCION == 6
mov ax, OPCION_num
mov bx, 6
cmp ax, bx
jne EtiSi18
mov ax, 1
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_42
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_42
dest_conv_non_zero_42:
xor cx, cx
dest_div_loop_42:
cmp ax, 0
je dest_pop_loop_42
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_42
dest_pop_loop_42:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_42
mov byte ptr [si], '$'
dest_conv_end_42:

mov ax, 1
mov I_num, ax
mov si, offset I
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_43
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_43
dest_conv_non_zero_43:
xor cx, cx
dest_div_loop_43:
cmp ax, 0
je dest_pop_loop_43
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_43
dest_pop_loop_43:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_43
mov byte ptr [si], '$'
dest_conv_end_43:

EtiMientras3:
; CICLO MIENTRAS I <= MEDIO
mov ax, I_num
mov bx, MEDIO_num
cmp ax, bx
ja EtiFinMientras3
; OPERACION *
xor ax, ax
mov ax, RES_num
mov bx, MENOR_num
mul bx
mov RES_num, ax
mov si, offset RES
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_44
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_44
dest_conv_non_zero_44:
xor cx, cx
dest_div_loop_44:
cmp ax, 0
je dest_pop_loop_44
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_44
dest_pop_loop_44:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_44
mov byte ptr [si], '$'
dest_conv_end_44:
; OPERACION +
xor ax, ax
mov ax, I_num
mov bx, 1
add ax, bx
mov I_num, ax
mov si, offset I
mov byte ptr [si], '$'
cmp ax, 0
jne dest_conv_non_zero_45
mov byte ptr [si], '0'
mov byte ptr [si + 1], '$'
jmp dest_conv_end_45
dest_conv_non_zero_45:
xor cx, cx
dest_div_loop_45:
cmp ax, 0
je dest_pop_loop_45
xor dx, dx
mov bx, 10
div bx
add dl, 30h
push dx
inc cx
jmp dest_div_loop_45
dest_pop_loop_45:
pop dx
mov [si], dl
inc si
loop dest_pop_loop_45
mov byte ptr [si], '$'
dest_conv_end_45:
jmp EtiMientras3
EtiFinMientras3:

IMPRIME Msg46
IMPRIME Salto

IMPRIME RES
IMPRIME Salto

jmp EtiFin18
EtiSi18:

EtiFin18:

jmp EtiMientras2
EtiFinMientras2:

mov Ax,4C00h
int 21h

.DATA
Salto db 10,13,'$'
A db 6,?,6 dup(24h)
A_num dw 0
B db 6,?,6 dup(24h)
B_num dw 0
C db 6,?,6 dup(24h)
C_num dw 0
MAYOR db 6,?,6 dup(24h)
MAYOR_num dw 0
MEDIO db 6,?,6 dup(24h)
MEDIO_num dw 0
MENOR db 6,?,6 dup(24h)
MENOR_num dw 0
OPCION db 6,?,6 dup(24h)
OPCION_num dw 0
VALIDO db 6,?,6 dup(24h)
VALIDO_num dw 0
RES db 6,?,6 dup(24h)
RES_num dw 0
I db 6,?,6 dup(24h)
I_num dw 0
Msg2 db 'INGRESE A:','$'
Msg3 db 'INGRESE B:','$'
Msg4 db 'INGRESE C:','$'
Msg21 db 'ERROR: DEBEN SER DISTINTOS, ORDENADOS Y DE 1 A 255','$'
Msg23 db 'MENU:','$'
Msg24 db '1. SUMA','$'
Msg25 db '2. RESTA','$'
Msg26 db '3. MULTIPLICACION','$'
Msg27 db '4. DIVISION','$'
Msg28 db '5. MODULO O RESIDUO','$'
Msg29 db '6. EXPONENCIACION','$'
Msg30 db '7. SALIR','$'
Msg31 db 'ELIGE UNA OPCION:','$'
Msg33 db 'SUMA (MAYOR + MEDIO):','$'
Msg35 db 'RESTA (MAYOR - MENOR):','$'
Msg37 db 'MULTIPLICACION (MAYOR * MEDIO):','$'
Msg39 db 'DIVISION (MAYOR / MENOR):','$'
Msg41 db 'MODULO (MAYOR % MEDIO):','$'
Msg46 db 'EXPONENCIACION (MENOR ^ MEDIO):','$'
.STACK
END Inicio
