import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Map;
import java.util.NoSuchElementException;

public class Writer {
	Map<String, Node> map;
	private Formatter output;
	public Writer(Map<String, Node> map) {
		this.map=map;
	}
	public void openfile() {
		try {
			output=new Formatter("Output.txt");
		}
		catch (SecurityException e) {
			System.err.println("access denied");
			System.exit(1);
		}catch (FileNotFoundException e1) {
			System.err.println("File not found");
			System.exit(1);
		}
			}
	public void wtire_To_File() {
		try{
			ArrayList<String> correction;
			for(Map.Entry<String, Node> entry : map.entrySet()) {
				output.format("%s, ",entry.getKey());
				output.format("%f ",entry.getValue().Get_PR());
				output.format("\n");
			}
		}
		catch (FormatterClosedException e) {
			System.err.println("errot writing to file");
		}
		catch (NoSuchElementException e1) {
			System.err.println("invalid input");
		}
	}
	public void closefile() {
		if(output!=null)
			output.close();
	}
	public void write() {
		openfile();
		wtire_To_File();
		closefile();
	}
}
