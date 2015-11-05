
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
    a = 1;
    a--;
    --a;
    return a;
}

int main()
{
    int i;
    int abc;
    int arr[10];

    func_with_two_args(1,2);
    i--;
    --i;

    return 0;
}