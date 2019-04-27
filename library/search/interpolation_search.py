"""
Source: Stack Abuse

Interpolation search is another divide and conquer algorithm, similar to binary search. Unlike
binary search, it does not always begin searching at the middle. Interpolation search calculates
the probable position of the element we are searching
"""

def InterpolationSearch(lys, val):  
    low = 0
    high = (len(lys) - 1)
    while low <= high and val >= lys[low] and val <= lys[high]:
        index = low + int(((float(high - low) / ( lys[high] - lys[low])) * ( val - lys[low])))
        if lys[index] == val:
            return index
        if lys[index] < val:
            low = index + 1;
        else:
            high = index - 1;
    return -1