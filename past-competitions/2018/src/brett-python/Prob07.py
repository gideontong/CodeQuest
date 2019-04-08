# Open the input file
with open("Prob07.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the number of words
        wordCount = int(inputFile.readline().replace("\n", ""))
        nonPalindromes = []
        # For each word
        for j in range(wordCount):
            word = inputFile.readline().replace("\n", "")
            # compare each pair of letters, moving inward
            for k in range(len(word) // 2):
                if word[k].upper() != word[-(k + 1)].upper():
                    # if any are unequal, note the index of the word
                    nonPalindromes.append(j + 1)
                    break
            # end for k
        # end for j
        if len(nonPalindromes) == 0:
            # all were palindromes
            print("True")
        else:
            # at least one wasn't
            # specify end to suppress the automatic newline
            print("False - ", end="")
            first = True
            # print each index
            for index in nonPalindromes:
                if not first:
                    # add commas as needed
                    print(", ", end="")
                first = False
                print(str(index), end="")
            # now print a newline
            print("")
                
        
