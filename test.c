
void func_without_args()
{
}

int func_with_one_arg(int arg)
{
    int a = arg*2;
    return a;
}

int func_with_two_args(int arg1,int arg2)
{
    int a = arg1*123+arg2*987;
    return a;
}

int main()
{
    int i;
    int abc;
    (true) ? (i = 1) : (i = 2) ;

    if(i==1)
    {
        abc = 123;
        abc = abc + 333;
    }
    else
    {
    }

    while(abc>100)
    {
        abc--;
    }

    for(i=0;i<100;i++)
    {
        abc++;
    }

    return 0;
}