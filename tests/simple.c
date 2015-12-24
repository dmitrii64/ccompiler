int main()
{
    complex comp = 3.2+8.2i;
    complex comp2 = 2.7+2.3i;
    complex comp3 = 2.1+3.4i;
    long z = 123456;
    long y = 654321;
    long zpy;
    int i = 0;
    int j = 0;
    float p;

    print("(");
    p = re(comp);
    print(p);
    print("+");
    p = im(comp);
    print(p);
    print("i) + ");

    print("(");
    p = re(comp2);
    print(p);
    print("+");
    p = im(comp2);
    print(p);
    print("i) = ");

    comp3 = comp + comp2;
    print("(");
    p = re(comp3);
    print(p);
    print("+");
    p = im(comp3);
    print(p);
    print("i)");

    print(" Z = ");
    print(z);
    print(" Y = ");
    print(y);
    print(" Z + Y = ");
    zpy = z + y;
    print(zpy);

    print(" Cycle:");
    while (i < 3)
    {
        j=0;
        while(j < 3)
        {
            j++;
            print("j");
        }
        i++;
        print("i");
    }

    print(" IF TEST: ");
    if(zpy == 777777)
    {
        print("TRUE");
    }
    else
    {
        print("FALSE");
    }



    print(" End ");
}