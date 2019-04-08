# Open the input file
with open("Prob02.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line and start a counter
        line = inputFile.readline().replace("\n", "")
        count = 0
        for j in range(len(line)):
            # Check each letter in the sentence
            if line[j] == 'a' or line[j] == 'e' or line[j] == 'i' or line[j] == 'o' or line[j] == 'u':
                count = count + 1
        print(count)
