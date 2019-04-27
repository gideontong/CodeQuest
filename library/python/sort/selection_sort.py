"""
Source: Interactive Python

The selection sort improves on the bubble sort by making only one exchange for every pass through
the list. In order to do this, a selection sort looks for the largest value as it makes a pass
and, after completing the pass, places it in the proper location. As with a bubble sort, after
the first pass, the largest item is in the correct place. After the second pass, the next largest
is in place.
"""

def selectionSort(alist):
   for fillslot in range(len(alist)-1,0,-1):
       positionOfMax=0
       for location in range(1,fillslot+1):
           if alist[location]>alist[positionOfMax]:
               positionOfMax = location

       temp = alist[fillslot]
       alist[fillslot] = alist[positionOfMax]
       alist[positionOfMax] = temp