import string
# Open the input file
with open("Prob15.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # This constant contains all uppercase letters
    # We will use this to make letter/number conversion easier
    # Add a 0 in front to ensure A ends up at index 1
    letters = '0' + string.ascii_uppercase
    # For each test case
    for caseNum in range(cases):
        # Read the next line
        plaintext = inputFile.readline().replace("\n", "")
        # for each letter
        for i in range(len(plaintext)):
            original = plaintext[i]
            # convert to a number 1-26
            value = letters.index(original)
            if value <= 5:
                # A-E - Add 6
                value = value + 6
            elif value <= 10:
                # F-J - Square
                value = value ** 2
            elif value <= 15:
                # K-O - Modulo Divide by 3, Multiply by 5, Add 1
                value = ((value % 3) * 5) + 1
            elif value <= 20:
                # P-T - Add digits, Multiply by 8
                value = ((value // 10) + (value % 10)) * 8
            else:
                # U-Z - Multiply largest integer factor by 2
                factor = 1
                # Start at half of value (rounded down) and count down
                for i in range(value // 2, 1, -1):
                    if value % i == 0:
                        factor = i
                        break
                value = factor * 2
            # Rein in value as needed to fit within 1-26
            while value > 26:
                value = value - 26
            # Convert back
            newLetter = letters[value]
            # Values of 0 use the original letter
            if newLetter == '0':
                newLetter = original
            # Print letter (specifying end suppresses the automatic newline)
            print(newLetter, end="")
        # Now print a newline in preparation for next word
        print("")
            
