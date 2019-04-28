# Recommended imports for all problems
# Some problems may require more

def convert(x):
    print(x)
    if(x < 10):
        return "0" + str(x)
    else:
        return str(x)

import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    x = sys.stdin.readline().rstrip()
    min = 0
    hours = 0
    sec = 0
    for i in (0, len(x)):
        if(x[i:i+1].isdigit()):
            if(x[i+1:i+2] == "m"):
                min = int(x[i:i+1])
                print(min)
            if(x[i+1:i+2] == "s"):
                sec = int(x[i:i+1])
            if(x[i+1:i+2] == "h"):
                hours = int(x[i:i+1])
    print(convert(hours) + ":" + convert(min) + ":" + convert(sec))