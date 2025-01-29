/***********************************************************************
 *                                                                     *
 * Light Automation File Format Processor                              *
 *                                                                     *
 * Team SCC111                                                         *
 *                                                                     *
 *By Aaron Rojy                                                            *
 ***********************************************************************/
#include <stdio.h>
#include <strings.h>
#include <stdbool.h>

#define MAX_BEGIN_SHOW_DATA 100
#define MAX_FIXTURES 100
#define MAX_STRING_LEN 800
#define MAX_MAPPINGS 100 
#define MAX_CUES 100
#define MAX_STEPS 200

/* this type is used to hold cue information */
typedef struct
{
  int id;
  int channel;
  int value;
} cueType;

int main() {
    /* Open Lighting Automation File */
    char *laffFileName = "laff.txt";
    FILE *lfp = fopen(laffFileName, "r");

    if (lfp == NULL) {
        printf("Error: Could not open file %s\n", laffFileName);
        return 1; //Exit if the file cannot be opened
    }

    //Variables
    char line[MAX_STRING_LEN];
    int Counter_SHOW_DATA = 0;
    int Counter_FIXTURE_MAPPING = 0;
    int Counter_CUES = 0;
    int Counter_PLAY = 0;

    bool BEGIN_SHOW_DATA = false;
    bool BEGIN_FIXTURE_MAPPING = false;
    bool BEGIN_CUES = false;
    bool BEGIN_PLAY = false;

    char BEGIN_SHOW_DATA_ARRAY[MAX_BEGIN_SHOW_DATA][MAX_STRING_LEN];
    char BEGIN_FIXTURE_MAPPING_ARRAY[MAX_FIXTURES][MAX_STRING_LEN];
    char BEGIN_CUES_ARRAY[MAX_CUES][MAX_STRING_LEN];
    char BEGIN_PLAY_ARRAY[MAX_STEPS][MAX_STRING_LEN];

    /* Read the file line by line */
    while (fgets(line, sizeof(line), lfp)) {
        //Remove newline character from the line (if present)
        line[strcspn(line, "\n")] = '\0';

        //Debug message for reading lines
        printf("Read line: %s\n", line);

        //Check for section start and end tokens
        if (strstr(line, "BEGIN_SHOW_DATA")) {
            BEGIN_SHOW_DATA = true;
            printf("BEGIN_SHOW_DATA started\n");
            continue;
        } else if (strstr(line, "END_SHOW_DATA")) {
            BEGIN_SHOW_DATA = false;
            printf("BEGIN_SHOW_DATA ended\n");
            continue;
        }

        if (strstr(line, "BEGIN_FIXTURE_MAPPING")) {
            BEGIN_FIXTURE_MAPPING = true;
            printf("BEGIN_FIXTURE_MAPPING started\n");
            continue;
        } else if (strstr(line, "END_FIXTURE_MAPPING")) {
            BEGIN_FIXTURE_MAPPING = false;
            printf("BEGIN_FIXTURE_MAPPING ended\n");
            continue;
        }

        if (strstr(line, "BEGIN_CUES")) {
            BEGIN_CUES = true;
            printf("BEGIN_CUES started\n");
            continue;
        } else if (strstr(line, "END_CUES")) {
            BEGIN_CUES = false;
            printf("BEGIN_CUES ended\n");
            continue;
        }

        if (strstr(line, "BEGIN_PLAY")) {
            BEGIN_PLAY = true;
            printf("BEGIN_PLAY started\n");
            continue;
        } else if (strstr(line, "END_PLAY")) {
            BEGIN_PLAY = false;
            printf("BEGIN_PLAY ended\n");
            continue;
        }

        //Store data into respective arrays
        if (BEGIN_SHOW_DATA) {
            strcpy(BEGIN_SHOW_DATA_ARRAY[Counter_SHOW_DATA], line);
            printf("Storing in BEGIN_SHOW_DATA_ARRAY[%d] = %s\n", Counter_SHOW_DATA, line);
            Counter_SHOW_DATA++;
        } else if (BEGIN_FIXTURE_MAPPING) {
            strcpy(BEGIN_FIXTURE_MAPPING_ARRAY[Counter_FIXTURE_MAPPING], line);
            printf("Storing in BEGIN_FIXTURE_MAPPING_ARRAY[%d] = %s\n", Counter_FIXTURE_MAPPING, line);
            Counter_FIXTURE_MAPPING++;
        } else if (BEGIN_CUES) {
            strcpy(BEGIN_CUES_ARRAY[Counter_CUES], line);
            printf("Storing in BEGIN_CUES_ARRAY[%d] = %s\n", Counter_CUES, line);
            Counter_CUES++;
        } else if (BEGIN_PLAY) {
            strcpy(BEGIN_PLAY_ARRAY[Counter_PLAY], line);
            printf("Storing in BEGIN_PLAY_ARRAY[%d] = %s\n", Counter_PLAY, line);
            Counter_PLAY++;
        }
    }

    //Close the file
    fclose(lfp);

    //Print stored data for each section
    printf("\nBEGIN_SHOW_DATA Section:\n");
    for (int i = 0; i < Counter_SHOW_DATA; i++) {
        printf("%s\n", BEGIN_SHOW_DATA_ARRAY[i]);
    }

    printf("\nBEGIN_FIXTURE_MAPPING Section:\n");
    for (int i = 0; i < Counter_FIXTURE_MAPPING; i++) {
        printf("%s\n", BEGIN_FIXTURE_MAPPING_ARRAY[i]);
    }

    printf("\nBEGIN_CUES Section:\n");
    for (int i = 0; i < Counter_CUES; i++) {
        printf("%s\n", BEGIN_CUES_ARRAY[i]);
    }

    printf("\nBEGIN_PLAY Section:\n");
    for (int i = 0; i < Counter_PLAY; i++) {
        printf("%s\n", BEGIN_PLAY_ARRAY[i]);
    }

    printf("\nFile reading completed.\n");


  //Show data
   char *PlayName = BEGIN_SHOW_DATA_ARRAY[0];
   char *date = BEGIN_SHOW_DATA_ARRAY[1];



   //mappping fixtures
       //2D array to store parsed numbers
    int parsed_fixture_mapping[MAX_FIXTURES][2];

    //Parse the fixture mapping array
    for (int i = 0; i < Counter_FIXTURE_MAPPING; i++) {
        char *line = BEGIN_FIXTURE_MAPPING_ARRAY[i];
        int fixture_number, address;

        //Extract numbers from the line
        sscanf(line, "FIXTURE %d %d", &fixture_number, &address);

        //Store the extracted values in the 2D array
        parsed_fixture_mapping[i][0] = fixture_number;
        parsed_fixture_mapping[i][1] = address;
    }

    //Print the 2D array
    printf("Parsed Fixture Mapping:\n");
    for (int i = 0; i < Counter_FIXTURE_MAPPING; i++) {
        printf("%d, %d\n", parsed_fixture_mapping[i][0], parsed_fixture_mapping[i][1]);
    }



    //Cue data
    int cueCount = 0;  //Number of cues detected
    int cueIndex = -1; //Current cue index
    int cues[MAX_CUES][MAX_FIXTURES][3]; //Stores integers for each cue
    int fixtureCounts[MAX_CUES] = {0}; //Number of fixtures in each cue

    //Parse the BEGIN_CUES_ARRAY
    for (int i = 0; i < sizeof(BEGIN_CUES_ARRAY) / MAX_STRING_LEN; i++) {
        char *line = (char *)BEGIN_CUES_ARRAY[i];

        if (strncmp(line, "BEGIN_CUE", 9) == 0) {
            //New cue detected
            sscanf(line, "BEGIN_CUE %d", &cueIndex);
            cueIndex--; //Adjust for 0-based indexing
            fixtureCounts[cueIndex] = 0; //Initialize fixture count for this cue
            cueCount++;
        } else if (strncmp(line, "FIXTURE", 7) == 0 && cueIndex >= 0) {
            //Parse a fixture line and store integers in the current cue's array
            int id, channel, value;
            sscanf(line, "FIXTURE %d %d %d", &id, &channel, &value);
            cues[cueIndex][fixtureCounts[cueIndex]][0] = id;
            cues[cueIndex][fixtureCounts[cueIndex]][1] = channel;
            cues[cueIndex][fixtureCounts[cueIndex]][2] = value;
            fixtureCounts[cueIndex]++;
        } else if (strncmp(line, "END_CUE", 7) == 0) {
            //Cue has ended
            cueIndex = -1;
        }
    }
    //Print parsed cues
    printf("Parsed Cues:\n");
    for (int i = 0; i < cueCount; i++) {
        printf("Cue %d:\n", i + 1);
        for (int j = 0; j < fixtureCounts[i]; j++) {
            printf("  %d, %d, %d\n", cues[i][j][0], cues[i][j][1], cues[i][j][2]);
        }
    } 

    if (cueCount==2){
      int cue1[MAX_FIXTURES][3];
      int cue2[MAX_FIXTURES][3];
      // if more cues add more arrays

      // Iterate over cues and split them into separate arrays
      for (int i = 0; i < cueCount; i++) {
        for (int j = 0; j < fixtureCounts[i]; j++) {
             if (i == 0) {
                 // Copy to cue1
                cue1[j][0] = cues[i][j][0];
                cue1[j][1] = cues[i][j][1];
                cue1[j][2] = cues[i][j][2];
            } else if (i == 1) {
                // Copy to cue2
                 cue2[j][0] = cues[i][j][0];
                 cue2[j][1] = cues[i][j][1];
                 cue2[j][2] = cues[i][j][2];
            }
            // if more cues add more else if (i == 1) {
                /* Copy to cueX
                 cueX[j][0] = cues[i][j][0];
                 cueX[j][1] = cues[i][j][1];
                 cueX[j][2] = cues[i][j][2];*/
          }
      }
      // Printing Cue 1
      printf("Cue 1:\n");
      for (int j = 0; j < fixtureCounts[0]; j++) { // Use fixtureCounts[0] for the number of fixtures in Cue 1
          printf("  Fixture ID: %d, Channel: %d, Value: %d\n", cue1[j][0], cue1[j][1], cue1[j][2]);
      }
      // Printing Cue 2
      printf("Cue 2:\n");
      for (int j = 0; j < fixtureCounts[1]; j++) { // Use fixtureCounts[1] for the number of fixtures in Cue 2
          printf("  Fixture ID: %d, Channel: %d, Value: %d\n", cue2[j][0], cue2[j][1], cue2[j][2]);
      }
    }
    return 0;
}


