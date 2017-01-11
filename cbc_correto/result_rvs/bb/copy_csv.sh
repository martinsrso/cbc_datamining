#!/bin/bash/
for i in `ls -d */`
  do
  	str="${i:0:${#i}-1}.csv"
  	cd $i
  	cp $str ../csvs
  	cd ..
  	echo $str
done