import math
# Open the input file
with open("Prob16.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # Set "constants"
    radians = math.pi * 2
    startAngle = math.pi / 2
    # For each test case
    for caseNum in range(cases):
        # Read the next line
        data = inputFile.readline().replace("\n", "")
        # Split the line
        pieces = data.split(" ")
        centerX = int(pieces[0])
        centerY = int(pieces[1])
        points = int(pieces[2])
        outerRadius = int(pieces[3])
        innerRadius = int(pieces[4])
        # Set starting angle and calculate distance between vertices
        currentAngle = startAngle
        angleDelta = radians / (points * 2)
        # Calculate vertices
        for i in range(points * 2):
            # Determine which radius to use; even i == points/outer
            radius = outerRadius
            if i % 2 == 1:
                radius = innerRadius
            # Calculate X and Y using given formulae
            vertexX = (radius * math.cos(currentAngle)) + centerX
            vertexY = (radius * math.sin(currentAngle)) + centerY
            # Correct for -0 errors; -0.001 will round to -0.00,
            # which is not a valid number
            if vertexX < 0 and vertexX > -0.005:
                vertexX = 0
            if vertexY < 0 and vertexY > -0.005:
                vertexY = 0
            # Print space as needed
            if i > 0:
                print(" ", end="")
            # Print coordinates (specify end to suppress newline,
            # and use format to force two decimal places)
            print("{:.2f},{:.2f}".format(vertexX, vertexY), end="")
            # Move to next angle (don't worry about exceeding 2PI)
            currentAngle = currentAngle + angleDelta
        # print newline when done
        print("")
        
