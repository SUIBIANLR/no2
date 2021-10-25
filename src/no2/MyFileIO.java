package no2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class MyFileIO {
	
	// �Զ�����ʽ������Ŀ
	// ��Ŀ��ʽΪ "(1/2 + 3'9/5) * 3"�ַ���
	// ��poll()��������һ���ַ���
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
	
	//д�𰸵�ָ���ļ��������ַ���������ʽ����answers������
	//�����л�Ϊ��������
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
