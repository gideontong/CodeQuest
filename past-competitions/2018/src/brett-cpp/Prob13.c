#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "Prob13.h"

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob13.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int numPeople, peopleIndex, dataIndex;
	char nextChar;
	char nameToPrint[100];
	struct PeopleBookEntry *people;

	// execute test cases
	while(testCases > 0){
		fscanf(inputFile, "%d\n", &numPeople);

		// allocate array large enough to hold all data
		people = calloc(numPeople, sizeof(struct PeopleBookEntry));

		fscanf(inputFile, "[[");

		// read names
		peopleIndex = 0;
		while(peopleIndex != numPeople){
			fscanf(inputFile, "%c", &nextChar);
			dataIndex = 0;

			// read each character until we hit a comma or bracket
			while(nextChar != ',' && nextChar != ']'){
				// add it to the name
				people[peopleIndex].name[dataIndex] = nextChar;
				dataIndex = dataIndex + 1;
				fscanf(inputFile, "%c", &nextChar);
			}

			// terminate string
			people[peopleIndex].name[dataIndex] = '\0';

			// update indices
			peopleIndex = peopleIndex + 1;
			dataIndex = 0;
		}

		// advance cursor
		fscanf(inputFile, ",[");

		// read ages
		for(int i = 0; i < numPeople; i++){
			fscanf(inputFile, "%d", &people[i].age);
			// advance cursor
			if(i < (numPeople - 1)){
				fscanf(inputFile, ",");
			}
		}

		// advance cursor
		fscanf(inputFile, "],[");

		// read instagram usernames
		peopleIndex = 0;
		while(peopleIndex != numPeople){
			fscanf(inputFile, "%c", &nextChar);
			dataIndex = 0;

			// read each character until we hit a comma or bracket
			while(nextChar != ',' && nextChar != ']'){
				// add it to the instagram name
				people[peopleIndex].instagram[dataIndex] = nextChar;
				dataIndex = dataIndex + 1;
				fscanf(inputFile, "%c", &nextChar);
			}

			// terminate string
			people[peopleIndex].instagram[dataIndex] = '\0';

			// update indices
			peopleIndex = peopleIndex + 1;
			dataIndex = 0;
		}

		// advance cursor
		fscanf(inputFile, ",[");

		// read twitter usernames
		peopleIndex = 0;
		while(peopleIndex != numPeople){
			fscanf(inputFile, "%c", &nextChar);
			dataIndex = 0;

			// read each character until we hit a comma or bracket
			while(nextChar != ',' && nextChar != ']'){
				// add it to the twitter name
				people[peopleIndex].twitter[dataIndex] = nextChar;
				dataIndex = dataIndex + 1;
				fscanf(inputFile, "%c", &nextChar);
			}

			// terminate string
			people[peopleIndex].twitter[dataIndex] = '\0';

			// update indices
			peopleIndex = peopleIndex + 1;
			dataIndex = 0;
		}

		// advance cursor
		fscanf(inputFile, ",[");

		// read phone numbers
		peopleIndex = 0;
		while(peopleIndex != numPeople){
			fscanf(inputFile, "%c", &nextChar);
			dataIndex = 0;

			// read each character until we hit a comma or bracket
			while(nextChar != ',' && nextChar != ']'){
				// add it to the phone number
				people[peopleIndex].mobile[dataIndex] = nextChar;
				dataIndex = dataIndex + 1;
				fscanf(inputFile, "%c", &nextChar);
			}

			// terminate string
			people[peopleIndex].mobile[dataIndex] = '\0';

			// update indices
			peopleIndex = peopleIndex + 1;
			dataIndex = 0;
		}

		// advance cursor
		fscanf(inputFile, ",[");

		// read emails
		peopleIndex = 0;
		while(peopleIndex != numPeople){
			fscanf(inputFile, "%c", &nextChar);
			dataIndex = 0;

			// read each character until we hit a comma or bracket
			while(nextChar != ',' && nextChar != ']'){
				// add it to the email
				people[peopleIndex].email[dataIndex] = nextChar;
				dataIndex = dataIndex + 1;
				fscanf(inputFile, "%c", &nextChar);
			}

			// terminate string
			people[peopleIndex].email[dataIndex] = '\0';

			// update indices
			peopleIndex = peopleIndex + 1;
			dataIndex = 0;
		}

		// advance cursor
		fscanf(inputFile, "]\n");

		// read names to output
		for(int i = 0; i < numPeople; i++){
			fscanf(inputFile, "%s\n", nameToPrint);

			// find the index of the person with that name
			peopleIndex = 0;
			for(peopleIndex = 0; peopleIndex < numPeople; peopleIndex++){
				if(strcmp(people[peopleIndex].name, nameToPrint) == 0){
					break;
				}
			}

			// print all data at that index
			printf("Name: %s\n", people[peopleIndex].name);
			printf("Age: %d\n", people[peopleIndex].age);
			printf("Instagram: %s\n", people[peopleIndex].instagram);
			printf("Twitter: %s\n", people[peopleIndex].twitter);
			printf("Phone: %s\n", people[peopleIndex].mobile);
			printf("Email: %s\n", people[peopleIndex].email);
		}

		// deallocate array
		free(people);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
