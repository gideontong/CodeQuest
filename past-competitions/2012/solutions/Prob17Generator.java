import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Prob17Generator
{
	static PrintStream out;

	static List<List<int[]>> solutions = new ArrayList<List<int []>>();
	
	static void printPath(PrintStream out,Stack<Integer> path)
	{
		if(path.size()==0)
		{
			return;
		}
		for(int i : path)
		{
			out.print('.');
			switch(i)
			{
			case 0:
				out.print("up");
				break;
			case 1:
				out.print("rt");
				break;
			case 2:
				out.print("dn");
				break;
			case 3:
				out.print("lf");
				break;
			}
		}
	}
	
	static char[] generateMaze()
	{
		char[] map=new char[25];
		map[0]='0';
		for(int i=1;i<25;++i)
		{
			char r=Math.random()>0.5?'0':'1';
			map[i]=r;
		}
		int index=(int)(Math.random()*25);
		map[index]='2';
		return map;
	}
	
	static void printSolution(PrintStream out, List<int[]> answer)
	{
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
	
	static void printMap(PrintStream out, char[] map, int pos)
	{
		out.println("<table>");
		for(int y=0;y<5;++y)
		{
			out.print("<tr>");
			for(int x=0;x<5;++x)
			{
				int cell=y*5+x;
				out.print("<td style=\"background-color:");
				if(cell!=pos)switch(map[cell])
				{
				case '3':
					out.print("blue");
					break;
				case '2':
					out.print("green");
					break;
				case '1':
					out.print("gray");
					break;
				case '0':
					out.print("white");
					break;
				}
				else
				{
					if(map[cell]=='2')
						out.print("red");
					else
						out.print("yellow");
				}
				out.print(";border:1px solid black;\">");
				out.print(cell+1<10?"0":"");
				out.print(cell+1);
				out.print("</td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
	}
	
	public static void main(String[] args)
	{

		try
		{
			if(args.length>0)
			{
				File file=new File(args[0]);
				if(file.exists())file.delete();
				out = new PrintStream(new FileOutputStream(file));
			}
			else out = System.out;
//			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("Prob17.in.txt")));
			
			solutions.clear();
			
			int longest=0;
			
//			String line=null;
			//while((line=in.readLine())!=null)
			//{
			for(int i=0;i<10000;++i)
			{
				char[] map=generateMaze();
				//char[] map=new char[25];
				//for(int i=0;i<25;++i)
				//{
				//	map[i]=line.charAt(i*2);
				//}
				
				solutions.clear();

				Prob17.solve(new Prob17.Progress(){

					@Override
					public void reportAnswer(List<int[]> answer)
					{
						solutions.add(answer);
						//printSolution(out,answer);
						//out.println("<br/>");
					}

					@Override
					public void reportMap(char[] map, int pos)
					{
						//printMap(out,map,pos);
						//out.println("<br/>");
					}

					@Override
					public void reportPath(Stack<Integer> path)
					{
						//printPath(out,path);
						//out.println("<br/>");
					}
					
				}, new ArrayList<int[]>(), map, 0);
				
				if(solutions.size()==0)
				{
					//out.print("No solution");
				}
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
						if(solutions.get(0).size()==solutions.get(1).size())
						{
							//out.print("Multiple solutions");
							continue;
						}
						else
						{
							List<int[]> answer=solutions.get(0);
							if(answer.size()>longest)
							{
								longest=answer.size();
								printMap(out,map,0);
								out.println("<br/>");
								
								
								for(int j=0;j<answer.size();++j)
								{
									int[] move = answer.get(j);
									int from=move[0];
									int to=move[1];
									out.print(from+1);
									out.print('-');
									out.print(to+1);
									if(j+1<answer.size())out.print(' ');
								}
							}
						}
					}
					else
					{
						List<int[]> answer=solutions.get(0);
						if(answer.size()>longest)
						{
							longest=answer.size();
							printMap(out,map,0);
							out.println("<br/>");
							
							
							for(int j=0;j<answer.size();++j)
							{
								int[] move = answer.get(j);
								int from=move[0];
								int to=move[1];
								out.print(from+1);
								out.print('-');
								out.print(to+1);
								if(j+1<answer.size())out.print(' ');
							}
						}
					}
				}
			}
			
			/*int largest=0;

			//for(int i=0;i<1000000;++i)
			/*while(true)
			{
				int [] map1=generateMaze();
				answer=null;
				solve(new ArrayList<int[]>(),new ArrayList<int[]>(), map1,0);
				if(answer!=null)
				{
					//printSolution(new PrintStream(System.out),answer);
					if(answer.size()>largest)
					{
						largest=answer.size();
						printMap(map1);
						System.out.println();
						System.out.println("-----*");
						printSolution(new PrintStream(System.out),answer);
						System.out.println();
						System.out.println("********************************************************");
					}
				}
				//else
				//	out.print("No solution");
				//out.println();
				//out.println("********************************************************");
			}*/

			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
