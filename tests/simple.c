int main()
{
    complex comp = 2.1+2.2i;
    int a = 2;
    int b = 5;
    int c;
    float p;

    p = re(comp);
    c = (-a + b) * (1 + 1);

    print("Begin");
    while(c<10)
    {
        c++;
        print(" С=");
        print(c);

    }
    print(" End");
}