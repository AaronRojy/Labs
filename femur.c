#include <stdio.h>

int main() 
{
    int femur = 0;
    printf("how long is your femur in CM\n");
    scanf("%d", &femur);
    printf("Your femur length is: %d \n", femur);
    int estimatedHight = 0;
    estimatedHight = (femur * 3)+ 61;
    printf("Your estimated hight is %dcm \n", estimatedHight);
}
