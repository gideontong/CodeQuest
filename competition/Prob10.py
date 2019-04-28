def decrypt(strng, key):
    decrypted = ''
    for x in strng:
        indx = (ord(x) - key) % 256
        if indx < 32:
            indx = indx + 97
        if indx < 97:
            indx = indx + 26
        if indx > 122:
            indx = 32
        decrypted = decrypted + chr(indx)
    return decrypted

# Recommended imports for all problems
# Some problems may require more
import sys
import math
import string
cases = int(sys.stdin.readline().rstrip())
for caseNum in range(cases):
    key = int(sys.stdin.readline().rstrip())
    string = sys.stdin.readline().rstrip()
    print(decrypt(string, key))