#!/bin/bash/
for i in `ls -d */`
  do rm $i*.csv
done
