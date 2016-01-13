export PATH := /opt/gnu/arm/bin:$(PATH)

AS=arm-none-eabi-as
LD=arm-none-eabi-ld

if [ $# -ne 0 ]
then
for arg
do
# Lancement des différents tests passés en arguments
echo "\033[36m****** lancement de $arg ****** \033[0m"
./min-ml $arg > journal.log
fichier=${arg%%.*}
cp ${arg%%.*}.s ${arg%%.*}.arm
echo "lancement de .arm"
qemu-arm ./${arg%%.*}.arm
done
fi
if [ $# -eq 0 ]
then
echo "\033[36m******* TEST DE TOUS LES FICHIERS  ******* \033[0m\n"

echo "\033[36m******* TEST DES FICHIERS CORRECTS ******* \033[0m\n"
for i in tests/TestsOk/*.ml
do
echo "\033[0mTest de $i \033[31m"
./min-ml $i > journal.log
fichier=${i%%.*}
cp ${i%%.*}.s ${j%%.*}.arm
echo "lancement de .arm"
qemu-arm ./${i%%.*}.arm


done
echo ""
echo "\033[36m******* TEST DES FICHIERS INCORRECTS ******* \n"



for j in tests/TestsErreurs/*.ml
do
echo "\033[0mTest de $j \033[31m"

./min-ml $j >> journal.log
fichier=${j%%.*}
#cp ${j%%.*}.s ${j%%.*}.arm
#echo "lancement de .arm"
#qemu-arm ./${j%%.*}.arm
#pas besoin car incorrect
done
fi
echo "\033[0mFin des Tests "
rm journal.log

#echo " il ya nb argument :" $#
