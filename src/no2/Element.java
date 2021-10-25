package no2;

import no2.Element;

public class Element {
	int wholeNumber = 0;
	int numerator = 0;
	int denominator = 0;
	char operator = ' ';
	int flag = 0;
	Element nextElement = null;

	// ����������¹��캯��
	public Element(int wholeNumber, int numerator, int denominator, char operator) {
		super();
		this.wholeNumber = wholeNumber;
		this.numerator = numerator;
		this.denominator = denominator;
		this.operator = operator;
	}

	// ����������¹��캯��
	public Element(int wholeNumber, int numerator, int denominator, char operator, int flag) {
		super();
		this.wholeNumber = wholeNumber;
		this.numerator = numerator;
		this.denominator = denominator;
		this.operator = operator;
		this.flag = flag;
	}
	
	public Element copyElement() {
		return new Element(this.wholeNumber,this.numerator,this.denominator,this.operator);
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

	public static boolean Multipy(Element e1, Element e2) {
		if (e1.denominator == 0 || e2.denominator == 0)
			return false;
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
		return true;
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

	private String printValue() {

		if (wholeNumber == 0) {
			if (this.numerator == 0)
				return Integer.toString(0);
			return Integer.toString(numerator) + "/" + Integer.toString(this.denominator);
		}
		if (numerator == 0)
			return Integer.toString(wholeNumber);
		return Integer.toString(wholeNumber) + "'" + Integer.toString(numerator) + "/"
				+ Integer.toString(this.denominator);
	}

	private String printOperator() {
		if(this.operator!=' ')
			return " " + String.valueOf(this.operator) + " ";
		return "";
	}

	public String print() {

		if (flag < 0) {
			if (flag == -1)
				return "(" + this.printValue() + this.printOperator();
			return "(" + "(" + this.printValue() + this.printOperator();
		}
		if (flag > 0) {
			if (flag == 1)
				return this.printValue() + ")" + this.printOperator();
			return this.printValue() + ")" + ")" + this.printOperator();
		}
		return this.printValue() + this.printOperator();
	}

	public static boolean Arithmetic(Element e1, Element e2) {
		boolean b = true;
		if (e1.operator == '+')
			Add(e1, e2);
		else if (e1.operator == '-') {
			b = Sub(e1, e2);
		} else if (e1.operator == '*')
			b = Multipy(e1, e2);
		else if (e1.operator == '/')
			Divide(e1, e2);
		return b;
	}

	// �жϷ����Ƿ��Ǹ����ȼ�

	public boolean isPrioritized() {
		if (operator == '*' || operator == '/')
			return true;
		return false;
	}

	//
	public static Element ramdomElement(String type, int range) { // 0 <= ��� < range
		switch (type) {
		case "integer":
		case "INT": { // ��������
			if (range < 0) {
				return null;
			}
			int num = (int) (range * Math.random());
			return new Element(num, 0, 1, ' ');
		}
		case "true fraction":
		case "TF": { // ���������
			if (range <= 0) {
				return null;
			}
			while (true) {
				int denominator = (int) (range * Math.random() + 1);
				int numerator = (int) (range * Math.random());
				if (numerator < denominator)
					return new Element(0, numerator, denominator, ' ');
			}
		}
		case "band fracrion":
		case "BF": { // ���ɴ�����
			if (range <= 0) {
				return null;
			}
			while (true) {
				int num = (int) (range * Math.random());
				int denominator = (int) (range * Math.random() + 1);
				int numerator = (int) (range * Math.random());
				if (numerator < denominator)
					return new Element(num, numerator, denominator, ' ');
			}
		}
		default:
			return null;
		}
	}

	public void setOperator(char ch) {
		if (ch == '+' || ch == '-' || ch == '*') {
			this.operator = ch;
			return;
		} else if (ch == '/') {
			if (!(this.nextElement.wholeNumber == 0 && this.nextElement.numerator == 0)) {
				this.operator = ch;
				return;
			}
			this.operator = '+';

		}
	}

	public void randomOperator() {
		int num = (int) (4 * Math.random());
		switch (num) {
		case 0:
			this.setOperator('+');
			break;

		case 1:
			this.setOperator('-');
			break;

		case 2:
			this.setOperator('*');
			break;

		case 3:
			this.setOperator('/');
			break;

		default: {
			this.setOperator('+');
		}
		}

		return;
	}

	// �Ƚ�����Ԫ�ص�ֵ�����Ƿ����

	public boolean isValueSame(Element e) {
		if (e.numerator == 0 && this.numerator == 0) {
			if (e.wholeNumber == this.wholeNumber)
				return true;
			return false;
		}
		if (e.numerator != 0 && this.numerator != 0) {
			if (e.numerator == this.numerator && e.wholeNumber == this.wholeNumber && e.denominator == this.denominator)
				return true;
			return false;
		}
		return false;
	}

	// �Ƚ�����Ԫ�صĲ������Ƿ����

	public boolean isOperatorSame(Element e) {
		if (this.operator == e.operator)
			return true;
		return false;
	}

	public void copyElement(Element e) {
		this.wholeNumber = e.wholeNumber;
		this.numerator = e.numerator;
		this.denominator = e.denominator;
		this.nextElement = e.nextElement;
	}
}
