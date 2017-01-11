import os

filenames = os.listdir()
print(filenames)
cont = 0

with open("baseCompleta.csv", 'w+') as outfile:
    for fname in filenames:
        with open(fname) as infile:
            if cont == 1:
                continue;
            for line in infile:
                outfile.write(line)
            cont = 1
