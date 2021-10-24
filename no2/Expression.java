package no2;

public class Expression {
	Element head = null;
	Element tail =null;
	Element result = new Element(0, 0, 1, ' ');
	
	public Expression(Element head,Element tail) {
		super();
		this.head = head;
		this.tail= tail;
	}

	//无括号情况下expression获得result
	
	public void getResult() {
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

			Element.Arithmetic(front, rear);
			rear = rear.nextElement;

		}

		result.wholeNumber = head.wholeNumber;
		result.numerator = head.numerator;
		result.denominator = head.denominator;
		result.operator = head.operator;
		result.nextElement = head.nextElement;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Element e1 = new Element(10, 0, 2, '/');
		Element e2 = new Element(2, 0, 8, '+');
		Element e3 = new Element(3, 0, 1, '*');
		Element e4 =new Element(6,0,1,' ');
		e1.nextElement=e2;
		e2.nextElement=e3;
		e3.nextElement=e4;
		Expression expression = new Expression(e1,e4);
		expression.getResult();
		expression.result.print();
		System.out.print(expression.result.operator);
	}
}
