if [ $# -ne 0 ]
then
for arg
do
# Lancement des différents tests passés en arguments
echo "****** lancement de $arg ******"
./min-ml $arg
done
fi
if [ $# -eq 0 ]
then
echo "******* TEST DE TOUS LES FICHIERS  ******* \n"

echo "********TEST DES FICHIERS CORRECTS ******* \n"
for i in tests/TestsOk/*.ml
do
./min-ml $i
done

echo "********TEST DES FICHIERS INCORRECTS ******* \n"


for j in tests/TestsErreurs/*.ml
do
./min-ml $j
done
fi

echo " il ya nb argument :" $#