bool operator>=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    if (lhs > rhs || lhs == rhs)
        return true;
    return false;
}

friend operator<(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    if (!(lhs >= rhs))
        return true;
    return false;
}

friend operator<=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    if (lhs < rhs || lhs == rhs)
        return true;
    return false;
}

friend operator!=(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    if (!(lhs == rhs))
        return true;
    return false;
}

bool operator!() {
    if 'this' is 0, than return true
    else return false
}

UnsignedLargeInt operator+(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    // Setup the result
    UnsignedLargeInt result;
    int result_length = get_result_array_length(lhs.length, rhs.length);
    result.digits = new char[result_length];

    int carry = 0;
    // iterate over both
    for (int i = 0; i < result_length; i++)
        // make sure value exists, if not set lhs_val to 0
        // get value at lhs, lhs_val

        // make sure value exists, if not set rhs_val to 0
        // get value at rhs, rhs_val

        // convert values to ints

        // add lhs_val, rhs_val, and the carry ==> numb

        // calculate new carry
        if (numb >= 10) {
            carry = 1;
            numb %= 10
        }
        else
            carry = 0;

        // convert back to char

        // put it into result arr
}

UnsignedLargeInt operator*(const UnsignedLargeInt& lhs, const UnsignedLargeInt& rhs) {
    // Setup the result_arr
    int result_arr_length = /* number of digits in rhs */
    UnsignedLargeInt result_arr[result_arr_length];

    for (int i = 0; i < rhs.length; i++) { // rhs
        int carry = 0;
        UnsignedLargeInt current;
        // size of current arr is lhs_length + rhs_length
        for (int j = 0; j < lhs.length; j++) { // lhs
            // get lhs and rhs vals
            // convert vals to int
            // multiply vals together
            // factor in the carry
            // put in result_val

            // calculate new carry
            int ones = result_val%10;
            int tens = result_val/10;
            carry = tens;

            // convert ones to char
            // put into array
        }
        // put current in result_arr
    }

    UnsignedLargeInt result;
    // iterate over result_arr
    for (int i...) {
        result += result_arr[i];
    }

    return result;
}


ostream& operator<<(ostream& output, const UnsignedLargeInt& rhs) {
    // iterate over the rhs
    output << //each element
    return output;
}

int convert_to_int(char c) {
    return c - 48;
}

char convert_to_char(int i) {
    return (int)(i + 48);
}

int get_result_array_length(int lhs_length, int rhs_length) {
    if ()// check if lhs > rhs
        //return lhs + 1
    else  // happens if rhs >= lhs
        // return rhs + 1
}
