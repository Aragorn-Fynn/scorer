package scorer.main;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class ScoreTool {

	private static final Map<String, String> RIGHTANSWERS = new HashMap<>();
	
	private static final Set<String> ANSWERS = new HashSet<>();
	/**
	 * ������ȷ��
	 * @param fileName
	 * @return
	 */
	private static Integer loadRightAnswer(String rightAnswerFileName){
		int answerNum = 0;
		
		try {
			answerNum = Files.readLines(new File(rightAnswerFileName), Charsets.UTF_8, new RightAnswerProcessor());
		} catch (IOException e) {
			System.out.println("���ر�׼�𰸳���");
			e.printStackTrace();
		}
		
		return answerNum;
	}
	
	/**
	 * �����ṩ�Ĵ�
	 * @param anserFileName
	 * @return
	 */
	private static Integer loadAnswer(String anserFileName) {
		
		Integer totalNum = 0;
		try {
			totalNum = Files.readLines(new File(anserFileName), Charsets.UTF_8, new AnswerProcessor());
		} catch (IOException e) {
			System.out.println("���ر���𰸳������������ļ�·�����ļ������Ƿ����Ҫ��");
			e.printStackTrace();
		}
		
		return totalNum;
	}
	
	private static class RightAnswerProcessor implements LineProcessor<Integer> {

		private int answerNum = 0;
		
		@Override
		public Integer getResult() {
			return answerNum;
		}

		@Override
		public boolean processLine(String line) throws IOException {
			if(line != null && !"".equals(line)) {
				RIGHTANSWERS.put(line, null);
				answerNum++;
			}
			return true;
		}
		
	}
	
	private static class AnswerProcessor implements LineProcessor<Integer> {

		//�����Ĵ�������
		int totalNum = 0;
		
		@Override
		public Integer getResult() {
			return totalNum;
		}

		@Override
		public boolean processLine(String line) throws IOException {
			
			if(line != null && !"".equals(line)) {
				ANSWERS.add(line);
				totalNum++;
			}
			return true;
		}
		
	}
	
	//ȡ�ñ���𰸵���������ȷ����
	private static Integer[] getResults() {
		
		Integer[] results= new Integer[2];
		//�����Ĵ�������
		int answerNum = ANSWERS.size();
		//��ȷ�Ĵ�����
		int rightNum = 0;
		for (Iterator<String> iterator = ANSWERS.iterator(); iterator.hasNext();) {
			String answer = iterator.next();
			if (RIGHTANSWERS.containsKey(answer)) {
				rightNum++;
			}
		}
		results[0] = rightNum;
		results[1] = answerNum;
		
		return results;
	}
	
	public static double[] getScores(String anserFileName, String rightAnswerFileName) {
		//��ʼ��
		loadRightAnswer(rightAnswerFileName);
		double totalNum = loadAnswer(anserFileName);

		//ƥ���׼��
		Integer[] results = getResults();
		//ȥ�غ�Ĵ�����
		double answerNum = results[1];
		//��ȷ�𰸵�����
		double rightNum = results[0];
		//��׼�𰸵�����
		double rightAnswerNum = RIGHTANSWERS.size();
		
		System.out.println("*****���ظ��ĸ���:"+(totalNum-answerNum)+"*****");
	
		double[] scores = new double[3];

		//precision:׼ȷ��
		scores[0] = getNumber(rightNum/answerNum);
		//recallRate:�ٻ���
		scores[1] = getNumber(rightNum/rightAnswerNum);
		System.out.println("*****�ٻ���:"+getNumber(rightNum/rightAnswerNum)+"*****");
		//F1
		scores[2] = getNumber(2*scores[0]*scores[1]/(scores[0]+scores[1]));
		
		return scores;
	}
	
	//������λС��
	private static double getNumber(double number){  
        DecimalFormat df = new DecimalFormat("#.0000");  
        double f = Double.valueOf(df.format(number));  
        return f;  
    }
	
	public static void main(String[] args) {
//		System.out.println(loadRightAnswer("D:/work/���Թ���/ԭʼ�ļ�/cid.txt"));
	}
}
