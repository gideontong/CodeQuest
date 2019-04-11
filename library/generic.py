"""
This is a generic template that should be as boilerplate for all
programs written in Python as the appropriate format.
"""

with open("Prob01.in.txt", "rt") as inputFile:
    cases = int(inputFile.readline().replace("\n", ""))
    for caseNum in range(cases):
        line = inputFile.readline().replace("\n", "")
        # Write all of your code here