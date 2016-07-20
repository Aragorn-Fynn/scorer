package scorer.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("�������׼�𰸵��ļ�·��:");
		String rightAnswerFileName = input.nextLine();
		System.out.println("�����뱻��𰸵��ļ�·��:");
		String answerFileName = input.nextLine();
		input.close();
		
		long startTime = System.currentTimeMillis();
		double[] scores = ScoreTool.getScores(answerFileName, rightAnswerFileName);
		System.out.println("׼ȷ��:"+ scores[0] + ".");
		System.out.println("�ٻ���:"+ scores[1] + ".");
		System.out.println("F1:"+ scores[2] + ".");
		
		long endTime = System.currentTimeMillis();
		System.out.println("�ܺ�ʱ��"+(endTime - startTime)/1000+"��");
	}
	

}
