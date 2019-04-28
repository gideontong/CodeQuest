# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    lines = int(sys.stdin.readline().rstrip())
    x = []
    for line in range(lines):
        r = sys.stdin.readline().rstrip()
        if(not r == "0.0"):
            x.append(float(r))
        else:
            x.append(0)
    min = x[0]
    max = x[0]
    for i in range(len(x)):
        if(min > x[i]):
            min = x[i]
        if(max < x[i]):
            max = x[i]
    for i in x:
        print(round(((i - min)/(max - min) * 255)))