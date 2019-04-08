# Open the input file
with open("Prob03.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the number
        line = inputFile.readline().replace("\n", "")
        # Strip off the suffix and convert to number
        number = int(line.replace("th", ""))
        # Teens and numbers ending in 0 or 4-9 get th
        if ((number % 100) >= 10 and (number % 100) <= 20) or number % 10 == 0 or number % 10 >= 4:
            print(str(number) + "th")
        # Others get st, nd, or rd
        elif number % 10 == 1:
            print(str(number) + "st")
        elif number % 10 == 2:
            print(str(number) + "nd")
        elif number % 10 == 3:
            print(str(number) + "rd")
        
