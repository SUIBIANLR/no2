package no2;

public class Element {
	int wholeNumber = 0;
	int numerator = 0;
	int denominator = 0;
	char operator = ' ';
	int flag;
	Element nextElement = null;

	public Element(int wholeNumber, int numerator, int denominator, char operator) {
		super();
		this.wholeNumber = wholeNumber;
		this.numerator = numerator;
		this.denominator = denominator;
		this.operator = operator;
	}

	// Ԫ�ؼӷ�,�������e1��

	public static void Add(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0)// ���������־�Ϊ0��ֻ��������������㼴��
			e1.wholeNumber += e2.wholeNumber;
		else {
			e1.wholeNumber += e2.wholeNumber;// ����������������ӣ�����e1����������
			int lcm = Lcm(e1.denominator, e2.denominator);// ����С������
			e1.numerator *= lcm / e1.denominator;// ��e1�ķ��ӽ��б任
			e2.numerator *= lcm / e2.denominator;// ��e2�ķ��ӽ��б任
			e1.denominator = lcm;// ��ĸ������õ���С������
			e1.numerator += e2.numerator;// e1��e2������ӣ������e1�ķ��Ӵ�

			// ���ٷ������ɴ�����(Ҳ��ֹ����e1��ֵΪʮ��������֮��������ʮ�˵����)

			e1.wholeNumber += e1.numerator / e1.denominator;
			e1.numerator = e1.numerator % e1.denominator;

			// ���ս��ҪԼ��

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		}
		e1.operator = e2.operator;// ���������Ѿ���ɣ���e2���������ֵ��e1�������
		e1.nextElement = e2.nextElement;// �޸Ľ���ϵ
	}

	// Ԫ�ؼ���,����ֻ��Ԫ�ؼ�����������������һ���и�����return false

	public static boolean Sub(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0) {// ���������־�Ϊ0��ֻ��������������㼴��
			e1.wholeNumber -= e2.wholeNumber;
			if (e1.wholeNumber < 0)
				return false;
		} else {
			if (e1.wholeNumber < e2.wholeNumber)
				return false;// ����������־Ͳ�������return false
			e1.numerator += e1.wholeNumber * e1.denominator;// ��������ת��Ϊ�ٷ���
			e2.numerator += e2.wholeNumber * e2.denominator;// ��������ת��Ϊ�ٷ���
			int lcm = Lcm(e1.denominator, e2.denominator);// ����С������
			e1.numerator *= lcm / e1.denominator;// ��e1�ķ��ӽ��б任
			e2.numerator *= lcm / e2.denominator;// ��e2�ķ��ӽ��б任
			e1.denominator = lcm;// ��ĸ������õ���С������
			e1.numerator -= e2.numerator;
			if (e1.numerator < 0)
				return false;

			// ���ٷ������ɴ�����

			e1.wholeNumber = e1.numerator / e1.denominator;
			e1.numerator = e1.numerator % e1.denominator;

			// ���ս��ҪԼ��

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		} // e1��e2��������������e1�ķ��Ӵ�
		e1.operator = e2.operator;// ���������Ѿ���ɣ���e2���������ֵ��e1�������
		e1.nextElement = e2.nextElement;// �޸Ľ���ϵ
		return true;
	}

	// Ԫ�س˷����������e1��

	public static void Multipy(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0)// ���������־�Ϊ0��ֻ��������������㼴��
			e1.wholeNumber *= e2.wholeNumber;
		else {
			e1.numerator = (e1.wholeNumber * e1.denominator + e1.numerator)
					* (e2.wholeNumber * e2.denominator + e2.numerator);
			e1.denominator = e1.denominator * e2.denominator;
			e1.wholeNumber = e1.numerator / e1.denominator;// ���ٷ������ɴ�����
			e1.numerator = e1.numerator % e1.denominator;

			// ���ս��ҪԼ��

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		}
		e1.operator = e2.operator;
		e1.nextElement = e2.nextElement;
	}

	// Ԫ�س���

	public static void Divide(Element e1, Element e2) {
		e1.numerator += e1.wholeNumber * e1.denominator;// ��������ת��Ϊ�ٷ���
		e2.numerator += e2.wholeNumber * e2.denominator;// ��������ת��Ϊ�ٷ���

		// ����������ת��Ϊ�˷�����

		e1.wholeNumber = e2.wholeNumber = 0;
		int temp = e2.numerator;
		e2.numerator = e2.denominator;
		e2.denominator = temp;
		Multipy(e1, e2);
	}
	// ��������㷨

	public static int Gcd(int a, int b) {
		if (b == 0)
			return a;
		return Gcd(b, a % b);
	}

	// ��С�������㷨

	public static int Lcm(int a, int b) {
		return a * b / Gcd(a, b);
	}

	public void print() {
		if (wholeNumber == 0)
			if (numerator == 0)
				System.out.print(0);
			else
				System.out.print(numerator + "/" + denominator);
		else if (numerator == 0)
			System.out.print(wholeNumber);
		else
			System.out.print(wholeNumber + "'" + numerator + "/" + denominator);
	}

	public static void Arithmetic(Element e1, Element e2) {
		if (e1.operator == '+')
			Add(e1, e2);
		if (e1.operator == '-')
			Sub(e1, e2);
		if (e1.operator == '*')
			Multipy(e1, e2);
		if (e1.operator == '/')
			Divide(e1, e2);
	}
	
	public boolean isPrioritized(){
		if(operator=='*'||operator=='/')return true;
		return false;
	}
}
