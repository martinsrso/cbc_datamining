import pyexcel_ods as po
from collections import OrderedDict
import sys
import glob

if len(sys.argv) <= 1:
    print("enter ods file name")
    sys.exit(0)

filenames = glob.glob("csvs/*.csv") 

data = OrderedDict()

for file in filenames:
	data.update(po.get_data(file))

po.save_data(sys.argv[1], data)