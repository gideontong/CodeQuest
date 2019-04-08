# Define the rotors
# Each value represents the amount that must be added
# to get the output value when feeding input into that index.
# Index values must be adjusted to account for rotation.
rotors = [[1, 2, 4, 7, 1, 9, 2, 0, 1, 3],
          [0, 2, 3, 9, 2, 4, 5, 7, 0, 8],
          [5, 8, 9, 4, 9, 3, 4, 5, 6, 7],
          [1, 5, 3, 9, 5, 5, 1, 7, 5, 9]]
inverseRotors = [[3, 9, 7, 8, 1, 9, 6, 0, 8, 9],
                 [0, 5, 1, 8, 3, 7, 8, 2, 0, 6],
                 [6, 1, 5, 1, 4, 5, 3, 6, 7, 2],
                 [5, 9, 1, 5, 3, 7, 5, 9, 1, 5]]
# Reflector doesn't rotate
reflector = [3, 5, 6, 7, 1, 9, 5, 2, 4, 8]

# Open the input file
with open("Prob19.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the key settings
        leftData = inputFile.readline().replace("\n", "")
        middleData = inputFile.readline().replace("\n", "")
        rightData = inputFile.readline().replace("\n", "")
        leftRotorData = leftData.split(" ")
        middleRotorData = middleData.split(" ")
        rightRotorData = rightData.split(" ")

        # Convert to integers
        leftRotor = int(leftRotorData[0]) - 1
        middleRotor = int(middleRotorData[0]) - 1
        rightRotor = int(rightRotorData[0]) - 1
        leftRotorStart = int(leftRotorData[1])
        middleRotorStart = int(middleRotorData[1])
        rightRotorStart = int(rightRotorData[1])
        
        # Read plaintext
        plaintext = inputFile.readline().replace("\n", "")

        # Set up cipher
        leftPosition = leftRotorStart
        middlePosition = middleRotorStart
        rightPosition = rightRotorStart
        for i in range(len(plaintext)):
            value = int(plaintext[i])

            # Enter left rotor
            # Calculate index for left rotor (same process for each rotor)
            index = value - leftPosition
            if index < 0:
                index = index + 10
            # Add the value of the calculated index.
            # Mod by 10 to keep single digit
            value = (value + rotors[leftRotor][index]) % 10

            # Enter middle rotor
            index = value - middlePosition
            if index < 0:
                index = index + 10
            value = (value + rotors[middleRotor][index]) % 10

            # Enter right rotor
            index = value - rightPosition
            if index < 0:
                index = index + 10
            value = (value + rotors[rightRotor][index]) % 10

            # Enter reflector
            # This doesn't rotate, so value is the index
            value = (value + reflector[value]) % 10

            # Now go backward through the rotors
            # Reenter right rotor
            index = value - rightPosition
            if index < 0:
                index = index + 10
            value = (value + inverseRotors[rightRotor][index]) % 10

            # Reenter middle rotor
            index = value - middlePosition
            if index < 0:
                index = index + 10
            value = (value + inverseRotors[middleRotor][index]) % 10

            # Reenter left rotor
            index = value - leftPosition
            if index < 0:
                index = index + 10
            value = (value + inverseRotors[leftRotor][index]) % 10

            # Print the resulting digit, suppressing the newline
            print(str(value), end="")

            # Rotate the right rotor one position
            # When it returns to its original position, rotate
            # the middle rotor one position, and so on
            rightPosition = (rightPosition + 1) % 10
            if rightPosition == rightRotorStart:
                middlePosition = (middlePosition + 1) % 10
                if middlePosition == middleRotorStart:
                    leftPosition = (leftPosition + 1) % 10
            # end rotation if
        # end for loop
        # Print newline for next test case
        print("")
