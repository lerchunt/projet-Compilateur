#include "math.h"
#include "fdlibm.h"
	.text
	.global _start
_start:
	bl	min_caml_hello_world
	bl	min_caml_exit

