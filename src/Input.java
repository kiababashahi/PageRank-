import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {
	private Scanner input;
	private String Input_Links;
	private String[] URLS=new String[2];
	private ArrayList<String[]> links=new ArrayList<String[]>();
	private ArrayList<String> raw_data=new ArrayList<String>();
	public Input(String Links) {
		this.Input_Links=Links;
	}
	public void read_the_links() {
		open();
		read();
		close();
	}
	public ArrayList<String[]> get_links(){
		return links;
	}
	private void open() {
		try {
			input=new Scanner(new File(Input_Links));
		}
		catch(FileNotFoundException e) {
			System.err.println("Error opening file");
			System.exit(1);
		}
	}
	private void read(){
		try {
			while(input.hasNextLine()) {
				raw_data.add(input.nextLine());
			}
			
		} catch (NoSuchElementException e){
			System.err.println("file improperly formed");
			input.close();
			System.exit(1);
		}
		catch (IllegalStateException e1) {
			System.err.println("error reading file");
			System.exit(1);
		} 	
		int[] indexes=new int[raw_data.size()];
		for(int i=0;i<raw_data.size();i++) {
			for(int j=0;j<raw_data.get(i).length();j++) {
				if(raw_data.get(i).charAt(j)==',') {
					indexes[i]=j;
				}					
			}
		}
		for(int i=0;i<raw_data.size();i++) {
			URLS[0]=raw_data.get(i).substring(0,indexes[i]);
			URLS[1]=raw_data.get(i).substring(indexes[i]+1,raw_data.get(i).length());
			URLS[0]=URLS[0].trim();
			URLS[1]=URLS[1].trim();
			links.add(URLS);
			URLS=new String[2];
		}
		raw_data.clear();
	}
	private void close() {
		if(input !=null)
			input.close();
	}
}
