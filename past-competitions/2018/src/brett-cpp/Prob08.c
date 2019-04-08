#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob08.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	float angle1, angle2, angle3;

	// execute test cases
	while(testCases > 0){
		// read in all angles
		fscanf(inputFile, "%f %f %f\n", &angle1, &angle2, &angle3);
		// add 180* to each of them
		angle1 = angle1 + 180.0;
		angle2 = angle2 + 180.0;
		angle3 = angle3 + 180.0;
		// bring back below 360* as necessary
		if(angle1 >= 360.0){
			angle1 = angle1 - 360.0;
		}
		if(angle2 >= 360.0){
			angle2 = angle2 - 360.0;
		}
		if(angle3 >= 360.0){
			angle3 = angle3 - 360.0;
		}
		// and print them back out
		// 0 = print leading zeroes as needed
		// 6 = the entire number must contain six characters (XXX.XX)
		// .2 = print two digits after the decimal point
		printf("%06.2f %06.2f %06.2f\n", angle1, angle2, angle3);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
