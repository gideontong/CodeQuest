"""
Source: Stack Abuse

Jump Search is similar to binary search in that it works on a sorted array, and uses a similar
divide and conquer approach to search through it. It can be classified as an improvement of
the linear search algorithm since it depends on linear search to perform the actual comparison
when searching for a value.
"""

import math

def JumpSearch (lys, val):  
    length = len(lys)
    jump = int(math.sqrt(length))
    left, right = 0, 0
    while left < length and lys[left] <= val:
        right = min(length - 1, left + jump)
        if lys[left] <= val and lys[right] >= val:
            break
        left += jump;
    if left >= length or lys[left] > val:
        return -1
    right = min(length - 1, right)
    i = left
    while i <= right and lys[i] <= val:
        if lys[i] == val:
            return i
        i += 1
    return -1