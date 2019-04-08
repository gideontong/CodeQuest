# Open the input file
with open("Prob06.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line
        line = inputFile.readline().replace("\n", "")
        # Break up words
        statuses = line.split(" ")
        value = 8
        errorCode = 0
        # Check each status
        for status in statuses:
            if status == "BROKEN":
                # If broken, add the value to the error code
                errorCode = errorCode + value
            # Reduce the value by half for each component
            value = value / 2
        left = "off"
        right = "off"
        # integer division determines left light
        leftValue = errorCode // 4
        # modulo division determines right light
        rightValue = errorCode % 4
        if leftValue == 1:
            left = "red"
        elif leftValue == 2:
            left = "green"
        elif leftValue == 3:
            left = "blue"
        if rightValue == 1:
            right = "red"
        elif rightValue == 2:
            right = "green"
        elif rightValue == 3:
            right = "blue"
        # print light colors
        print(left + " " + right)
