def used_in_row(arr,row,num): 
	for i in range(9): 
		if(arr[row][i] == num): 
			return True
	return False

def used_in_col(arr,col,num): 
	for i in range(9): 
		if(arr[i][col] == num): 
			return True
	return False

def used_in_box(arr,row,col,num): 
	for i in range(3): 
		for j in range(3): 
			if(arr[i+row][j+col] == num): 
				return True
	return False

def check_location_is_safe(arr,row,col,num): 
	return not used_in_row(arr,row,num) and not used_in_col(arr,col,num) and not used_in_box(arr,row - row%3,col - col%3,num) 

def find_location(arr,l): 
	for row in range(9): 
		for col in range(9): 
			if(arr[row][col]==0): 
				l[0]=row 
				l[1]=col 
				return True
	return False

def solver(arr): 
	l=[0,0] 	 
	if(not find_location(arr,l)): 
		return True
	row=l[0] 
	col=l[1] 
	for num in range(1,10): 
		if(check_location_is_safe(arr,row,col,num)): 
			arr[row][col]=num 
			if(solver(arr)): 
				return True
			arr[row][col] = 0		 
	return False

# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    lines = [["0" for x in range(9)]for y in range(9)] 
    puzzle = [[0 for x in range(9)]for y in range(9)] 
    for i in range(9):
        nextline = sys.stdin.readline().rstrip()
        for j in range(9):
            lines[i][j] = nextline[j:j+1]
    for i in range(9):
        for j in range(9):
            if lines[i][j] == "_":
                puzzle[i][j] = 0
            else:
                puzzle[i][j] = int(lines[i][j])
    solver(puzzle)
    for i in range(9):
        for j in range(9):
            print(puzzle[i][j], end='')
        print("")
