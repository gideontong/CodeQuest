# Open the input file
with open("Prob08.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line
        line = inputFile.readline().replace("\n", "")
        # split into values
        originals = line.split(" ")
        first = True
        # convert the angles
        for angle in originals:
            # add 180*
            newAngle = float(angle) + 180.0
            # bring within the 0-359.99 range
            if newAngle >= 360.00:
                newAngle = newAngle - 360.00
            # print the angle
            if not first:
                print(" ", end="")
            first = False
            # use format to force five digits (two after the .)
            # specifying end suppresses newlines
            print("{:06.2f}".format(newAngle), end="")
        # now print the newline
        print("")
