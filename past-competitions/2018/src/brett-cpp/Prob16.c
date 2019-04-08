#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob16.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int centerX, centerY, points, outerRadius, innerRadius;
	double currentAngle, angleSpacing, x, y;

	// execute test cases
	while(testCases > 0){
		// read inputs
		fscanf(inputFile, "%d %d %d %d %d\n", &centerX, &centerY, &points, &outerRadius, &innerRadius);

		// Most languages include a constant for PI; C's should be M_PI
		// If you forget or it's not defined, just use however many digits
		// you can remember; at least 5 should be accurate enough.
		// PI / 2 is straight up (90 degrees)
		currentAngle = M_PI / 2; // 90 degrees
		// Calculate distance in radians between vertices
		angleSpacing = (2 * M_PI) / (points * 2); // 360 / (points * 2)

		// Calculate each vertex
		for(int i = 0; i < (points * 2); i++){
			// Use the appropriate radius - even i values represent points
			// on the outer circle
			if(i % 2 == 0){
				x = (outerRadius * cos(currentAngle)) + centerX;
				y = (outerRadius * sin(currentAngle)) + centerY;
			}
			else{
				x = (innerRadius * cos(currentAngle)) + centerX;
				y = (innerRadius * sin(currentAngle)) + centerY;
			}

			// Adjust for -0 issues
			// Values such as -0.001 will be rounded to (and print as)
			// -0.00, which isn't technically a number, and will
			// invalidate your response.
			if(x < 0 && x > -0.005){
				x = 0.00;
			}
			if(y < 0 && y > -0.005){
				y = 0.00;
			}

			// Print coordinates
			if(i != 0){
				printf(" ");
			}
			printf("%.2f,%.2f", x, y);

			// Move to the next angle; don't worry about exceeding 2*PI
			currentAngle = currentAngle + angleSpacing;
		}
		printf("\n");

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
