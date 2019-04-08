#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob05.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int n, temp, sequenceLength;

	// execute test cases
	while(testCases > 0){
		// read 'n' (the number)
		fscanf(inputFile, "%d\n", &n);

		// count how many times we muck with the number
		// before it ends up at 1
		temp = n;
		sequenceLength = 1;
		while(temp != 1){
			sequenceLength = sequenceLength + 1;

			if(temp % 2 == 0){
				// even numbers are chopped in half
				temp = temp / 2;
			}
			else{
				// odd numbers are multipled by 3, then get 1 added
				temp = (3 * temp) + 1;
			}
		}

		// print the number and the sequence length
		printf("%d:%d\n", n, sequenceLength);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
