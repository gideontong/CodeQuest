# Open the input file
with open("Prob12.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the limit; add 1 so the limit itself is included
        limit = int(inputFile.readline().replace("\n", "")) + 1
        # Fill an array with 'True' to indicate numbers
        # are potential primes. We'll mark them as False
        # as they are proven to be composite
        possiblePrimes = [True for x in range(limit)]
        primes = []
        # start with 2; 0 and 1 are neither prime nor composite
        for i in range(2, limit):
            if possiblePrimes[i]:
                # if still True, it's a prime
                primes.append(i)
                eliminated = 0
                # eliminate multiples
                # go from 2i to limit, increasing by i
                for j in range(i * 2, limit, i):
                    if possiblePrimes[j]:
                        possiblePrimes[j] = False
                        eliminated = eliminated + 1
                # if we eliminated anything new, report it
                if eliminated > 0:
                    print("Prime " + str(i) + " Composite Set Size: " + str(eliminated))
        # When done, report all primes
        first = True
        # defining end suppresses the default newline
        print("{", end="")
        for prime in primes:
            if not first:
                # add commas as needed
                print(",", end="")
            first = False
            print(str(prime), end="")
        # now print the newline
        print("}")
