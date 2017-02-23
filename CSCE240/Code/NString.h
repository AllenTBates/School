#include <string.h>

//typedef unsigned int size_t;

class NString {
    private:
        size_t len;
        size_t size;
        char* data;

    public:
        NString();
        NString(const NString& a);
        NString(const char* zstr);
        NString(const char* str, size_t n);
        ~NString();

        NString& operator=(const NString& right);
        NString& operator=(const char c);
        NString& operator+=(const NString& right);
        NString& operator+=(const char c);
        NString& operator+=(const char* zstr);

        size_t Put(const char c);
};
