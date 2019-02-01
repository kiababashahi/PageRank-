import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class network {
	private ArrayList<String[]> links;
	private Map<String, Node> Web_sites = new HashMap<>();
	private Map<String,Node> Sinks=new HashMap<>();
	public network(ArrayList<String[]> links) {
		this.links = links;
	}

	public void Creat_Network() {
		for (int i = 0; i < links.size(); i++) {
			Node u = new Node(links.get(i)[0]);
			Node v = new Node(links.get(i)[1]);
			Web_sites.putIfAbsent(links.get(i)[0], u);
			Web_sites.putIfAbsent(links.get(i)[1], v);
		}
		for (int i = 0; i < links.size(); i++) {
			creat_edge(Web_sites.get(links.get(i)[0]), Web_sites.get(links.get(i)[1]));
		}
		initialize();
		//PageRanks(10^-6, 100, 0.65);
	}
	public Map<String, Node> PageRanks(double epsilon, int times,double damp){
		calculate_PR(epsilon, times, damp);
		/*for (Map.Entry<String, Node> entry : Web_sites.entrySet()) {
			System.out.println(entry.getKey()+" " +entry.getValue().Get_PR());
		}*/
		return Web_sites;
	}
	
	private void creat_edge(Node u, Node v) {
		u.point_to(v);
		v.pointed_to(u);
	}

	private void initialize() {
		for (Map.Entry<String, Node> entry : Web_sites.entrySet()) {
			// System.out.println(1.0/Web_sites.size());
			entry.getValue().set_PR(1.0 / Web_sites.size());
		}
	}
	
	public void fin_sinks() {
		for(Map.Entry<String, Node> entry :Web_sites.entrySet()) {
			if(entry.getValue().linkes_to()==0) {
				Sinks.putIfAbsent(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void calculate_PR(double e,int max, double d) {
		int count=0;
		double sum=0.0;
		double oldRank=0;
		double NewRank=0.0;
		double SumofNewRank=Double.MAX_VALUE;
		for(Map.Entry<String, Node> entry: Web_sites.entrySet()) {
			oldRank+=entry.getValue().Get_PR();
		}
		double change=SumofNewRank-oldRank;
		while( count<max && change > e) {
			//SumofNewRank=0;
			for(Map.Entry<String, Node> entry:Web_sites.entrySet()) {
				sum=0;
				 for(int i=0;i<entry.getValue().Get_in().size();i++) {
					 sum+=entry.getValue().Get_in().get(i).Get_Ratio();
				 }
				 NewRank=(1-d)+ (d*sum);
				 entry.getValue().set_PR(NewRank);
			}
			count++;
			for(Map.Entry<String, Node> entry:Sinks.entrySet()) {
				for(Map.Entry<String, Node> Webs:Web_sites.entrySet()) {
					if(!(entry.getKey().equals(Webs.getKey()))){
						double temp=Webs.getValue().Get_PR();
						temp+=(1.0/(Web_sites.size()-1))*entry.getValue().Get_PR();
						NewRank=temp;
						Webs.getValue().set_PR(NewRank);
					}
				}
			}
			SumofNewRank=0;
			for(Map.Entry<String, Node> entry: Web_sites.entrySet()) {
				SumofNewRank+=entry.getValue().Get_PR();
			}
			change=SumofNewRank-oldRank;
			oldRank=SumofNewRank;
		}
	}
	/////// for(Map.Entry<String, Node> entry: Web_sites.entrySet()) {
	///// System.out.println(entry.getValue().Get_out();
	//
}