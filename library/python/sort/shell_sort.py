"""
Source: Interactive Python

The shell sort, sometimes called the “diminishing increment sort,” improves on the insertion
sort by breaking the original list into a number of smaller sublists, each of which is sorted
using an insertion sort. The unique way that these sublists are chosen is the key to the shell 
ort. Instead of breaking the list into sublists of contiguous items, the shell sort uses an
increment i, sometimes called the gap, to create a sublist by choosing all items that are i
items apart.
"""

def shellSort(alist):
    sublistcount = len(alist)//2
    while sublistcount > 0:

      for startposition in range(sublistcount):
        gapInsertionSort(alist,startposition,sublistcount)

      print("After increments of size",sublistcount,
                                   "The list is",alist)

      sublistcount = sublistcount // 2

def gapInsertionSort(alist,start,gap):
    for i in range(start+gap,len(alist),gap):

        currentvalue = alist[i]
        position = i

        while position>=gap and alist[position-gap]>currentvalue:
            alist[position]=alist[position-gap]
            position = position-gap

        alist[position]=currentvalue