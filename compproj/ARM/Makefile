export PATH := /opt/gnu/arm/bin:$(PATH)

AS=arm-none-eabi-as
LD=arm-none-eabi-gcc
ASM=$(wildcard *.s)
PRG=$(subst .s,.arm,${ASM})

all: ${PRG}

echo:
	echo ${ASM}
	echo ${PRG}

%.o : %.s libmincaml.S 
	${AS} -o $@ $^ 

%.arm : %.o
	${LD} -o $@ $^ -lc --specs=rdimon.specs

test: ${PRG}
	@for p in ${PRG} ; do \
	  echo "*** Launching $$p"; \
	  qemu-arm ./$$p  ; \
	done

