#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob03.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int value;

	// execute test cases
	while(testCases > 0){
		// read in the number (ignore the suffix)
		fscanf(inputFile, "%dth\n", &value);

		// % is modular division; it returns the remainder, not the dividend
		// X % 10 returns the last digit of a decimal integer
		// if number is a "teen" or its last digit is 0 or 4+, it still uses th
		if((value % 100 >= 10 && value % 100 <= 20) || value % 10 == 0 || value % 10 >= 4){
			printf("%dth\n", value);
		}
		// otherwise it's *1st, *2nd, *3rd
		else if(value % 10 == 1){
			printf("%dst\n", value);
		}
		else if(value % 10 == 2){
			printf("%dnd\n", value);
		}
		else if(value % 10 == 3){
			printf("%drd\n", value);
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
