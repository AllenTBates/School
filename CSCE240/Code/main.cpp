#include <iostream>

using namespace std;

int b;

class TenMatrix {
	int matrix[10][10];

	public:
	TenMatrix();
	~TenMatrix();
	TenMatrix& operator+(int i);
	bool operator==(TenMatrix& comp);
	void printM();
};

TenMatrix::TenMatrix() {
    for (int i = 0; i != 10; i++)
        for (int j = 0; j != 10; j++)
            matrix[i][j] = 0;
}

TenMatrix::~TenMatrix() {}

TenMatrix& TenMatrix::operator+(int a) {
    for (int i = 0; i != 10; i++)
        for (int j = 0; j != 10; j++)
            matrix[i][j] += a;
    return *this;
}

bool TenMatrix::operator==(TenMatrix& comp) {
    for (int i = 0; i != 10; i++)
        for (int j = 0; j != 10; j++)
            if (matrix[i][j] != comp.matrix[i][j])
                return false;
    return true;
}

void TenMatrix::printM() {
    for (int i = 0; i != 10; i++) {
        for (int j = 0; j != 10; j++)
            cout << matrix[i][j] << " ";
        cout << endl;
    }
}

int main() {
    TenMatrix tm;
    TenMatrix tm2;
    if (tm == tm2)
        cout << "AHHH";
}
