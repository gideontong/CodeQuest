#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "Prob18.h"

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob18.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int pathLengths[7] = {0, 0, 0, 0, 0, 0, 0};

	// execute test cases
	while(testCases > 0){
		// read in path lengths
		fscanf(inputFile, "%d %d %d %d %d %d %d\n", &pathLengths[0], &pathLengths[1], &pathLengths[2], &pathLengths[3], &pathLengths[4], &pathLengths[5], &pathLengths[6]);

		// Array used to indicate which paths are available from a location,
		// and where said paths lead. This will be updated as the algorithm
		// continues. -1 indicates an unavailable option.
		int pathsToUse[7] = {-1, -1, -1, -1, -1, -1, -1};

		// allocate memory for an "initial" candidate path, representing
		// us standing at the starting line, not having moved anywhere
		struct DisneyPath *initial = malloc(sizeof(struct DisneyPath));
		// initialize all paths to -1 to indicate they're not yet set
		initial->paths[0] = -1;
		initial->paths[1] = -1;
		initial->paths[2] = -1;
		initial->paths[3] = -1;
		initial->paths[4] = -1;
		initial->paths[5] = -1;
		initial->paths[6] = -1;
		// we haven't gone anywhere yet
		initial->pathsUsed = 0;
		// so we're still at start
		initial->location = 0;
		// and haven't moved any distance
		initial->length = 0;
		// nor been to any rides (except the castle)
		initial->visited[0] = 1;
		initial->visited[1] = 0;
		initial->visited[2] = 0;
		initial->visited[3] = 0;
		initial->visited[4] = 0;
		// there aren't any other candidate paths yet
		initial->prev = NULL;
		initial->next = NULL;

		// start a linked list with that struct at its head (for a brief moment)
		struct DisneyPath *head = initial;

		// loop until we find a path that hits all four rides
		while(head->visited[1] == 0 || head->visited[2] == 0 || head->visited[3] == 0 || head->visited[4] == 0){
			struct DisneyPath *currentPath = head;

			// remove the first item from the list
			head = head->next;
			if(head != NULL){
				head->prev = NULL;
			}
			currentPath->next = NULL;

			// check to see if it's gotten stuck; if we've walked 7 paths
			// and haven't hit all four rides, the candidate path isn't worth
			// considering any further.
			if(currentPath->pathsUsed < 7){
				// if it hasn't, branch it out along each path
				// from the current location

				// update pathsToUse array to show where we can go
				if(currentPath->location == 0){
					// currently at start
					// can use paths 1, 3, 5, 7
					pathsToUse[0] = 1;
					pathsToUse[1] = -1;
					pathsToUse[2] = 2;
					pathsToUse[3] = -1;
					pathsToUse[4] = 3;
					pathsToUse[5] = -1;
					pathsToUse[6] = 4;
				}
				else if(currentPath->location == 1){
					// currently at pirates
					// can use paths 1, 2
					pathsToUse[0] = 0;
					pathsToUse[1] = 2;
					pathsToUse[2] = -1;
					pathsToUse[3] = -1;
					pathsToUse[4] = -1;
					pathsToUse[5] = -1;
					pathsToUse[6] = -1;
				}
				else if(currentPath->location == 2){
					// currently at splash mtn
					// can use paths 2, 3, 4
					pathsToUse[0] = -1;
					pathsToUse[1] = 1;
					pathsToUse[2] = 0;
					pathsToUse[3] = 3;
					pathsToUse[4] = -1;
					pathsToUse[5] = -1;
					pathsToUse[6] = -1;
				}
				else if(currentPath->location == 3){
					// currently at 7 dwarves
					// can use paths 4, 5, 6
					pathsToUse[0] = -1;
					pathsToUse[1] = -1;
					pathsToUse[2] = -1;
					pathsToUse[3] = 2;
					pathsToUse[4] = 0;
					pathsToUse[5] = 4;
					pathsToUse[6] = -1;
				}
				else{
					// currently at space mtn
					// can use paths 6, 7
					pathsToUse[0] = -1;
					pathsToUse[1] = -1;
					pathsToUse[2] = -1;
					pathsToUse[3] = -1;
					pathsToUse[4] = -1;
					pathsToUse[5] = 3;
					pathsToUse[6] = 0;
				}

				// generate new paths based on pathsToUse data
				for(int path = 0; path < 7; path++){
					if(pathsToUse[path] < 0){
						// we can't use this path; skip
						continue;
					}

					// allocate a new DisneyPath struct
					struct DisneyPath *newPath = malloc(sizeof(struct DisneyPath));
					// copy the original path
					// note use of -> to access fields on the struct from the struct's pointer
					for(int i = 0; i < 7; i++){
						newPath->paths[i] = currentPath->paths[i];
					}
					for(int i = 0; i < 5; i++){
						newPath->visited[i] = currentPath->visited[i];
					}
					// and add new information as though we've walked down
					// one of the paths
					newPath->paths[currentPath->pathsUsed] = path + 1;
					newPath->pathsUsed = currentPath->pathsUsed + 1;
					newPath->length = currentPath->length + pathLengths[path];
					newPath->location = pathsToUse[path];
					newPath->visited[pathsToUse[path]] = 1;
					// leave pointers null for now
					newPath->prev = NULL;
					newPath->next = NULL;

					// insert it into the list, keeping the list in sorted order
					// shorter paths should be added to the front
					if(head == NULL){
						// list is empty, make this the new head
						head = newPath;
					}
					else if(compareDisneyPaths(newPath, head) < 0){
						// belongs at the front of the list, make it the new head
						// and shove the existing head back a bit
						newPath->next = head;
						head->prev = newPath;
						head = newPath;
					}
					else{
						// find where it belongs in the list
						struct DisneyPath *previousItem = head;
						while(previousItem->next != NULL && compareDisneyPaths(newPath, previousItem->next) > 0){
							previousItem = previousItem->next;
						}
						if(previousItem->next == NULL){
							// belongs at the end of the list
							previousItem->next = newPath;
							newPath->prev = previousItem;
						}
						else{
							// squeeze it between two existing paths
							newPath->prev = previousItem;
							newPath->next = previousItem->next;
							previousItem->next = newPath;
							newPath->next->prev = newPath;
						}
					}
				}
				// end generate new paths

				// Clear out memory; this path is finished
				free(currentPath);
			}
			// end if block
		}
		// end while loop

		// we have a path that's hit all four rides, and if it's at the
		// front of the list, it must be our shortest path!
		for(int i = 0; i < head->pathsUsed; i++){
			if(i > 0){
				printf(" ");
			}
			printf("%d", head->paths[i]);
		}
		printf("\n");

		// Clear out memory; destroy the entire list
		struct DisneyPath *nextItem = head->next;
		while(nextItem != NULL){
			free(head);
			head = nextItem;
			nextItem = head->next;
		}
		free(head);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}

int compareDisneyPaths(struct DisneyPath *path1, struct DisneyPath *path2){
	// if path1 is shorter, return -1
	if(path1->length < path2->length){
		return -1;
	}
	// if path2 is shorter, return 1
	else if(path1->length > path2->length){
		return 1;
	}
	else{
		// find which path takes a lower-numbered path first; return -1/1 as appropriate
		for(int i = 0; i < path1->pathsUsed && i < path2->pathsUsed; i++){
			if(path1->paths[i] < path2->paths[i]){
				return -1;
			}
			else if(path1->paths[i] > path2->paths[i]){
				return 1;
			}
		}
	}
	// should never get here
	return 0;
}
