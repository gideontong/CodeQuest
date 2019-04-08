# Open the input file
with open("Prob04.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the line
        line = inputFile.readline().replace("\n", "")
        # Split the string on spaces
        responses = line.split(" ")
        # Start counters
        rock = 0
        paper = 0
        scissors = 0
		# Count responses
        for response in responses:
            if response == 'R':
                rock = rock + 1
            elif response == 'P':
                paper = paper + 1
            else:
                scissors = scissors + 1
		# Determine the winner - must be alone, 
		# and whatever beats it must be 0
        if rock == 1 and paper == 0:
            print("ROCK")
        elif paper == 1 and scissors == 0:
            print("PAPER")
        elif scissors == 1 and rock == 0:
            print("SCISSORS")
        else:
            print("NO WINNER")

        
