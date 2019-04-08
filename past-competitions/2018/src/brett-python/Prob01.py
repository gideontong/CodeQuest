# Open the input file
with open("Prob01.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the score
        score = int(inputFile.readline().replace("\n", ""))
        # Check the score
        if score >= 70:
            print("PASS")
        else:
            print("FAIL")

    
