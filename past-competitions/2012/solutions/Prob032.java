import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Prob032 {
	
	public static final String ASC = "The numbers are in ascending order";
	public static final String DESC = "The numbers are in descending order";
	public static final String RANDOM = "The numbers are in random order";
	public static final String INVALID = "The input was invalid";

	public ArrayList<String> input;
	
	public Prob032(){
		loadFile();
	}
	
	public ArrayList<String> getInput(){
		return this.input;
	}
	
	public void loadFile(){
		InputStream in;
		BufferedReader br;
		
		try{
			in = this.getClass().getResourceAsStream("Prob03.in.txt");
			br = new BufferedReader(new InputStreamReader(in));
			input = new ArrayList<String>();
			
			String s;
	
			while((s = br.readLine()) != null) { 
				input.add(s);
			} 
//			else{
				//file contained no input therefore invalid;
//				input = null;
//			}
			
			br.close();
			in.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void getOrder(){
		
		String retVal = null;
		
		if(this.input!=null && !input.isEmpty()){
			
			for(int i=0; i<input.size(); i++){
				StringTokenizer strTok = new StringTokenizer(input.get(i), " ");
				
				Integer prev = new Integer(0);
				Integer curr;
				int counter = 0;
				retVal = null;
				
				while(strTok.hasMoreTokens()){
					//parse to int and check for invalid input
					try{
						if(counter==0){
							//first int
							prev = new Integer(strTok.nextToken());
						}else{
							//not first one, check order relative to first int
							curr = new Integer(strTok.nextToken());
							
							if(curr>prev){
								//ascending
								if(retVal!=null && retVal!=ASC){
									//order has changed
									retVal = RANDOM;
								}else{
									retVal = ASC;
								}
							}else if(curr<prev){
								//descending
								if(retVal!=null && retVal!=DESC){
									//order has changed
									retVal = RANDOM;
								}else{
									retVal = DESC;
								}
							}else{
								//random
								retVal = RANDOM;
							}
							
							//move to next token
							prev = curr;
						}
						
					}catch(NumberFormatException nfe){
						retVal = INVALID;
						break;
					}
					counter ++;
				}
				System.out.println(retVal);
			}
		}
//		return retVal;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Prob032 o = new Prob032();
		
		if(o.getInput()!=null){
			
			o.getOrder();
			
//			if(order == null){
//				System.out.println(INVALID);
//			}else{
//				System.out.println(order);
//			}
		}else{
			System.out.println(INVALID);
		}
	}

}
