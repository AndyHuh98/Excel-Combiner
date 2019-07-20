import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FileList {
	private File[] files;
	private String folderPath;
	private File folder;
	
	public FileList() {
		folderPath = "/Users/andrew/Desktop/MIMIC_DATABASE";
		folder = new File(folderPath);
	    files = folder.listFiles(new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return !name.equals(".DS_Store");
	        }
	    });
	}
	
	//Getters
	public File[] getFiles() {
		return files;
	}
	
	public File getFolder() {
		return folder;
	}
	
	//Tester
	public static void testClass() {
		FileList fileList = new FileList();
		
		int i = 0;
		
		for (File file : fileList.getFiles()) {
			System.out.println(file.getName());
			i++;
		}
	}
	
	/*
	public static void main(String[] args) {
		testClass();
	}
	*/
}
