#include "NString.h"

NString::NString() {
    len = 0;
    size = 1;
    data = new char[1];
}

NString::NString(const NString& a) {
    len = a.len;
    size = a.size;
    data = new char[size];
    memcpy(data,a.data,len);
}

NString::NString(const char* zstr) {
    len = strlen(zstr);
    size = 2*len+1;
    memcpy(data,zstr,len);
}

NString::NString(const char* zstr, size_t n) {
    int len = n;
    if (n > strlen(zstr))
        len = strlen(zstr);
    size = 2*len+1;
    memcpy(data,zstr,len);
}

NString::~NString() {
    delete[] data;
}

NString& NString::operator=(const NString& right) {
    return *(new NString(right));
}

NString& NString::operator=(const char c) {
    data = new char[2];
    len = 1;
    size = 2;
    data[0] = c;
    return *this;
}

NString& NString::operator+=(const NString& right) {
    int oriLen = len;
    if (len + strlen(right.data) <= size) {
        len = len + strlen(right.data);
    } else {
        char* original = data;
        size = 2*(len + strlen(right.data)) + 1;
        data = new char[size];
        memcpy(data, original, len);
        len = len + strlen(right.data);
        delete original;
    }
    for (int i = oriLen; i < len; i++) {
        data[i] = right.data[i-oriLen];
    }
    return *this;
}

NString& NString::operator+=(const char c) {
    Put(c);
    return *this;
}

NString& NString::operator+=(const char* zstr) {
    for (int i=0; i<strlen(zstr); i++)
        Put(zstr[i]);
    return *this;
}

size_t NString::Put(const char c) {
    if (len + 1 <= size) {
        len = len + 1;
    } else {
        char* original = data;
        size = 2*(len + 1) + 1;
        data = new char[size];
        memcpy(data, original, len);
        len = len + 1;
        delete original;
    }
    len = len + 1;
    data[len-1] = c;
    return len;
}
