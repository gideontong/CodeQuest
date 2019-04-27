#-----------------------------------------------------------------------
# minesweeper.py
#-----------------------------------------------------------------------

import stdio
import stdarray
import sys
import random

# Accept integers m and n, and float p as command-line arguments.
# Create a m x n minesweeper game where each cell is a bomb with
# probability p. Write the m x n game and the neighboring bomb counts
# to standard output.

m = int(sys.argv[1])
n = int(sys.argv[2])
p = float(sys.argv[3])

# Create bombs as a m+2 * n+2 array.
bombs = stdarray.create2D(m+2, n+2, False)

# bombs is [1..m][1..n]; the border is used to handle boundary cases.
for r in range(1, m+1):
    for c in range(1, n+1):
        bombs[r][c] = (random.random() < p)

# Write the bombs.
for r in range(1, m+1):
    for c in range(1, n+1):
        if bombs[r][c]:
            stdio.write('* ')
        else:
            stdio.write('. ')
    stdio.writeln()

# Create solution as a m+2 x n+2 array.
solution = stdarray.create2D(m+2, n+2, 0)

# solution[i][j] is the number of bombs adjacent to cell (i, j).
for r in range(1, m+1):
    for c in range(1, n+1):
        # (rr, cc) indexes neighboring cells.
        for rr in range(r-1, r+2):
            for cc in range(c-1, c+2):
                if bombs[rr][cc]:
                    solution[r][c] += 1

# Write solution.
stdio.writeln()
for r in range(1, m+1):
    for c in range(1, n+1):
        if bombs[r][c]:
            stdio.write('* ')
        else:
            stdio.write(str(solution[r][c]) + ' ')
    stdio.writeln()

#-----------------------------------------------------------------------

# python minesweeper.py 3 5 .5
# . . * . * 
# * . * . * 
# * * * . * 
# 
# 1 3 * 4 * 
# * 6 * 6 * 
# * * * 4 * 

# python minesweeper.py 3 5 .5
# . . * * . 
# . . . * * 
# . . . . * 
# 
# 0 1 * * 3 
# 0 1 3 * * 
# 0 0 1 3 * 