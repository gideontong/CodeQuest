"""
Source: Stack Abuse

Binary search follows a divide and conquer methodology. It is faster than linear search but
requires that the array be sorted before the algorithm is executed
"""

def BinarySearch(lys, val):  
    first = 0
    last = len(lys)-1
    index = -1
    while (first <= last) and (index == -1):
        mid = (first+last)//2
        if lys[mid] == val:
            index = mid
        else:
            if val<lys[mid]:
                last = mid -1
            else:
                first = mid +1
    return index