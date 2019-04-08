# Open the input file
with open("Prob14.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # read the line count/length data
        upperLines = int(inputFile.readline().replace("\n", ""))
        upperLineLengths = []
        upperLineLengthStrs = inputFile.readline().replace("\n", "").split(" ")
        for i in range(upperLines):
            upperLineLengths.append(int(upperLineLengthStrs[i]))
        lowerLines = int(inputFile.readline().replace("\n", ""))
        lowerLineLengths = []
        lowerLineLengthStrs = inputFile.readline().replace("\n", "").split(" ")
        for i in range(lowerLines):
            lowerLineLengths.append(int(lowerLineLengthStrs[i]))
        # set up text buffers and counters
        upperText = ""
        lowerText = ""
        upperLine = 0
        lowerLine = 0;
        upperLength = 0;
        lowerLength = 0;
        # continue reading lines until both messages are complete
        while (upperLine < upperLines) or (lowerLine < lowerLines):
            nextLine = inputFile.readline().replace("\n", "")
            # parse each character in the line
            for i in range(len(nextLine)):
                char = nextLine[i]
                # - and = are upper and lower spaces, respectively
                if char == "-":
                    upperText = upperText + " "
                    upperLength = upperLength + 1
                elif char == "=":
                    lowerText = lowerText + " "
                    lowerLength = lowerLength + 1
                # other letters get added to the appropriate string
                elif char.isupper():
                    upperText = upperText + char
                    upperLength = upperLength + 1
                else:
                    lowerText = lowerText + char
                    lowerLength = lowerLength + 1
                # when we reach the end of a line, add a break
                # and increment the line counter
                if upperLength == upperLineLengths[upperLine]:
                    upperText = upperText + "\n"
                    upperLength = 0
                    upperLine = upperLine + 1
                elif lowerLength == lowerLineLengths[lowerLine]:
                    lowerText = lowerText + "\n"
                    lowerLength = 0
                    lowerLine = lowerLine + 1
        # print both messages when done
        # suppress newline on lowerText to avoid extra blank line
        print(upperText)
        print(lowerText, end="")
