# Open the input file
with open("Prob05.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the number
        original = int(inputFile.readline().replace("\n", ""))
        current = original
        count = 1
		# Process the collatz function until we hit 1
        while current != 1:
            count = count + 1
            if current % 2 == 0:
                current = current / 2
            else:
                current = (current * 3) + 1
		# Print the count
        print(str(original) + ":" + str(count))


        
