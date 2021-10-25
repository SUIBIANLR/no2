package no2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class MyFileIO {
	
	// 以队列形式储存题目
	// 题目形式为 "(1/2 + 3'9/5) * 3"字符串
	// 用poll()函数出队一个字符串
	public static Queue<String> getExerciseFormFile(String fileName) {
		try {
			LinkedList<String> questions = new LinkedList<String>();
			RandomAccessFile f = new RandomAccessFile(fileName, "r");
			
			String question = f.readLine();
			
			while(question != null) {
				String cut = question.replaceFirst("^[0-9]+.", "");
				String finalStr = cut.replace(" = ", "");
				System.out.println(finalStr);
				questions.offer(finalStr);
				question = f.readLine();
			}
			f.close();
			
			return questions;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	//写答案到指定文件，答案以字符串队列形式放在answers队列中
	//函数中会为你加上序号
	public static void writeQueue(String fileName, Queue<String> Queue){
		try {
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			Integer index = 1;
			String answer = Queue.poll();
			
			while(answer != null) {		
				f.writeBytes(index.toString() + '.' + answer + '\n');
				index++;
				answer = Queue.poll();
			}
			f.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
