#ifndef PROB13_H_
#define PROB13_H_

// struct to hold data about a single person
// assume names, etc., won't be more than 100 characters long
struct PeopleBookEntry {
	char name[100];
	int age;
	char instagram[100];
	char twitter[100];
	char mobile[11];
	// mobile must NOT be an int or long! Maximum (unsigned) value is 4,294,967,295
	// which wouldn't cover over half of all possible phone numbers
	// also include an extra space for a null terminator
	char email[100];
};

#endif