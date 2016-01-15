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
echo "génération de $i.s"
./min-ml $i > journal.log
done

###### CREATION ARM ######### 
cd tests/TestsOk
make -k
cd ../../
#############################

	for i in tests/TestsOk/*.ml
	do
		echo "\033[0mTest de $i \033[31m"
		fichier=${i%%.*}

		#make test Var=${i%%.*}
		#cp ${i%%.*}.s ${i%%.*}.arm
		#echo "lancement de .arm"
		echo -n "(*" > tmp.txt
		retour=`qemu-arm ./${i%%.*}.arm`
		echo -n "$retour">> tmp.txt
		echo "*)" >> tmp.txt
		sed -n 1p $i > tmp2.txt
		test=`diff -q tmp.txt tmp2.txt | grep on`
		if [ -z "$test" ]
			then
			echo "\033[32mOK\033[0m"
			else
			echo "$test"
			cat tmp.txt
			cat tmp2.txt
		fi
	done
	echo ""
	echo "\033[36m******* TEST DES FICHIERS INCORRECTS ******* \n"



	for j in tests/TestsErreurs/*.ml
	do
		echo "\033[0mTest de $j \033[31m"
		
		echo -n "(*" > tmp.txt
		echo -n `./min-ml $j 2>> tmp.txt`
		
		echo "*)" >> tmp.txt
		sed ':a;N;$!ba;s/\n//g' tmp.txt >tmp3.txt
		
		sed -n 1p $j > tmp2.txt
		
		test=`diff -q tmp3.txt tmp2.txt | grep on`
		if [ -z "$test" ]
			then
			echo "\033[32mOK\033[0m"
			else
			echo "$test"
			cat tmp3.txt
			cat tmp2.txt
		fi
		#cp ${j%%.*}.s ${j%%.*}.arm
		#echo "lancement de .arm"
		#qemu-arm ./${j%%.*}.arm
		#pas besoin car incorrect
	done
fi
echo "\033[0mFin des Tests "
rm journal.log
rm tmp.txt
rm tmp2.txt

#echo " il ya nb argument :" $#
