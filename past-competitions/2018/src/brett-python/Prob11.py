import math
# Open the input file
with open("Prob11.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line
        line = inputFile.readline().replace("\n", "")
        # split up the data
        # 0-2 are chroma key, 3 is tolerance,
        # 4-6 are foreground, 7-9 are background
        data = line.split(" ")
        # calculate distance (** 2 squares values)
        redDist = (int(data[0]) - int(data[4])) ** 2
        greenDist = (int(data[1]) - int(data[5])) ** 2
        blueDist = (int(data[2]) - int(data[6])) ** 2
        distance = math.sqrt(redDist + greenDist + blueDist)
        if distance <= int(data[3]):
            print(data[7] + " " + data[8] + " " + data[9])
        else:
            print(data[4] + " " + data[5] + " " + data[6])
