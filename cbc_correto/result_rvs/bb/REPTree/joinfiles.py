import os
import sys


filenames = os.listdir()
filenames2 = []

# for file in filenames:
	# filenames2.append(sys.argv[1]+file)

# print(sys.argv[1]+"baseCompleta.csv")
# print(filenames2)

with open("baseCompleta.csv", 'a') as outfile:
    for fname in filenames:
    	if not fname == "joinfiles.py" and not fname == "computeImprovement.py":
    		with open(fname) as infile:
    		    for line in infile:
    		        if line.find(";predict") < 0:
    		            outfile.write(line)
