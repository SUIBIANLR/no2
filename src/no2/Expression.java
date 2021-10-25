package no2;

import no2.Element;
import no2.Expression;
import no2.Main;

public class Expression {
	Element head = null;
	Element tail = null;
	Element result = new Element(0, 0, 1, ' ');

	public Expression(Element head, Element tail) {
		super();
		this.head = head;
		this.tail = tail;
	}

	public Expression() {

	}

	public boolean getRealResult() {

		// �����ʽ�������ţ���ô���������Ż��result����

		if (!this.haveParentheses()) {
			if (getResult())
				return true;
			return false;
		}

		// �������ʽ�������ţ���ô��ȥһ�����ţ��ٵݹ�����Լ�����
		Element front = head;
		Element rear = front.nextElement;
		while (!(front.flag < 0)) {
			front = front.nextElement;
		}
		rear = front.nextElement;
		while (!(rear.flag > 0)) {
			rear = rear.nextElement;
		}

		// ����һ��������frontָ�������ţ�rearָ����������
		front.flag += 1;
		rear.flag -= 1;
		Expression exp = new Expression(front, rear);
		if (!exp.getRealResult())
			return false;
		if (!this.getRealResult())
			return false;
		return true;
	}

	// �����������expression���result

	private boolean getResult() {
		Element front = head;
		Element rear = head.nextElement;

		// ��ɳ˳�һ��������

		while (!(rear == tail.nextElement)) {
			if (front.isPrioritized()) {
				Element.Arithmetic(front, rear);
				rear = rear.nextElement;
			} else {
				front = front.nextElement;
				rear = rear.nextElement;
			}
		}

		// front��rear�ٴθ�λ����ɼӼ�һ��������

		front = head;
		rear = head.nextElement;

		while (!(rear == tail.nextElement)) {

			boolean b = Element.Arithmetic(front, rear);
			rear = rear.nextElement;
			if (b == false)
				return false;
		}

		result.wholeNumber = head.wholeNumber;
		result.numerator = head.numerator;
		result.denominator = head.denominator;
		result.operator = head.operator;
		result.nextElement = head.nextElement;
		return true;
	}

	@SuppressWarnings("null")
	public boolean isAllValueSame(Expression exp) {

		// �����ڱ���ʽ�е�ÿһ��Ԫ���Ƿ���exp�У������һ��Ԫ�ز���������return false

		for (Element p = head; p == null; p = p.nextElement)
			if (!exp.haveItValue(p))
				return false;

		// ������exp��ÿһ��Ԫ���Ƿ��ڱ���ʽ�У������һ��Ԫ�ز���������return false
		for (Element q = exp.head; q == null; q = q.nextElement)
			if (!this.haveItValue(q))
				return false;
		return true;
	}

	@SuppressWarnings("null")
	public boolean isAllOperatorSame(Expression exp) {

		// �����ڱ���ʽ�е�ÿһ��Ԫ���Ƿ���exp�У������һ��Ԫ�ز���������return false

		for (Element p = head; p == null; p = p.nextElement)
			if (!exp.haveItOperator(p))
				return false;

		// ������exp��ÿһ��Ԫ���Ƿ��ڱ���ʽ�У������һ��Ԫ�ز���������return false
		for (Element q = exp.head; q == null; q = q.nextElement)
			if (!this.haveItOperator(q))
				return false;
		return true;
	}

	public boolean haveItValue(Element e) {
		for (Element p = head; p != null; p = p.nextElement)
			if (p.isValueSame(e))
				return true;// �������ʽ����һ��Ԫ����e��ͬ��˵��e�ڱ���ʽ��
		return false;
	}

	public boolean haveItOperator(Element e) {
		for (Element p = head; p != null; p = p.nextElement)
			if (p.isOperatorSame(e))
				return true;// �������ʽ����һ��Ԫ����e��ͬ��˵��e�ڱ���ʽ��
		return false;
	}

	public boolean isSameResult(Expression exp) {
		if (this.result.wholeNumber == exp.result.wholeNumber && this.result.numerator == exp.result.numerator
				&& this.result.denominator == exp.result.denominator)
			return true;
		return false;
	}

