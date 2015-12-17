int main()
{
    int s = 0;
    bool a = true;
    bool b = false;
    bool c;

    c = a || b;
    c = a && b;
    s++;

//    a = !a;
    a = c == b;
    b = 1 >= 2;
    b = 3 <= 6;
    c = a != b;


}