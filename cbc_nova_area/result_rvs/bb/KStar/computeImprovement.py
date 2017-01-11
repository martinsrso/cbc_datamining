import csv
import sys
from collections import defaultdict

if len(sys.argv) <= 1:
    print("enter csv file name")
    sys.exit(0)

areas = defaultdict(lambda: defaultdict(float))
pred = defaultdict(lambda: defaultdict(float))
instances = set()
parameters = set()

def verifyZero(value):
    if value == 0.0:
        return 1
    return value

def absoluteValue(value, area):
    if value > area:
        area = verifyZero(area)
        return -1 * value/area
    else: 
        value = verifyZero(value)
        return area/value

def bestParamInst(inst):
    bestArea = sys.float_info.max
    bestPSet = ''
    for pset, area in pred[inst].iteritems():
        # print('inst: {}, pset: {}, area: {}'.format(inst, pset, area))
        if area < bestArea:
            bestArea = area
            bestPSet = pset

    # raw_input()
    return bestPSet


def defaultArea(inst):
    if 'default' not in areas[inst]:
        print('default result not found for {}'.format(inst))
        exit(0)
    return areas['default']


with open(sys.argv[1], 'rb') as csvfile:
    rcsv = csv.reader(csvfile, delimiter=";")
    for row in rcsv:
        instname = row[0]
        paramset = row[1]
        area = float(row[2])
        predarea = float(row[4])

        areas[instname][paramset] = area
        pred[instname][paramset] = predarea
        instances.add(instname)
        parameters.add(paramset)

# checkng if instances have default value
toremove = set()
for inst in instances:
    if 'default' not in areas[inst]:
        print('instance {} does not have execution corresponding to default  value. removing it.'.format(inst))
        toremove.add(inst)

for inst in toremove:
    instances.remove(inst)

instances.remove('mspp16')

sumimpr = 0.0
sumimpr_real = 0.0
maximpr = 0.0
maximpr_real = 0.0
maximprinst = ''

outstr = "instance, recommended_parameter, param_value_pred, param_value_real, default_value, improvement_pred, improvement_real\n"
for inst in instances:
    bestpset = bestParamInst(inst)
    impr = absoluteValue(pred[inst][bestpset], areas[inst]['default'])
    impr_real = absoluteValue(areas[inst][bestpset], areas[inst]['default'])
    # if absValue == 0.00:
    #     impr = 0
    # else:
    #     impr = areas[inst]['default']/absValue
    outstr += '{},{},{},{},{},{},{}'.format(inst,
        bestpset, pred[inst][bestpset], areas[inst][bestpset], areas[inst]['default'], impr, impr_real)
    outstr += "\n"
    # print('instance {} recommended parameter {} {} default {} improvement {}'.format(inst,
        # bestpset, areas[inst][bestpset], areas[inst]['default'], impr))
    sumimpr += impr
    sumimpr_real += impr_real
    if impr > maximpr:
        maximpr = impr
        maximprinst = inst

    if impr_real > maximpr_real:
        maximpr_real = impr_real
        maximprinst = inst

avimpr = sumimpr/len(instances)
avimpr_real = sumimpr_real/len(instances)
outstr+='average improvement_pred: {} maximum improvement: {} ({})'.format(avimpr,
    maximpr, maximprinst)

outstr+='average improvement_real: {} maximum improvement_real: {} ({}) \n'.format(avimpr_real,
    maximpr_real, maximprinst)

outstr+="\n"
# print('average improvement: {} maximum improvement: {} ({})'.format(avimpr,
    # maximpr, maximprinst))

with open(sys.argv[2], "wb") as csvfile:
    csvfile.write(outstr)


print(outstr)
