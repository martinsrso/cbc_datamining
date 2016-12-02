import glob
import os

def format(value):
    return "%.3f" % value

def floats(dirglob): #retorna a media
    values = [0] * 6
    for file in dirglob:
        i = 0
        for val in list(map(float, open(file).readlines()[1].split(";"))):
            values[i] += val
            i += 1
    return list(map(lambda x: x/len(dirglob), values))

def strings(values, string): #retorna string
    str_full = ""
    str_part = ""
    for val in values:
        str_part +=  ";" + str(val)
    str_full = string + str_part
    return str_full.replace(".",",") + "\n"

for dire in os.listdir():
    if dire != "csv"and dire != "script.py":


        strs = ["basecompleta","pureinteger","purebinary","mixed-integer",
            "matrixminaij>=0andmaxaij<=1Binarizadabinaryproblems",
            "matrixminaij>=0andmaxaij<=1Binarizadaproblems",
            "matrixminaij>=-1000andmaxaij<=1000Binarizadabinaryproblems",
            "matrixminaij>=-1000andmaxaij<=1000Binarizadaproblems"]

        globs = [glob.glob(dire+"/mixed-integer*"),glob.glob(dire+"/baseCompleta*"),
            glob.glob(dire+"/matrixminaij>=0andmaxaij<=1Binarizadabinaryproblems*"),
            glob.glob(dire+"/matrixminaij>=0andmaxaij<=1Binarizadaproblems*"),
            glob.glob(dire+"/matrixminaij>=-1000andmaxaij<=1000Binarizadabinaryproblems*"),
            glob.glob(dire+"/matrixminaij>=-1000andmaxaij<=1000Binarizadaproblems*"),
            glob.glob(dire+"/pureinteger*"),glob.glob(dire+"/purebinary*")]

        valores = []
        for g in globs:
            valores.append(floats(g))


        for x in range(len(globs)):
            with open("/home/rmartins/Downloads/cbc_datamining/models/bb/csv/"+strs[x]+".csv", "a+") as f:
                f.write(strings(valores[x], globs[x][0][:globs[x][0].index("/")]))
