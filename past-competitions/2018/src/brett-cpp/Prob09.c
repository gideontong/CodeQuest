#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob09.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int a, b, c, temp;

	// execute test cases
	while(testCases > 0){
		// read the initial a and b values
		fscanf(inputFile, "%d,%d\n", &a, &b);

		// swap them if needed so a is larger
		if(a < b){
			temp = a;
			a = b;
			b = temp;
		}

		do{
			// subtract b from a (into c)
			c = a - b;
			// print the equation
			printf("%d-%d=%d\n", a, b, c);
			if(c != 0){
				// if c isn't 0, set us up for the next round
				a = b;
				b = c;
				// swap a and b if needed
				if(a < b){
					temp = a;
					a = b;
					b = temp;
				}
			}
		}while(c != 0);

		// if a and b are 1, the original numbers are coprime
		if(a == 1 && b == 1){
			printf("COPRIME\n");
		}
		// otherwise they ain't
		else{
			printf("NOT COPRIME\n");
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
