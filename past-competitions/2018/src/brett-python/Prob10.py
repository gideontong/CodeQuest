# Open the input file
with open("Prob10.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the data
        boardsize = inputFile.readline().replace("\n", "")
        start = inputFile.readline().replace("\n", "")
        end = inputFile.readline().replace("\n", "")
        # board size is irrelevant
        # each space can be "even" or "odd"
        # add its coordinates; if the result is even, the space is "even"
        # a bishop starting on an "even" space can only move to "even" spaces
        # split the coordinates
        startCoords = start.split(",")
        endCoords = end.split(",")
        # add the coordinates
        startValue = int(startCoords[0]) + int(startCoords[1])
        endValue = int(endCoords[0]) + int(endCoords[1])
        # compare evenness
        if startValue % 2 == endValue % 2:
            print("Yes")
        else:
            print("No")
        
