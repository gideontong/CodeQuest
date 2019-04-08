#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob19.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	char nextChar;
	int currentValue;

	// each number represents the amount to add to the input
	// to get the output. each rotor is listed in position 0.
	// index values must be adjusted to account for rotation.
	int rotors[4][10] = {{1, 2, 4, 7, 1, 9, 2, 0, 1, 3},
			{0, 2, 3, 9, 2, 4, 5, 7, 0, 8},
			{5, 8, 9, 4, 9, 3, 4, 5, 6, 7},
			{1, 5, 3, 9, 5, 5, 1, 7, 5, 9}};
	int inverseRotors[4][10] = {{3, 9, 7, 8, 1, 9, 6, 0, 8, 9},
			{0, 5, 1, 8, 3, 7, 8, 2, 0, 6},
			{6, 1, 5, 1, 4, 5, 3, 6, 7, 2},
			{5, 9, 1, 5, 3, 7, 5, 9, 1, 5}};
	int reflector[10] = {3, 5, 6, 7, 1, 9, 5, 2, 4, 8};

	int leftRotor, leftRotorStart, leftRotorPosition;
	int middleRotor, middleRotorStart, middleRotorPosition;
	int rightRotor, rightRotorStart, rightRotorPosition;
	int rotorIndex;

	// execute test cases
	while(testCases > 0){
		// read key settings
		fscanf(inputFile, "%d %d\n", &leftRotor, &leftRotorStart);
		fscanf(inputFile, "%d %d\n", &middleRotor, &middleRotorStart);
		fscanf(inputFile, "%d %d\n", &rightRotor, &rightRotorStart);

		// adjust for index
		leftRotor = leftRotor - 1;
		middleRotor = middleRotor - 1;
		rightRotor = rightRotor - 1;
		// set start positions
		leftRotorPosition = leftRotorStart;
		middleRotorPosition = middleRotorStart;
		rightRotorPosition = rightRotorStart;

		// read characters one at a time
		fscanf(inputFile, "%c", &nextChar);
		// until we hit a newline
		while(nextChar != '\n'){
			// convert from number - '0' == 48, '1' == 49
			// so '0' - 48 = 0, etc.
			currentValue = nextChar - 48;

			// enter left rotor
			// when in position 0, currentValue is the rotorIndex
			// when the rotor is shifted, we have to adjust accordingly
			rotorIndex = currentValue - leftRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			// add the rotor's value to the current value
			currentValue = currentValue + rotors[leftRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// enter middle rotor
			rotorIndex = currentValue - middleRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			currentValue = currentValue + rotors[middleRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// enter right rotor
			rotorIndex = currentValue - rightRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			currentValue = currentValue + rotors[rightRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// enter reflector
			// the reflector doesn't rotate
			currentValue = currentValue + reflector[currentValue];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// re-enter right rotor
			// same as above, but now we use the inverseRotor arrays
			// for going "backwards" through the rotors
			rotorIndex = currentValue - rightRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			currentValue = currentValue + inverseRotors[rightRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// re-enter middle rotor
			rotorIndex = currentValue - middleRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			currentValue = currentValue + inverseRotors[middleRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// re-enter left rotor
			rotorIndex = currentValue - leftRotorPosition;
			if(rotorIndex < 0){
				rotorIndex = rotorIndex + 10;
			}
			currentValue = currentValue + inverseRotors[leftRotor][rotorIndex];
			if(currentValue >= 10){
				currentValue = currentValue - 10;
			}

			// print the resulting value
			printf("%d", currentValue);

			// rotate rotors
			rightRotorPosition = rightRotorPosition + 1;
			if(rightRotorPosition == 10){
				rightRotorPosition = 0;
			}
			// if right rotor has completed a full rotation,
			if(rightRotorPosition == rightRotorStart){
				// rotate the middle one
				middleRotorPosition = middleRotorPosition + 1;
				if(middleRotorPosition == 10){
					middleRotorPosition = 0;
				}
				// if it's completed a full rotation,
				if(middleRotorPosition == middleRotorStart){
					// rotate the left one too
					leftRotorPosition = leftRotorPosition + 1;
					if(leftRotorPosition == 10){
						leftRotorPosition = 0;
					}
				}
			}

			// get next character
			fscanf(inputFile, "%c", &nextChar);
		}
		printf("\n");

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
