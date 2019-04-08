import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Prob17
{
	static List<List<int[]>> solutions=new ArrayList<List<int[]>>();
	
	static Stack<Integer> path=new Stack<Integer>();
	
	static void printSolution(PrintStream out)
	{
		if(solutions.size()==0)
			out.print("No solution");
		else
		{
			Collections.sort(solutions, new Comparator<List<int[]>>(){

				@Override
				public int compare(List<int[]> object1, List<int[]> object2)
				{
					return object1.size()-object2.size();
				}
				
			});
			
			if(solutions.size()>1)
			{
				if(solutions.get(0).size()==0)
				{
					out.print("No moves necessary");
				}
				else if(solutions.get(0).size()==solutions.get(1).size())
				{
					out.print("Multiple solutions:");
					for(List<int[]> answer : solutions)
					{
						for(int i=0;i<answer.size();++i)
						{
							int[] move = answer.get(i);
							int from=move[0];
							int to=move[1];
							out.print(from+1);
							out.print('-');
							out.print(to+1);
							if(i+1<answer.size())out.print(", ");
						}	
					}
					return;
				}				
			}

			List<int[]> answer=solutions.get(0);
			for(int i=0;i<answer.size();++i)
			{
				int[] move = answer.get(i);
				int from=move[0];
				int to=move[1];
				out.print(from+1);
				out.print('-');
				out.print(to+1);
				if(i+1<answer.size())out.print(' ');
			}
		}
	}
	
	static int look(int direction, int pos)
	{
		int x,y;
		switch(direction)
		{
		case 0:
			pos-=5;
			return pos<0?-1:pos;
		case 1:
			y=pos/5;
			x=pos%5;
			++x;
			return x>4?-1:y*5+x;
		case 2:
			pos+=5;
			return pos>24?-1:pos;
		case 3:
			y=pos/5;
			x=pos%5;
			--x;
			return x<0?-1:y*5+x;
		}
		return -1;
	}
	
	static interface Progress
	{
		void reportPath(Stack<Integer> path);
		
		void reportMap(char[] map, int pos);
		
		void reportAnswer(List<int[]> answer);
	}
	
	static void solve(Progress out, List<int[]> progress, char[] map, int pos)
	{
		if(out!=null)
		{
			out.reportPath(path);
			out.reportMap(map, pos);
		}
		for(int direction=0;direction<4;++direction)
		{
			path.push(direction);
			int lookPos=look(direction,pos);
			if(lookPos>=0)
			{
				char[] m=Arrays.copyOf(map, map.length);
				m[pos]='3';
				switch(map[lookPos])
				{
				case '0':
					solve(out,progress,m,lookPos);
					break;
				case '1':
					int newPos=look(direction,lookPos);
					char finding=newPos<0?'1':map[newPos];
					if(finding!='1'&&finding!='2')
					{
						m[lookPos]='0';
						m[newPos]='1';
						//for(int i=0;i<m.length;++i)if(m[i]=='3')m[i]='0';
						List<int[]> newSolution=new ArrayList<int[]>(progress);
						newSolution.add(new int[]{lookPos,newPos});
						solve(out,newSolution,m,lookPos);
					}
					break;
				case '2':
					List<int[]> answer=new ArrayList<int[]>(progress);
					solutions.add(answer);
					if(out!=null)
					{
						out.reportPath(path);
						out.reportMap(m, lookPos);
						out.reportAnswer(answer);
					}
					break;
				}
			}
			path.pop();
		}
	}
	
	public static void main(String[] args)
	{

		try
		{
			PrintStream out;
			if(args.length>0)
			{
				File file=new File(args[0]);
				if(file.exists())file.delete();
				out = new PrintStream(new FileOutputStream(file));
			}
			else out = System.out;
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("Prob17.in.txt")));
			
			String line=null;
			while((line=in.readLine())!=null)
			{
				char[] map=new char[25];
				for(int i=0;i<25;++i)
					map[i]=line.charAt(i*2);

				solutions.clear();
				solve(null,new ArrayList<int[]>(), map,0);
				printSolution(out);
				out.println();
			}
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
