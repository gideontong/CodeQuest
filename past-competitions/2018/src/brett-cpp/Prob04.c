#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob04.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int rockCount, paperCount, scissorsCount;

	// execute test cases
	while(testCases > 0){
		// read first character
		char nextChar = fgetc(inputFile);
		// reset counts
		rockCount = 0;
		paperCount = 0;
		scissorsCount = 0;

		// read each character until we hit a newline
		while(nextChar != '\n'){
			// count rock/paper/scissors (ignore spaces)
			if(nextChar == 'R'){
				rockCount++;
			}
			else if(nextChar == 'P'){
				paperCount++;
			}
			else if(nextChar == 'S'){
				scissorsCount++;
			}

			// read next character
			nextChar = fgetc(inputFile);
		}

		if(rockCount == 1 && paperCount == 0){
			// rock wins if there's one rock and no papers to beat it
			printf("ROCK\n");
		}
		else if(paperCount == 1 && scissorsCount == 0){
			// paper wins if there's one paper and no scissors to beat it
			printf("PAPER\n");
		}
		else if(scissorsCount == 1 && rockCount == 0){
			// scissors win if there's one scissors (scissor?) and no rocks to beat it
			printf("SCISSORS\n");
		}
		else{
			// either there's a multiple of something, or all three results are represented
			// either way, it's a tie
			printf("NO WINNER\n");
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
