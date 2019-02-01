import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
//Page rank code 
	public static void main(String[] args) {
		String web = "C:\\....\\test cases\\test case 1\\links.txt";
		double scaling_factor=0.85;
		double epslion=0.000001;
		int count=Integer.MAX_VALUE;
		ArrayList<String[]> links;
		Input my_links=new Input(web);
		my_links.read_the_links();
		links=my_links.get_links();
		network nt=new network(links);
		long LINEARstartTime = System.nanoTime();
		nt.Creat_Network();
		long LINEARendTime = System.nanoTime();
		long LINEARduration = (LINEARendTime - LINEARstartTime);
		System.out.println("the creation took: "+ LINEARduration + " nano seconds");	
		Map<String, Node> PR=new HashMap<>();
		long LINEARstartTime1 = System.currentTimeMillis();
		for(int i=0;i<100000;i++) {
			PR=nt.PageRanks(epslion, count, scaling_factor);}
		long LINEARendTime1 = System.currentTimeMillis();
		long LINEARduration1 = (LINEARendTime1 - LINEARstartTime1);
		for (Map.Entry<String, Node> entry : PR.entrySet()) {
			System.out.println(entry.getKey()+" " +entry.getValue().Get_PR());
		}
		System.out.println("the calculation took: "+ LINEARduration1 + " mili seconds");	
		System.out.println("the creation took: "+ LINEARduration + " nano seconds");
		Writer wr=new Writer(PR);
		wr.write();
	}

}
