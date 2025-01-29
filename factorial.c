#include <stdio.h>

int main() {
    int result = factorial(5);
    int result2 = sum(5);
    printf("%d", result);
    printf("%d", result2);
}

int factorial(int a) {
        if (a < 0){
            return a* factorial(a-1) ; 
        }
        return a;
    } 
    int sum(int b) {
        if (b < 0){
            return b + sum(b-1) ; 
        }
        return b;
    } 
