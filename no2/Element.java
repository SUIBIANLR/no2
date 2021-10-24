package no2;

public class Element {
	int wholeNumber = 0;
	int numerator = 0;
	int denominator = 0;
	char operator = ' ';
	int flag=0;
	Element nextElement = null;
	
	//无括号情况下构造函数
	public Element(int wholeNumber, int numerator, int denominator, char operator) {
		super();
		this.wholeNumber = wholeNumber;
		this.numerator = numerator;
		this.denominator = denominator;
		this.operator = operator;
	}
	
	//右括号情况下构造函数
	public Element(int wholeNumber, int numerator, int denominator, char operator,int flag) {
		super();
		this.wholeNumber = wholeNumber;
		this.numerator = numerator;
		this.denominator = denominator;
		this.operator = operator;
		this.flag=flag;
	}

	// 元素加法,结果放入e1中

	public static void Add(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0)// 若分数部分均为0，只需对整数部分运算即可
			e1.wholeNumber += e2.wholeNumber;
		else {
			e1.wholeNumber += e2.wholeNumber;// 带分数整数部分相加，放入e1的整数部分
			int lcm = Lcm(e1.denominator, e2.denominator);// 求最小公倍数
			e1.numerator *= lcm / e1.denominator;// 对e1的分子进行变换
			e2.numerator *= lcm / e2.denominator;// 对e2的分子进行变换
			e1.denominator = lcm;// 分母就是求得的最小公倍数
			e1.numerator += e2.numerator;// e1和e2分子相加，存放在e1的分子处

			// 将假分数化成带分数(也防止出现e1的值为十七又三分之三而不是十八的情况)

			e1.wholeNumber += e1.numerator / e1.denominator;
			e1.numerator = e1.numerator % e1.denominator;

			// 最终结果要约分

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		}
		e1.operator = e2.operator;// 由于运算已经完成，将e2的运算符赋值给e1的运算符
		e1.nextElement = e2.nextElement;// 修改结点关系
	}

	// 元素减法,由于只有元素减法会产生负数，因此一旦有负数，return false

	public static boolean Sub(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0) {// 若分数部分均为0，只需对整数部分运算即可
			e1.wholeNumber -= e2.wholeNumber;
			if (e1.wholeNumber < 0)
				return false;
		} else {
			if (e1.wholeNumber < e2.wholeNumber)
				return false;// 如果整数部分就不够减，return false
			e1.numerator += e1.wholeNumber * e1.denominator;// 将带分数转化为假分数
			e2.numerator += e2.wholeNumber * e2.denominator;// 将带分数转化为假分数
			int lcm = Lcm(e1.denominator, e2.denominator);// 求最小公倍数
			e1.numerator *= lcm / e1.denominator;// 对e1的分子进行变换
			e2.numerator *= lcm / e2.denominator;// 对e2的分子进行变换
			e1.denominator = lcm;// 分母就是求得的最小公倍数
			e1.numerator -= e2.numerator;
			if (e1.numerator < 0)
				return false;

			// 将假分数化成带分数

			e1.wholeNumber = e1.numerator / e1.denominator;
			e1.numerator = e1.numerator % e1.denominator;

			// 最终结果要约分

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		} // e1和e2分子相减，存放在e1的分子处
		e1.operator = e2.operator;// 由于运算已经完成，将e2的运算符赋值给e1的运算符
		e1.nextElement = e2.nextElement;// 修改结点关系
		return true;
	}

	// 元素乘法，结果放入e1中

	public static void Multipy(Element e1, Element e2) {
		if (e1.numerator == 0 && e2.numerator == 0)// 若分数部分均为0，只需对整数部分运算即可
			e1.wholeNumber *= e2.wholeNumber;
		else {
			e1.numerator = (e1.wholeNumber * e1.denominator + e1.numerator)
					* (e2.wholeNumber * e2.denominator + e2.numerator);
			e1.denominator = e1.denominator * e2.denominator;
			e1.wholeNumber = e1.numerator / e1.denominator;// 将假分数化成带分数
			e1.numerator = e1.numerator % e1.denominator;

			// 最终结果要约分

			int factor = Gcd(e1.numerator, e1.denominator);
			e1.numerator /= factor;
			e1.denominator /= factor;
		}
		e1.operator = e2.operator;
		e1.nextElement = e2.nextElement;
	}

	// 元素除法

	public static void Divide(Element e1, Element e2) {
		e1.numerator += e1.wholeNumber * e1.denominator;// 将带分数转化为假分数
		e2.numerator += e2.wholeNumber * e2.denominator;// 将带分数转化为假分数

		// 将除法运算转化为乘法运算

		e1.wholeNumber = e2.wholeNumber = 0;
		int temp = e2.numerator;
		e2.numerator = e2.denominator;
		e2.denominator = temp;
		Multipy(e1, e2);
	}
	// 最大公因数算法

	public static int Gcd(int a, int b) {
		if (b == 0)
			return a;
		return Gcd(b, a % b);
	}

	// 最小公倍数算法

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
		else if (e1.operator == '-')
			Sub(e1, e2);
		else if (e1.operator == '*')
			Multipy(e1, e2);
		else if (e1.operator == '/')
			Divide(e1, e2);
	}
	
	//判断符号是否是高优先级
	
	public boolean isPrioritized(){
		if(operator=='*'||operator=='/')return true;
		return false;
	}
	
	//
	public static Element ramdomElement(String type, int range) {  // 0 <= 结果 < range
		  switch(type) {
		  case "integer": case "INT":{  //生成整数
		   if (range < 0) {
		    return null;
		   }
		   int num = (int)(range * Math.random());
		   return new Element(num, 0, 1, ' ');
		  }
		  case "true fraction": case "TF":{  //生成真分数
		   if (range <= 0) {
		    return null;
		   }
		   int denominator = (int)(range * Math.random() + 1);
		   int numerator = (int)(range * Math.random());
		   return new Element(0, numerator, denominator, ' ');
		  }
		  case "band fracrion": case "BF":{  //生成带分数
		   if (range <= 0) {
		    return null;
		   }
		   int num = (int)(range * Math.random());
		   int denominator = (int)(range * Math.random() + 1);
		   int numerator = (int)(range * Math.random());
		   return new Element(num, numerator, denominator, ' ');
		  }
		  default:
		   return null;
		  } 
		 }
		 
		 public void setOperator(char ch) {
		  if(ch=='+' || ch=='-' || ch=='*' || ch=='/') {
		   this.operator = ch;
		   return;
		  }
		  else { return; }
		 }
		 
		 public void randomOperator(char ch) {
		  int num = (int)(4 * Math.random());
		  switch(num) {
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
		  
		  default:{}
		  }
		  
		  return;
		 }
}
