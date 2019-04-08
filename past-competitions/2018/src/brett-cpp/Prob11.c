#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob11.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int cR, cG, cB, t, fR, fG, fB, bR, bG, bB;

	// execute test cases
	while(testCases > 0){
		// remind me to tell Mike not to put this many inputs on one line again
		fscanf(inputFile, "%d %d %d %d %d %d %d %d %d %d\n", &cR, &cG, &cB, &t, &fR, &fG, &fB, &bR, &bG, &bB);

		// find distance between foreground and chroma key
		double rDist = (fR - cR) * (fR - cR);
		double gDist = (fG - cG) * (fG - cG);
		double bDist = (fB - cB) * (fB - cB);
		double distance = sqrt(rDist + gDist + bDist);

		// if the distance is less than or equal to the tolerance,
		// use the background color
		if(distance <= t){
			printf("%d %d %d\n", bR, bG, bB);
		}
		else{
			printf("%d %d %d\n", fR, fG, fB);
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
