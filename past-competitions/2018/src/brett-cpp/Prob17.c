#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob17.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	char board[9];
	char winner;

	// execute test cases
	while(testCases > 0){
		// read the board state
		fscanf(inputFile, "%s\n", board);

		// set winner to null character to indicate a tie
		winner = '\0';

		// search for winners. three spaces in a row, column, or diagonal must be equal
		if(board[0] == 'X' || board[0] == 'O'){
			// check options extending from top left corner
			if(board[1] == board[0] && board[2] == board[0]){
				// top row
				winner = board[0];
			}
			else if(board[4] == board[0] && board[8] == board[0]){
				// downward diagonal
				winner = board[0];
			}
			else if(board[3] == board[0] && board[6] == board[0]){
				// left column
				winner = board[0];
			}
		}
		if(board[1] == 'X' || board[1] == 'O'){
			// check options extending from top center
			if(board[4] == board[1] && board[7] == board[1]){
				// which is the center column only; top row already checked
				winner = board[1];
			}
		}
		if(board[2] == 'X' || board[2] == 'O'){
			// check options extending from top right corner
			// top row already checked
			if(board[5] == board[2] && board[8] == board[2]){
				// upward diagonal
				winner = board[2];
			}
			else if(board[4] == board[2] && board[6] == board[2]){
				// right column
				winner = board[2];
			}
		}
		if(board[3] == 'X' || board[3] == 'O'){
			// check options extending from middle left
			if(board[4] == board[3] && board[5] == board[3]){
				// middle row only (left column checked already)
				winner = board[3];
			}
		}
		if(board[6] == 'X' || board[6] == 'O'){
			// check options extending from bottom left corner
			if(board[7] == board[6] && board[8] == board[6]){
				// bottom row only (all others checked already)
				winner = board[6];
			}
		}

		// if the 'winner' is still null, it's a tie
		if(winner == '\0'){
			printf("%s = TIE\n", board);
		}
		else{
			// otherwise, print who won
			printf("%s = %c WINS\n", board, winner);
		}

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
