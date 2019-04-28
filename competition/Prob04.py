# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    x = sys.stdin.readline().rstrip().split(" ")
    if(x[1] == "true"):
        if(int(x[0]) > 85):
            print("big ticket")
        elif(int(x[0]) > 65):
            print("small ticket")
        else:
            print("no ticket")
    else:
        if(int(x[0]) > 80):
            print("big ticket")
        elif(int(x[0]) > 60):
            print("small ticket")
        else:
            print("no ticket")