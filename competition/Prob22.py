# Recommended imports for all problems
# Some problems may require more

import sys
import math
import string
import datetime
cases = int(sys.stdin.readline().rstrip())

def bubbleSort(alist):
    for passnum in range(len(alist)-1,0,-1):
        for i in range(passnum):
            if alist[i]>alist[i+1]:
                temp = alist[i]
                alist[i] = alist[i+1]
                alist[i+1] = temp

for caseNum in range(cases):
    files = sys.stdin.readline().rstrip().split(" ")
    r = [] # SCORES
    n = [] # NAMES
    s = [] # SIZES
    for file in range(int(files[0])):
        array = sys.stdin.readline().rstrip().split(" ")
        old = 0.0
        months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        date = array[0].split("/")
        day = int(date[0])
        month = int(date[1])
        year = int(date[2])
        n.append(array[4])
        date = datetime.date(year, month, day)
        diff = date - date.today()
        old = abs(diff.days)
        if(array[2] == "PM"):
            old -= 0.5
        r.append(old * float(array[3]) / 1000)
        s.append(float(array[3]))
    k = []
    for i in range(int(files[0])):
        k.append(r[i])
    bubbleSort(k)
    print(k)
    print(r)
    size = 0
    for i in range(int(files[0]) - 1, 0, -1):
        index = r.index(k[i])
        if(size + s[index]/1000 <= float(files[1]) * 1000 * 0.25):
            print(n[index], format(r[index], ".3f"))
            size += s[index]/1000