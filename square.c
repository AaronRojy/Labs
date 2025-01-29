#include <stdio.h>

int main() 
{
    int hollow = 0;
    printf("Do u want a whole square or a hollow square(1/2)\n");
    scanf("%d", &hollow);
    int square = 0;
    printf("how big do you want your square\n");
    scanf("%d", &square);
    if (hollow ==1) 
    {
        for (int i = 0; i < square; i++) 
        {
            for (int j = 0; j < square; j++) 
            {
                printf("*");
            }
            printf("\n");
        }
    }
    else if (hollow == 2) 
    {
        for (int i = 0; i < square; i++) 
        {
            for (int j = 0; j < square; j++) 
            {
                if (i == 0 || i == square - 1 || j == 0 || j == square - 1) 
                {
                    printf("*");
                }
                else 
                {
                    printf(" ");
                }
            }
            printf("\n");
        }   
    }


}