#include <iostream>

using namespace std;

class test{
	public:
	int count;
	char c;
	test() {count = 0; c = ' ';}
	test(int i, char j) {count = i; c = j;}
	char* concat();
};
int main() {
	test a;
	a.count = 5;
	a.c = 'b';
	char* str = a.concat();
	cout << str;
	delete[] str;
}
char* test::concat() {
	char* str = new char[count+1];
	for (int i = 0; i != count; i++)
		str[i] = c;
	str[count] = '\0';
	return str;
}


