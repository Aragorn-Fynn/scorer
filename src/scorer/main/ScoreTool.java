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
	 * 加载正确答案
	 * @param fileName
	 * @return
	 */
	private static Integer loadRightAnswer(String rightAnswerFileName){
		int answerNum = 0;
		
		try {
			answerNum = Files.readLines(new File(rightAnswerFileName), Charsets.UTF_8, new RightAnswerProcessor());
		} catch (IOException e) {
			System.out.println("加载标准答案出错！");
			e.printStackTrace();
		}
		
		return answerNum;
	}
	
	/**
	 * 加载提供的答案
	 * @param anserFileName
	 * @return
	 */
	private static Integer loadAnswer(String anserFileName) {
		
		Integer totalNum = 0;
		try {
			totalNum = Files.readLines(new File(anserFileName), Charsets.UTF_8, new AnswerProcessor());
		} catch (IOException e) {
			System.out.println("加载被测答案出错，请检查您的文件路径及文件内容是否符合要求！");
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

		//给出的答案总数量
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
	
	//取得被测答案的数量和正确数量
	private static Integer[] getResults() {
		
		Integer[] results= new Integer[2];
		//给出的答案总数量
		int answerNum = ANSWERS.size();
		//正确的答案数量
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
		//初始化
		loadRightAnswer(rightAnswerFileName);
		double totalNum = loadAnswer(anserFileName);

		//匹配标准答案
		Integer[] results = getResults();
		//去重后的答案数量
		double answerNum = results[1];
		//正确答案的数量
		double rightNum = results[0];
		//标准答案的数量
		double rightAnswerNum = RIGHTANSWERS.size();
		
		System.out.println("*****答案重复的个数:"+(totalNum-answerNum)+"*****");
	
		double[] scores = new double[3];

		//precision:准确率
		scores[0] = getNumber(rightNum/answerNum);
		//recallRate:召回率
		scores[1] = getNumber(rightNum/rightAnswerNum);
		System.out.println("*****召回率:"+getNumber(rightNum/rightAnswerNum)+"*****");
		//F1
		scores[2] = getNumber(2*scores[0]*scores[1]/(scores[0]+scores[1]));
		
		return scores;
	}
	
	//保留四位小数
	private static double getNumber(double number){  
        DecimalFormat df = new DecimalFormat("#.0000");  
        double f = Double.valueOf(df.format(number));  
        return f;  
    }
	
	public static void main(String[] args) {
//		System.out.println(loadRightAnswer("D:/work/测试工具/原始文件/cid.txt"));
	}
}
