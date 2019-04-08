#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob06.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	char batteryState[7];
	char exchangerState[7];
	char pumpState[7];
	char sensorState[7];
	int overallState, leftState, rightState;

	// execute test cases
	while(testCases > 0){
		// read in status strings - %s covers all text until whitespace is hit
		fscanf(inputFile, "%s %s %s %s\n", batteryState, exchangerState, pumpState, sensorState);

		// reset overall status code
		overallState = 0;
		// check each status and add the appropriate value
		if(strcmp(batteryState, "BROKEN") == 0){
			overallState = overallState + 8;
		}
		if(strcmp(exchangerState, "BROKEN") == 0){
			overallState = overallState + 4;
		}
		if(strcmp(pumpState, "BROKEN") == 0){
			overallState = overallState + 2;
		}
		if(strcmp(sensorState, "BROKEN") == 0){
			overallState = overallState + 1;
		}

		// determine LED states
		// X / 4 is integer division; remainder discarded
		// X % 4 is modular division; remainder is returned
		leftState = overallState / 4;
		rightState = overallState % 4;

		// print LED states
		switch(leftState){
		case 3: printf("blue "); break;
		case 2: printf("green "); break;
		case 1: printf("red "); break;
		case 0: printf("off "); break;
		}
		switch(rightState){
		case 3: printf("blue\n"); break;
		case 2: printf("green\n"); break;
		case 1: printf("red\n"); break;
		case 0: printf("off\n"); break;
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
