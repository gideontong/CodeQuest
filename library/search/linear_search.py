"""
Source: Stack Abuse

The algorithm consists of iterating over an array and returning the index of the first occurrence
of an item once it is found
"""

def LinearSearch(lys, element):  
    for i in range (len(lys)):
        if lys[i] == element:
            return i
    return -1