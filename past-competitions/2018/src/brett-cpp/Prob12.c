#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob12.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int limit;
	int sieve[45000];
	int primes[45000];
	int primeCount = 0;
	int compositeCount = 0;

	// execute test cases
	while(testCases > 0){
		fscanf(inputFile, "%d\n", &limit);

		// reset the sieve
		// start with 2, as 0 and 1 are considered neither prime nor composite
		for(int i = 2; i <= limit; i++){
			// 1 == prime
			// a number will be set to 0 when found to be composite
			sieve[i] = 1;
		}
		// reset the number of primes
		// we don't have to reset the list of primes, since
		// which numbers are prime won't change
		primeCount = 0;

		// perform the sieve
		for(int i = 2; i <= limit; i++){
			if(sieve[i] == 1){
				// prime number
				// eliminate multiples as composites and count them
				compositeCount = 0;
				for(int j = i + i; j <= limit; j = j + i){
					if(sieve[j] == 1){
						// only count if not already eliminated
						sieve[j] = 0;
						compositeCount++;
					}
				}
				// if eliminations occurred, print how many
				if(compositeCount > 0){
					printf("Prime %d Composite Set Size: %d\n", i, compositeCount);
				}
				// record the prime
				primes[primeCount] = i;
				primeCount++;
			}
		}

		// print the primes
		printf("{");
		for(int i = 0; i < primeCount; i++){
			if(i > 0){
				// add commas as needed
				printf(",");
			}
			printf("%d", primes[i]);
		}
		printf("}\n");

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