//Output
/*Read line: BEGIN_SHOW_DATA
BEGIN_SHOW_DATA started
Read line: The Great SCC Panto
Storing in BEGIN_SHOW_DATA_ARRAY[0] = The Great SCC Panto
Read line: 24.12.2024
Storing in BEGIN_SHOW_DATA_ARRAY[1] = 24.12.2024
Read line: END_SHOW_DATA
BEGIN_SHOW_DATA ended
Read line: BEGIN_FIXTURE_MAPPING
BEGIN_FIXTURE_MAPPING started
Read line: FIXTURE 1 27
Storing in BEGIN_FIXTURE_MAPPING_ARRAY[0] = FIXTURE 1 27
Read line: FIXTURE 2 37
Storing in BEGIN_FIXTURE_MAPPING_ARRAY[1] = FIXTURE 2 37
Read line: FIXTURE 3 47
Storing in BEGIN_FIXTURE_MAPPING_ARRAY[2] = FIXTURE 3 47
Read line: FIXTURE 4 57
Storing in BEGIN_FIXTURE_MAPPING_ARRAY[3] = FIXTURE 4 57
Read line: END_FIXTURE_MAPPING
BEGIN_FIXTURE_MAPPING ended
Read line: BEGIN_CUES
BEGIN_CUES started
Read line: BEGIN_CUE 1
Storing in BEGIN_CUES_ARRAY[0] = BEGIN_CUE 1
Read line: FIXTURE 1 1 255
Storing in BEGIN_CUES_ARRAY[1] = FIXTURE 1 1 255
Read line: FIXTURE 4 1 255
Storing in BEGIN_CUES_ARRAY[2] = FIXTURE 4 1 255
Read line: END_CUE
Storing in BEGIN_CUES_ARRAY[3] = END_CUE
Read line: BEGIN_CUE 2
Storing in BEGIN_CUES_ARRAY[4] = BEGIN_CUE 2
Read line: FIXTURE 1 1 255
Storing in BEGIN_CUES_ARRAY[5] = FIXTURE 1 1 255
Read line: FIXTURE 4 1 255
Storing in BEGIN_CUES_ARRAY[6] = FIXTURE 4 1 255
Read line: FIXTURE 1 2 255
Storing in BEGIN_CUES_ARRAY[7] = FIXTURE 1 2 255
Read line: FIXTURE 4 2 255
Storing in BEGIN_CUES_ARRAY[8] = FIXTURE 4 2 255
Read line: FIXTURE 1 2 0
Storing in BEGIN_CUES_ARRAY[9] = FIXTURE 1 2 0
Read line: FIXTURE 4 2 0
Storing in BEGIN_CUES_ARRAY[10] = FIXTURE 4 2 0
Read line: FIXTURE 1 3 255
Storing in BEGIN_CUES_ARRAY[11] = FIXTURE 1 3 255
Read line: FIXTURE 4 3 255
Storing in BEGIN_CUES_ARRAY[12] = FIXTURE 4 3 255
Read line: FIXTURE 1 3 0
Storing in BEGIN_CUES_ARRAY[13] = FIXTURE 1 3 0
Read line: FIXTURE 4 3 0
Storing in BEGIN_CUES_ARRAY[14] = FIXTURE 4 3 0
Read line: END_CUE
Storing in BEGIN_CUES_ARRAY[15] = END_CUE
Read line: END_CUES
BEGIN_CUES ended
Read line: BEGIN_PLAY
BEGIN_PLAY started
Read line: CUE 1 FADE FADE 1
Storing in BEGIN_PLAY_ARRAY[0] = CUE 1 FADE FADE 1
Read line: CUE 2 CUT CUT 3
Storing in BEGIN_PLAY_ARRAY[1] = CUE 2 CUT CUT 3
Read line: END_PLAY
BEGIN_PLAY ended

BEGIN_SHOW_DATA Section:
The Great SCC Panto
24.12.2024

BEGIN_FIXTURE_MAPPING Section:
FIXTURE 1 27
FIXTURE 2 37
FIXTURE 3 47
FIXTURE 4 57

BEGIN_CUES Section:
BEGIN_CUE 1
FIXTURE 1 1 255
FIXTURE 4 1 255
END_CUE
BEGIN_CUE 2
FIXTURE 1 1 255
FIXTURE 4 1 255
FIXTURE 1 2 255
FIXTURE 4 2 255
FIXTURE 1 2 0
FIXTURE 4 2 0
FIXTURE 1 3 255
FIXTURE 4 3 255
FIXTURE 1 3 0
FIXTURE 4 3 0
END_CUE

BEGIN_PLAY Section:
CUE 1 FADE FADE 1
CUE 2 CUT CUT 3

File reading completed.
Parsed Fixture Mapping:
1, 27
2, 37
3, 47
4, 57
Parsed Cues:
Cue 1:
  1, 1, 255
  4, 1, 255
Cue 2:
  1, 1, 255
  4, 1, 255
  1, 2, 255
  4, 2, 255
  1, 2, 0
  4, 2, 0
  1, 3, 255
  4, 3, 255
  1, 3, 0
  4, 3, 0
Cue 1:
  Fixture ID: 1, Channel: 1, Value: 255
  Fixture ID: 4, Channel: 1, Value: 255
Cue 2:
  Fixture ID: 1, Channel: 1, Value: 255
  Fixture ID: 4, Channel: 1, Value: 255
  Fixture ID: 1, Channel: 2, Value: 255
  Fixture ID: 4, Channel: 2, Value: 255
  Fixture ID: 1, Channel: 2, Value: 0
  Fixture ID: 4, Channel: 2, Value: 0
  Fixture ID: 1, Channel: 3, Value: 255
  Fixture ID: 4, Channel: 3, Value: 255
  Fixture ID: 1, Channel: 3, Value: 0
  Fixture ID: 4, Channel: 3, Value: 0*/
