int func1(int arg,int arg2,int arg3)
{
	arg = arg * 2;
	return arg;
}

int main()
{
	int a;
	int b;
	a = 5;
	b = func1(a,3,2+2);
	a = b;

	b = 5 * 2 + 4 * 3;


	return 0;
}