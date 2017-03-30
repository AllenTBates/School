#ifndef UNSIGNED_LARGE_INT_H
#define UNSIGNED_LARGE_INT_H

class UnsignedLargeInt {
private:
    char* digits;

public:
    UnsignedLargeInt();
    UnsignedLargeInt(unsigned long long src);
    UnsignedLargeInt(UnsignedLargeInt& src);
    ~UnsignedLargeInt();

    UnsignedLargeInt& operator+=(const UnsignedLargeInt& rhs);
    UnsignedLargeInt& operator*=(const UnsignedLargeInt& rhs);
    UnsignedLargeInt operator++(); // pre-incremenet
    UnsignedLargeInt operator++(int IGNORE_ME); // post-increment
    bool operator!();

    friend UnsignedLargeInt operator+(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend UnsignedLargeInt operator*(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator==(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator!=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator<=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator>=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator<(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend bool operator>(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs);
    friend ostream& operator<<(ostream& output, const UnsignedLargeInt& rhs);
};

#endif // UNSIGNED_LARGE_INT_H
