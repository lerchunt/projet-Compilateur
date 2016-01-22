export PATH := /opt/gnu/arm/bin:$(PATH)
AS=arm-eabi-as
LD=arm-eabi-ld
ASM=$(wildcard *.s)
PRG=$(subst .s,.arm,${ASM})

all: ${PRG}

echo:
	echo ${ASM}
	echo ${PRG}

%.o : %.s libmincaml.S
	${AS} -o $@ $^

%.arm : %.o
	${LD} -o $@ $^ -lm -L/usr/lib/gcc/arm-none-eabi/4.8.2/fpu -lgcc -lc
test: ${PRG}
	@for p in ${PRG} ; do \
	  echo "*** Launching $$p"; \
	  qemu-arm ./$$p  ; \
	done

