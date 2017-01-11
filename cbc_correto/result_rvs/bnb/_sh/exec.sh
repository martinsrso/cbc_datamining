#!/bin/bash/
#copia os arquivos .php da pasta atual para todas as subpastas contidas nela

for i in `ls -d */`
	do cd $i
	   j="python3 joinfiles.py"
	   ci="python computeImprovement.py baseCompleta.csv ${i:0:${#i}-1}.csv"
	   eval $j
	   eval $ci
	   echo $i
	   cd ..
  # do joi="python3 ${i}joinfiles.py $i"
     # comp="python ${i}computeImprovement.py ${i}baseCompleta.csv $i${i:0:${#i}-1}.csv"
     # $joi
     # $comp
done
exit 0
