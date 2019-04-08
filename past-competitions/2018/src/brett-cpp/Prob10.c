#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob10.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int boardX, boardY, startX, startY, endX, endY;

	// execute test cases
	while(testCases > 0){
		// read in data
		fscanf(inputFile, "%d,%d\n", &boardX, &boardY);
		fscanf(inputFile, "%d,%d\n", &startX, &startY);
		fscanf(inputFile, "%d,%d\n", &endX, &endY);

		// size of board is not important; a bishop can access any space
		// that's the same color as the one it started on.
		// white spaces will be on "even" squares; add X and Y, if the
		// result is even, the space is white.
		if((startX + startY) % 2 == (endX + endY) % 2){
			printf("Yes\n");
		}
		else{
			printf("No\n");
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
