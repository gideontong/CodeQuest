with open("Prob02.in.txt", "rt") as inputFile:
    cases = int(inputFile.readline().replace("\n", ""))
    for caseNum in range(cases):
        line = inputFile.readline().replace("\n", "")
        count = 0
        for i in range(len(line)):
            if line[i] == 'a' or line[i] == 'e' or line[i] == 'i' or line[i] == 'o' or line[i] == 'u':
                count = count + 1
        print(count)
