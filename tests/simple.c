int main()
{
    complex comp = 3.2+8.2i;
    float p = 0.0;
    int i_var = 1;

    p = re(comp);
    while(p<10.0)
    {
        p = re(comp) + 1.0;
        sre(comp,p);
        print(p);
        print(" ");
    }


    print(" End ");
}