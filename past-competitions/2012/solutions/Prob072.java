import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Prob072 {
	
	public static final String STUDENT = "STUDENT";
	public static final String ANSWER = "=";
	
	public static int pointValue = -1;
	public static int totalPoints;
	public Collection<Student> input;
	public Collection<String> key;
	
	public Collection<Student> getInput() {
		return input;
	}

	public Collection<String> getKey() {
		return key;
	}

	private class Student{
		private String id;
		private Collection<String> answers;
		
		public Student(){
			
		}
		public Student(String id){
			this.id = id;
		}
		
		public int getScore(Collection<String> key){
			int wrongCount  = 0;
			
			if(key!=null && !key.isEmpty()){
				Iterator<String> keyItr = key.iterator();
				Iterator<String> answerItr = this.answers.iterator();
				
				while(keyItr.hasNext()){
					//can't assume answers are same length as key
					if(answerItr.hasNext()){
						String keyStr = keyItr.next();
						String answerStr = answerItr.next();
						
//						if(answerStr.indexOf(ANSWER)== answerStr.lastIndexOf(ANSWER)){
							//only one answer
							if(!keyStr.equalsIgnoreCase(answerStr)){
								//wrong answer
								wrongCount++;
							}
								
//						}else{
//							//two answers submitted. wrong
//							wrongCount++;
//						}
						
					}else{
						//student didn't answer all questions
						//count how many left.
						keyItr.next();
						wrongCount++;
					}
				}
			}
			return totalPoints - (pointValue * wrongCount);
		}
		
		public String getId() {
			return id;
		}
//		public void setId(String id) {
//			this.id = id;
//		}
//		public Collection<String> getAnswers() {
//			return answers;
//		}

//		public void setAnswers(Collection<String> answers) {
//			this.answers = answers;
//		}		
		public void addAnswer(String s){
			if(this.answers==null)
				this.answers = new ArrayList<String>();
			this.answers.add(s);
		}
		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append(this.id + "\r\n");
			if(this.answers!=null && !this.answers.isEmpty()){
				Iterator<String> itr = this.answers.iterator();
				while(itr.hasNext()){
					sb.append(itr.next() + "\r\n");
				}
			}
			return sb.toString();
		}
	}
	
	
	public void loadData(String filename){
		Student student;
		
		try {
			key = new ArrayList<String>();
			input = new ArrayList<Student>();
			student = this.new Student();
			
			//read file
			InputStream in = this.getClass().getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			//read first line
			pointValue = Integer.parseInt(br.readLine().trim());
			if(pointValue==-1){
				//problem with load
				throw new Exception();
			}
			
			//loop through answer lines
			String s; 
			while((s = br.readLine()) != null) { 
				
				if(s.startsWith(STUDENT)){
					//student answers
					student = new Student(s);
					break;
				}else{
					//answer key
					key.add(s);
				}
			} 
			//set total points
			totalPoints = pointValue * key.size();

			//loop through students
//			int count = 0;
			while((s = br.readLine()) != null) { 
				
				if(s.startsWith(STUDENT)){
					//student is done answering, add to list
					input.add(student);
					//new student
					student = new Student(s);
				}else{
					//same student
					student.addAnswer(s);
				}
				
//				count ++;
			} 
			//add last student to input collection
			input.add(student);
			
			br.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void printScores(){
		
		try{
			if(input!=null && !input.isEmpty()){
				
				Iterator<Student> itr = input.iterator();
				while(itr.hasNext()){
					Student s = itr.next();
					String output = s.getId() + ": " + s.getScore(key);
					System.out.println(output);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Prob072 gs = new Prob072();
		
		gs.loadData("Prob07.in.txt");
		
		gs.printScores();
	}
}
