int func1(int arg)
{
	arg = arg * 2;
	return arg;
}

int main()
{
	int a;
	int b;
	a = 5;
	b = func1(a);
	a = b;
	return 0;
}