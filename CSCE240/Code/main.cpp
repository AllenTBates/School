#include <iostream>

using namespace std;

class A {
public:
    int size;
    A ():size(0) {cout << "C" << endl;}
    A (A& a) :size(a.size) {cout << "P\n";}
    ~A() {cout << "D" << endl;}
};

A& foo(A arg1) {return arg1;}

int main() {
    int* a = new int[4];
    for (int i = 0; i != 4; i++) {a[i] = 3*i;}
    cout << ++a[0];
}
