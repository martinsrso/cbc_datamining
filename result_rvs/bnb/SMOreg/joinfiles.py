import os

filenames = os.listdir()

with open("baseCompleta.csv", 'a') as outfile:
    for fname in filenames:
        with open(fname) as infile:
            for line in infile:
                if line.find(";predict") < 0:
                    outfile.write(line)
