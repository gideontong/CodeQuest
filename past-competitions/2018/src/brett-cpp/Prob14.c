#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob14.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int upperLines, lowerLines, upperLine, lowerLine, upperLength, lowerLength, lowerIndex;
	char nextChar;
	// buffer to hold the lowercase message until we can print it
	// 4098 characters is deliberately excessive
	char lowercaseText[4098];

	// execute test cases
	while(testCases > 0){
		// get number of uppercase lines
		fscanf(inputFile, "%d\n", &upperLines);

		// get lengths of each uppercase line
		int upperLineLengths[upperLines];
		for(int i = 0; i < upperLines; i++){
			fscanf(inputFile, "%d", &upperLineLengths[i]);

			// advance cursor
			if(i < (upperLines - 1)){
				fscanf(inputFile, " ");
			}
			else{
				fscanf(inputFile, "\n");
			}
		}

		// get number of lowercase lines
		fscanf(inputFile, "%d\n", &lowerLines);

		// get lengths of each lowercase line
		int lowerLineLengths[lowerLines];
		for(int i = 0; i < lowerLines; i++){
			fscanf(inputFile, "%d", &lowerLineLengths[i]);

			// advance cursor
			if(i < (lowerLines - 1)){
				fscanf(inputFile, " ");
			}
			else{
				fscanf(inputFile, "\n");
			}
		}

		// keep track of which line we're on in each message,
		// and how long those lines are. Also keep track of
		// where we're at in the lowercase buffer
		upperLine = 0;
		lowerLine = 0;
		upperLength = 0;
		lowerLength = 0;
		// add a newline at the start to make printing easier
		lowercaseText[0] = '\n';
		lowerIndex = 1;

		// while either message is incomplete
		while(upperLine != upperLines || lowerLine != lowerLines){
			// read one character at a time
			fscanf(inputFile, "%c", &nextChar);

			if(nextChar == '-'){
				// print uppercase space immediately
				printf(" ");
				upperLength = upperLength + 1;
			}
			else if(nextChar == '='){
				// save lowercase space in buffer
				lowercaseText[lowerIndex] = ' ';
				lowerIndex = lowerIndex + 1;
				lowerLength = lowerLength + 1;
			}
			else if(nextChar >= 'A' && nextChar <= 'Z'){
				// print uppercase letter immediately
				printf("%c", nextChar);
				upperLength = upperLength + 1;
			}
			else if(nextChar >= 'a' && nextChar <= 'z'){
				// save lowercase letter in buffer
				lowercaseText[lowerIndex] = nextChar;
				lowerIndex = lowerIndex + 1;
				lowerLength = lowerLength + 1;
			}

			if(upperLength == upperLineLengths[upperLine]){
				// end uppercase line and start the next
				printf("\n");
				upperLine = upperLine + 1;
				upperLength = 0;
			}
			else if(lowerLength == lowerLineLengths[lowerLine]){
				// end lowercase line and start the next
				lowercaseText[lowerIndex] = '\n';
				lowerIndex = lowerIndex + 1;
				lowerLine = lowerLine + 1;
				lowerLength = 0;
			}
		}
		// advance cursor
		fscanf(inputFile, "\n");

		// terminate lowercase message
		lowercaseText[lowerIndex] = '\0';
		// then print it
		printf("%s", lowercaseText);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
