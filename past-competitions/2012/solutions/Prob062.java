import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author nortoha
 *
 */
public class Prob062 {
	
	private static void writeFile(Collection<String> output) throws Exception{
		try{
			if(output!=null && !output.isEmpty()){
				Iterator<String> itr = output.iterator();
				while(itr.hasNext()){
					String s = itr.next() + "\r\n";
					System.out.print(s);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Collection<String> output = new ArrayList<String>();
		
		try{
			//read file
			InputStream in = Prob062.class.getResourceAsStream("Prob06.in.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			//loop through lines
			String s; 
			
			while((s = br.readLine()) != null) { 
				s = s.toUpperCase().replaceAll("[^a-zA-Z0-9]", "");
				if(new StringBuffer(s).reverse().toString().equals(s))
					output.add("yes");
				else 
					output.add("no");
			} 
			
			br.close();
			in.close();

			//write file
			writeFile(output);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
