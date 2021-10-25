package no2;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	private static ArrayList<Expression> list = new ArrayList<Expression>();
	private static Queue<String> questions = new LinkedList<String>();
	private static Queue<String> answers = new LinkedList<String>();

	private static boolean setRange = false;
	static int range = 10;  // 数的范围
	
	private static boolean setNumber = false;
	static int number = 10;  // 题目数量
	
	private static boolean setExerciseFile= false;
	private static String exerciseFile = "Exercises.txt";
	
	private static boolean setAnswerFile= false;
	private static String answerFile = "Answers.txt";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {				
			for(int i = 0; i < args.length - 1; i++) {
				if(args[i].matches("-r")) {
					int range = Integer.valueOf(args[++i]);
					if(range >= 1) {
						setRange(range);
						Main.setRange = true;
					}
				}
				
				if(args[i].matches("-w")) {
					int num = Integer.valueOf(args[++i]);
					if(num >= 1) {
						setNumber(num);
						Main.setNumber = true;
					}	
				}
				
				if(args[i].matches("-e")) {
					Main.exerciseFile = args[++i];
					Main.setExerciseFile = true;
				}
				
				if(args[i].matches("-a")) {
					Main.answerFile = args[++i];
					Main.setAnswerFile = true;
				}						
			}
			
			
			if(setRange == false) {
				throw new Exception("未输入参数-r,请输入正确的-r，例：-r 10");
			}
			else {
				InputExpression(number);
				 AddExpressionToQuestion();
				 MyFileIO.writeQueue(exerciseFile, questions);
				 AddExpressionToAnswer();
				 MyFileIO.writeQueue(answerFile, answers);
				
			}
			
			if(Main.setExerciseFile == false && Main.setAnswerFile == false) {
				
			}
			else if(Main.setExerciseFile == true && Main.setAnswerFile == true) {
				/*
				**  从文件做练习题
				 */
				System.out.println("efile = " + exerciseFile);
				System.out.println("afile = " + answerFile);
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			// e.printStackTrace();
			System.out.println("请输入正确的参数！");
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			System.out.println("请输入正确的参数！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void AddExpressionToQuestion() {
		for (int i = 0; i < number; i++) {
			questions.offer(list.get(i).printExpression());
		}
	}
	
	public static void AddExpressionToAnswer() {
		String str="";
		for (int i = 0; i < number; i++) {
			if (list.get(i).getRealResult()) {
				str = list.get(i).printResult();
			} else
				str = "";
			answers.offer(str);
		}
	}
	
	public boolean isExist(Expression exp) {
		for (Expression eInList : list) {
			if (eInList.isSameResult(exp) && eInList.isAllOperatorSame(exp) && eInList.isAllValueSame(exp))
				return true;
		}
		return false;
	}

	public static int randomInt(int range1, int range2) {// 生成一个大于等于range1，小于等于range2的整数
		Random rand = new Random();
		int num = (int) (rand.nextInt(range2 - range1 + 1) + range1);
		return num;
	}

	public static void InputExpression(int numberOfExpressionToInput) {
		for (int i = 0; i < numberOfExpressionToInput; i++) {
			list.add(Expression.GetExpression(randomInt(2, 4), range));
		}
	}
	
	private static void setRange(int range) {
		Main.range = range;
	}
	
	private static void setNumber(int num) {
		Main.number = num;
	}
}