	public boolean haveParentheses() {
		int left = 0;
		int right = 0;
		Element p = head;
		while (p != tail.nextElement) {
			if (p.flag < 0)
				left += Math.abs(p.flag);
			else if (p.flag > 0)
				right += Math.abs(p.flag);
			p = p.nextElement;
		}
		// ֻ�������ź������ŵĸ�������Ϊ0���Ҹ������ʱ������Ϊ������
		if (left > 0 && right > 0 && left == right)
			return true;
		return false;
	}

	// �������һ����ʽ

	public static Expression GetExpression(int amountOfElement, int range) {
		// amountOfElementָ����������ʽ���м�������ӣ�range�����ķ�Χ
		switch (amountOfElement) {
		case 2:
			return GetExpressionOf_2_Element();
		case 3:
			return GetExpressionOf_3_Element();
		case 4:
			return GetExpressionOf_4_Element();
		default:
			return null;
		}
	}

	private static Expression GetExpressionOf_2_Element() {
		// ��������element
		Element e1 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e2 = Element.ramdomElement(GetRandomMode(), Main.range);
		e1.nextElement = e2;// ����element
		e1.randomOperator();// e1��������������
		return new Expression(e1, e2);// �����e1Ϊhead��e2Ϊtail����ʽ
	}

	private static Expression GetExpressionOf_3_Element() {
		Element e1 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e2 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e3 = Element.ramdomElement(GetRandomMode(), Main.range);
		e1.nextElement = e2;
		e2.nextElement = e3;
		e1.randomOperator();
		e2.randomOperator();
		Expression expression = new Expression(e1, e3);
		Element front = expression.head;
		Element rear = expression.head.nextElement;
		if (Main.randomInt(0, 1) == 1) {
			expression.setParentheses(front, rear);// ��������1���ͼ�����
		} else if (Main.randomInt(0, 1) == 1) {// �����������1�����ں�����element������
			front = front.nextElement;
			rear = rear.nextElement;
			expression.setParentheses(front, rear);
		}
		return expression;
	}

	private void setParentheses(Element front, Element rear) {
		front.flag -= 1;
		rear.flag += 1;
	}

	private static Expression GetExpressionOf_4_Element() {
		Element e1 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e2 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e3 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e4 = Element.ramdomElement(GetRandomMode(), Main.range);
		e1.nextElement = e2;
		e2.nextElement = e3;
		e3.nextElement = e4;
		e1.randomOperator();
		e2.randomOperator();
		e3.randomOperator();
		Expression expression = new Expression(e1, e4);
		Element front = expression.head;
		Element rear = expression.head.nextElement;
		if (Main.randomInt(0, 1) == 1) {// ��������1����set
			expression.setParentheses(front, rear);
			if (Main.randomInt(0, 1) == 1) {// �����set��������������1����rear������setһ��
				rear = rear.nextElement;
				expression.setParentheses(front, rear);
			}
		} else if (Main.randomInt(0, 1) == 1) {// ������ϴ�ûset������������1����front��rear�����ƺ���setһ��
			front = front.nextElement;
			rear = rear.nextElement;
			expression.setParentheses(front, rear);
			if (Main.randomInt(0, 1) == 1) {// �����set��������������1����rear������setһ��
				rear = rear.nextElement;
				expression.setParentheses(front, rear);
			}
		}
		return expression;
	}

	// �����"INT"��"TF"��"BF"��ѡһ������
	private static String GetRandomMode() {
		int randomNumber = (int) (3 * Math.random());
		String str = null;
		switch (randomNumber) {
		case 0: {
			str = "INT";
			break;
		}
		case 1: {
			str = "TF";
			break;
		}
		case 2: {
			str = "BF";
			break;
		}
		default:
		}
		return str;
	}

	public String printResult() {
		String str = null;
		str = "=" + " " + this.result.print();
		return str;
	}

	public String printExpression() {
		String str = "";
		Element p = head;
		while (!(p == tail.nextElement)) {
			str += p.print();
			p = p.nextElement;
		}
		return str;
	}

}
