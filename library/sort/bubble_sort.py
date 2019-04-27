"""
Source: Interactive Python

The bubble sort makes multiple passes through a list. It compares adjacent items and exchanges
those that are out of order. Each pass through the list places the next largest value in its
proper place.
"""

def bubbleSort(alist):
    for passnum in range(len(alist)-1,0,-1):
        for i in range(passnum):
            if alist[i]>alist[i+1]:
                temp = alist[i]
                alist[i] = alist[i+1]
                alist[i+1] = temp