package scorer.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入标准答案的文件路径:");
		String rightAnswerFileName = input.nextLine();
		System.out.println("请输入被测答案的文件路径:");
		String answerFileName = input.nextLine();
		input.close();
		
		long startTime = System.currentTimeMillis();
		double[] scores = ScoreTool.getScores(answerFileName, rightAnswerFileName);
		System.out.println("准确率:"+ scores[0] + ".");
		System.out.println("召回率:"+ scores[1] + ".");
		System.out.println("F1:"+ scores[2] + ".");
		
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时："+(endTime - startTime)/1000+"秒");
	}
	

}
