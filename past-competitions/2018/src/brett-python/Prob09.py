# Open the input file
with open("Prob09.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line
        line = inputFile.readline().replace("\n", "")
        # split into digits
        numbers = line.split(",")
        a = int(numbers[0])
        b = int(numbers[1])
        c = 100
        swap = 0
        # continue process until c is 0
        while c != 0:
            # swap a and b if needed
            if a < b:
                swap = a
                a = b
                b = swap
            # subtract and print equation
            c = a - b
            print(str(a) + "-" + str(b) + "=" + str(c))
            # shift values
            a = b
            b = c
        # print result
        if a == 1:
            print("COPRIME")
        else:
            print("NOT COPRIME")
