package no2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Expression {
	Element head = null;
	Element result = new Element(0,0,1,' ');
	
	public void getResult(){
		RemoveParentheses();
		getResultInNoParentheses();
	}
	
	public void getResultInNoParentheses() {
		Element front = head;
		Element rear = head.nextElement;
		
		//完成乘除一级的运算
		
		while(!(rear==null)) {
			if(front.isPrioritized())
				{Element.Arithmetic(front,rear);
				rear=rear.nextElement;
				}
			else {
				front=front.nextElement;
				rear=rear.nextElement;
			}
		}
		
		//front和rear再次复位，完成加减一级的运算
		
		front = head;
		rear = head.nextElement;
		
		while(!(rear==null)) {
			
				Element.Arithmetic(front,rear);
				rear=rear.nextElement;
				
		}
		
		result.wholeNumber=head.wholeNumber;
		result.numerator=head.numerator;
		result.denominator=head.denominator;
		result.operator=head.operator;
		result.nextElement=head.nextElement;
		
	}

	private   void  RemoveParentheses() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Expression expression=new Expression();
		expression.head=new Element(5,1,2,'*');
		expression.head.nextElement=new Element(3,1,8,'+');
		expression.head.nextElement.nextElement=new Element(8,0,1,' ');
		expression.getResultInNoParentheses();
		Element e=new Element(19,0,0,' ');
		expression.head.print();
		}
}
