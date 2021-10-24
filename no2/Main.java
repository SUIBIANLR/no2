package no2;
import java.util.ArrayList;
public class Main {
	ArrayList<Expression> list= new ArrayList<Expression>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isExist(Expression exp) {
		for (Expression eInList : list) {
			if (eInList.isSameResult(exp) && eInList.isAllOperatorSame(exp) && eInList.isAllValueSame(exp))
				return true;
		}
		return false;
	}
}
