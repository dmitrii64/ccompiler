
Intermediate Code Specification
===============================

Commands
--------

### VOID ###

```
VOID identifier _ _
```

Create identifier of VOID type and allocate memory

### INT ###

```
INT identifier _ _
```

Create identifier of INT type and allocate memory

### CHAR ###

```
CHAR identifier _ _
```

Create identifier of CHAR type and allocate memory

### FLOAT ###

```
FLOAT identifier _ _
```

Create identifier of FLOAT type and allocate memory

### BOOL ###

```
BOOL identifier _ _
```

Create identifier of BOOL type and allocate memory

### COMPLEX ###

```
COMPLEX identifier _ _
```

Create identifier of COMPLEX type and allocate memory

### SHORT ###

```
SHORT identifier _ _
```

Create identifier of SHORT type and allocate memory

### DOUBLE ###

```
DOUBLE identifier _ _
```

Create identifier of DOUBLE type and allocate memory

### LONG ###

```
LONG identifier _ _
```

Create identifier of LONG type and allocate memory

### MOV ###

```
MOV value identifier _
```

Assign value to identifier

### ADD ###

```
ADD identifier1 identifier2 identifier3
ADD identifier1 constant identifier3
ADD constant constant identifier3
```

Summate identifier1 (or constant) and identifier2 (or constant) and assign result to identifier3

### DIV ###

```
DIV identifier1 identifier2 identifier3
DIV identifier1 constant identifier3
DIV constant constant identifier3
```

Divide identifier1 (or constant) by identifier2 (or constant) and assign result to identifier3

### MUL ###

```
MUL identifier1 identifier2 identifier3
MUL identifier1 constant identifier3
MUL constant constant identifier3
```

Multiply identifier1 (or constant) and identifier2 (or constant) and assign result to identifier3

### INC ###

```
INC identifier _ _
```

Increment identifier value

### DEC ###

```
DEC identifier _ _
```

Decrement identifier value

### CALL ###

```
CALL func_identifier _ _
```

Call function

### RET ###

...?

### DEFL ###

```
DEFL lab _ _
```

Define label lab

### BRL ###

```
BRL lab _ _
```

Jump to label lab

### BF ###

```
BF lab expr _
```

Jump to label lab if expression expr is FALSE

### BZL ###

```
BZL lab expr _
```

Jump to label lab if expression expr is 0

### BPL ###

```
BPL lab expr _
```

Jump to label lab if expression expr more than 0

### BML ###

```
BML lab expr _
```

Jump to label lab if expression expr less than 0

References
----------

- Opaleva, Page 376
- [Compiler - Intermediate Code Generation](http://www.tutorialspoint.com/compiler_design/compiler_design_intermediate_code_generations.htm)
