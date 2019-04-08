#ifndef PROB18_H_
#define PROB18_H_

// structure to hold a candidate path
struct DisneyPath {
	// the number of paths we've taken
	// limited to 7, because for this map, that's the highest number of paths 
	// you can take in order to hit all four rides without unnecessarily retracing steps
	int paths[7];
	// the total number of paths taken; aka, the number of entries in paths
	// that have meaningful values
	int pathsUsed;
	// the current location (each ride is assigned a number 1-4; start is 0)
	int location;
	// the total length of the current path
	int length;
	// an array of 1's and 0's to indicate where we've been. 1 is visited
	int visited[5];
	// Pointers to the next-best and next-worst path options we've found
	// so far. These will be updated as new paths are found and bad ones
	// are eliminated.
	struct DisneyPath *prev;
	struct DisneyPath *next;
};

// convenience function to compare two paths
// returns -1 if path1 is shorter, or 1 is path2 is shorter
// if the same length, returns -1/1 for whichever path uses
// the lowest-numbered option
int compareDisneyPaths(struct DisneyPath *path1, struct DisneyPath *path2);

#endif