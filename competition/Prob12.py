# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    array = sys.stdin.readline().rstrip().split(" ")
    a = float(array[0])
    b = int(array[1])
    c = float(array[2])
    c += 1
    d = a;
    x = 1.0
    conj = 1.0
    for i in range(0, b):
        x = conj/a
        conj -= x
        a = a - c
    conj /= (d-c*b)
    print(format(conj, '.2%'))

        