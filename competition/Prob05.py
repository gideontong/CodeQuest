# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    x = sys.stdin.readline().rstrip().split(" ")
    found = "false"
    for i in range(0, int(x[0]) + 1):
        for j in range(0, int(x[1]) + 1):
            if(i + 5 * j == int(x[2])):
                found = "true"
    print(found)