import java.util.ArrayList;

public class Node {
	private double PR=0.0;
	private String URL;
	private ArrayList<Node> Out=new ArrayList<Node>();
	private ArrayList<Node> in=new ArrayList<Node>();
	public Node(String URL) {
		this.URL=URL;
	}
	public void point_to(Node n) {
		Out.add(n);
	}
	public void pointed_to(Node n) {
		in.add(n);
	}
	public ArrayList<Node> Get_out(){
		return Out;
	}
	public ArrayList<Node> Get_in(){
		return in;
	}
	public boolean In_sink() {
		if (Out.size()==0)
				return true;
		else 
			return false;
 	}
	public int is_reffered() {
		return in.size();
	}
	public int linkes_to() {
		return Out.size();
	}
	public String get_URL() {
		return URL;
	}
	public void set_PR(double PR) {
		this.PR=PR;
	}
	public double Get_PR() {
		return PR;
	}
	public double Get_Ratio() {
		return PR/Out.size();
	}
	
}
