# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    x = sys.stdin.readline().rstrip().split(" ")
    if(x[0] == x[1]):
        print(int(x[0])*4)
    else:
        print(int(x[0]) + int(x[1]))