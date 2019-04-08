#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
   FILE *br;
   char INPUT_FILE_NAME[] = "Prob00.in.txt";
   int T;
   int N;
   int i, j;
   int MAX_STRING = 80;
   char inLine[MAX_STRING+1];

   // prepare to read the file
   br = fopen(INPUT_FILE_NAME, "r");

   // get the number of test cases
   fscanf(br, "%d\n", &T);

   // loop through the test cases
   while (T > 0)
   {
      // get the number of lines in each test case
      fscanf(br, "%d\n", &N);

      // loop through the lines
      for (i=0; i<N; i++)
      {
         // Read the line of text
         j = 0;
         inLine[j] = fgetc(br);
         while ( (j < MAX_STRING) && (inLine[j] != '\n') && (inLine[j] != '\0') && (inLine[j] != '\r') )
         {
            j = j + 1;
            inLine[j] = fgetc(br);
         }
         inLine[j] = '\0';

         // print it out
         printf ("%s\n", inLine);
      }

      T = T - 1;

   }

   // clean up
   fclose(br);

}
