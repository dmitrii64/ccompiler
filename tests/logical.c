int main()
{
    bool a = true;
    bool b = false;
    bool c;

    c = a || b;
    c = a && b;
    a = !a;
    a = c == b;
    b = 1 >= 2;
    b = 3 <= 6;
    c = a != b;

}