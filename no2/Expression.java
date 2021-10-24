package no2;

import java.util.ArrayList;

public class Expression {
	Element head = null;
	Element tail = null;
	Element result = new Element(0, 0, 1, ' ');

	public Expression(Element head, Element tail) {
		super();
		this.head = head;
		this.tail = tail;
	}

	// 无括号情况下expression获得result

	public boolean getResult() {
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

//	private void RemoveParentheses() {
//
//		
//		while(true) {
//			Element front =head;
//			Element rear= head;
//		while(front.flag==0) {
//			front=front.nextElement;
//		}
//		rear=front.nextElement;
//		while(rear.flag==0) {
//			rear=rear.nextElement;
//		}
//		if(front.nextElement==rear) {
//			front.flag++;
//			rear.flag--;
//			Element.Arithmetic(front,rear);
//		}else {
//			front=front.nextElement;
//			Element.Arithmetic(front,rear);
//		}
//		if(!(front.nextElement==null)) {
//			front=front.nextElement;
//			
//		}else if(rear.flag!=0) {
//			rear=front;
//			front=head;
//			while(front.flag==0) {
//				front=front.nextElement;
//			}
//			front.flag++;
//			rear.flag--;
//			Element.Arithmetic(front,rear);
//			break;
//		}else break;
//		}
//		
//	}

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Element e1 = Element.ramdomElement("INT", 5);
		Element e2 = Element.ramdomElement("TF", 5);
		Element e3 = Element.ramdomElement("BF", 5);
		Element e4 = Element.ramdomElement("INT", 5);
		e1.nextElement = e2;
		e2.nextElement = e3;
		e3.nextElement = e4;
		e1.randomOperator();
		e2.randomOperator();
		e3.randomOperator();
		e1.printAll();
		e2.printAll();
		e3.printAll();
		e4.print();
		Expression expression = new Expression(e1, e4);
		if (expression.getResult()) {
			System.out.println("");
			System.out.println("运算结果为：");
			expression.result.print();
		} else
			System.out.println("该算式不符合规范");

//		Element e1=new Element(1,0,1,'*');
//		Element e2=new Element(0,1,2,'*');
//		Element e3=new Element(4,4,5,'-');
//		Element e4=new Element(3,0,1,' ');
//		e1.nextElement=e2;
//		e2.nextElement=e3;
//		e3.nextElement=e4;
//		Expression expression =new Expression(e1,e4);
//		if(expression.getResult())
//			expression.result.print();
//		else System.out.println("算式不符合要求");
	}
}
