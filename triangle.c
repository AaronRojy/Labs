#include <stdio.h>

int main()
{
    int rows = 0;
    printf("how many rows do u want in your triangle\n");
    scanf("%d", &rows);
    int array[rows][rows];

    for (int i = 0; i < rows; i++) 
    {
        for (int j = 0; j <rows; j++) 
        {
            array[i][j] == i+j;
        }
    }

    for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < rows; j++) 
            {
                printf("%d", &array[i][j]);

            }
            printf("\n");
        }

    printf("\n");

    for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < rows; j++) 
            {
                printf("%d\n",i);
                printf("%d\n",j);
                
            }
            printf("\n");
        }

}