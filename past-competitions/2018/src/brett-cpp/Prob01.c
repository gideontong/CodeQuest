#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob01.in.txt", "r");
	int score;
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);

	// execute test cases
	while(testCases > 0){
		// read score - %d returns an integer number
		// &score is the address of the score variable
		fscanf(inputFile, "%d\n", &score);

		// check score
		if(score >= 70){
			printf("PASS\n");
		}
		else{
			printf("FAIL\n");
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
