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

		// 如果算式中无括号，那么调用无括号获得result方法

		if (!this.haveParentheses()) {
			if (getResult())
				return true;
			return false;
		}

		// 如果是算式中有括号，那么削去一层括号，再递归调用自己算结果
		Element front = head;
		Element rear = front.nextElement;
		while (!(front.flag < 0)) {
			front = front.nextElement;
		}
		rear = front.nextElement;
		while (!(rear.flag > 0)) {
			rear = rear.nextElement;
		}

		// 以上一套下来后，front指向左括号，rear指向了右括号
		front.flag += 1;
		rear.flag -= 1;
		Expression exp = new Expression(front, rear);
		if (!exp.getRealResult())
			return false;
		if (!this.getRealResult())
			return false;
		return true;
	}

	// 无括号情况下expression获得result

	private boolean getResult() {
		Element front = head;
		Element rear = head.nextElement;

		// 完成乘除一级的运算

		while (!(rear == tail.nextElement)) {
			if (front.isPrioritized()) {
				Element.Arithmetic(front, rear);
				rear = rear.nextElement;
			} else {
				front = front.nextElement;
				rear = rear.nextElement;
			}
		}

		// front和rear再次复位，完成加减一级的运算

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

		// 看对于本算式中的每一个元素是否都在exp中，如果有一个元素不在里面则return false

		for (Element p = head; p == null; p = p.nextElement)
			if (!exp.haveItValue(p))
				return false;

		// 看对于exp中每一个元素是否都在本算式中，如果有一个元素不在里面则return false
		for (Element q = exp.head; q == null; q = q.nextElement)
			if (!this.haveItValue(q))
				return false;
		return true;
	}

	@SuppressWarnings("null")
	public boolean isAllOperatorSame(Expression exp) {

		// 看对于本算式中的每一个元素是否都在exp中，如果有一个元素不在里面则return false

		for (Element p = head; p == null; p = p.nextElement)
			if (!exp.haveItOperator(p))
				return false;

		// 看对于exp中每一个元素是否都在本算式中，如果有一个元素不在里面则return false
		for (Element q = exp.head; q == null; q = q.nextElement)
			if (!this.haveItOperator(q))
				return false;
		return true;
	}

	public boolean haveItValue(Element e) {
		for (Element p = head; p != null; p = p.nextElement)
			if (p.isValueSame(e))
				return true;// 如果本算式中有一个元素与e相同，说明e在本算式中
		return false;
	}

	public boolean haveItOperator(Element e) {
		for (Element p = head; p != null; p = p.nextElement)
			if (p.isOperatorSame(e))
				return true;// 如果本算式中有一个元素与e相同，说明e在本算式中
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
		// 只有左括号和右括号的个数都不为0，且个数相等时，才认为有括号
		if (left > 0 && right > 0 && left == right)
			return true;
		return false;
	}

	// 随机生成一个算式

	public static Expression GetExpression(int amountOfElement, int range) {
		// amountOfElement指的是生成算式中有几个数相加，range是数的范围
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
		// 生成两个element
		Element e1 = Element.ramdomElement(GetRandomMode(), Main.range);
		Element e2 = Element.ramdomElement(GetRandomMode(), Main.range);
		e1.nextElement = e2;// 链接element
		e1.randomOperator();// e1生成随机的运算符
		return new Expression(e1, e2);// 输出以e1为head，e2为tail的算式
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
			expression.setParentheses(front, rear);// 如果随机到1，就加括号
		} else if (Main.randomInt(0, 1) == 1) {// 如果这次随机到1，就在后两个element加括号
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
		if (Main.randomInt(0, 1) == 1) {// 如果随机到1，则set
			expression.setParentheses(front, rear);
			if (Main.randomInt(0, 1) == 1) {// 如果在set的情况下又随机到1，则rear后移再set一次
				rear = rear.nextElement;
				expression.setParentheses(front, rear);
			}
		} else if (Main.randomInt(0, 1) == 1) {// 如果在上次没set的情况下随机到1，则front和rear都后移后再set一次
			front = front.nextElement;
			rear = rear.nextElement;
			expression.setParentheses(front, rear);
			if (Main.randomInt(0, 1) == 1) {// 如果在set的情况下又随机到1，则rear后移再set一次
				rear = rear.nextElement;
				expression.setParentheses(front, rear);
			}
		}
		return expression;
	}

	// 随机从"INT"、"TF"、"BF"中选一个返回
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
