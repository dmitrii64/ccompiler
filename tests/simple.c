int main()
{
    complex comp = 3.2+8.2i;
    complex comp2 = 2.7+2.3i;
    complex comp3 = 2.1+3.4i;

    float p;

    comp3 = comp + comp2;


    print("RE: ");
    p = re(comp3);
    print(p);
    print(" IM: ");
    p = im(comp3);
    print(p);

    print(" End");
}