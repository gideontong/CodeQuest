# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    x = int(sys.stdin.readline().rstrip())
    print(round(math.pi*(40075/math.pi+2*x), 1))