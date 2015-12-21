int main()
{
    int s = 0;
    bool a = true;
    bool b = false;
    bool c;

    c = a || b;
    c = a && b;
    s++;


    a = (c == b) ? 2 : 1;
    a = (true) ? 2 : 1;
    a = (c != b) ? 2 : 1;
    a = (2 < 5) ? 2 : 1;
    a = (2 == 2) ? 2 : 1;
    a = ( 2 == 4 ) ? 2 : 1;

    s = (2 + 2)*2;

    return 0;
}