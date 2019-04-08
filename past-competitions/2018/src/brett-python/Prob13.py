# Open the input file
with open("Prob13.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the number of people
        numPeople= int(inputFile.readline().replace("\n", ""))
        # Read the massive data line
        fullData = inputFile.readline().replace("\n", "")
        # Split on array breaks
        splitData = fullData.split("],[")
        # Then split those arrays on commas
        # names will still have a [[ at the front as the first array; strip it with [1:]
        names = splitData[0][2:].split(",")
        ages = splitData[1].split(",")
        instagram = splitData[2].split(",")
        twitter = splitData[3].split(",")
        phone = splitData[4].split(",")
        # email will still have a ]] at the end as the last array; strip it with [:-1]
        email = splitData[5][:-2].split(",")
        # Now read each name from the list
        for i in range(numPeople):
            name = inputFile.readline().replace("\n", "")
            # Find that person's index
            index = names.index(name)
            # And spill their beans
            print("Name: " + name)
            print("Age: " + ages[index])
            print("Instagram: " + instagram[index])
            print("Twitter: " + twitter[index])
            print("Phone: " + phone[index])
            print("Email: " + email[index])

