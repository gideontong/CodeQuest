"""
Source: Stack Abuse

Exponential search depends on binary search to perform the final comparison of values. The algorithm works by:
- Determining the range where the element we're looking for is likely to be
- Using binary search for the range to find the exact index of the item
"""

def ExponentialSearch(lys, val):  
    if lys[0] == val:
        return 0
    index = 1
    while index < len(lys) and lys[index] <= val:
        index = index * 2
    return BinarySearch( arr[:min(index, len(lys))], val)