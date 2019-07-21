import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class FileSelector extends JFrame {
	private JFileChooser fileSelect;
	private JFrame frame;
	//private JPanel panel;
	
	public FileSelector() throws InvalidFormatException, IOException {
		frame = new JFrame();
		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		fileSelect = new JFileChooser()	;
		fileSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
		fileSelect.setFileFilter(filter);
		
		int returnVal = fileSelect.showDialog(frame, "Combine");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String folderPath = fileSelect.getCurrentDirectory().toString();
			System.out.println(folderPath);
			FileList fileList = new FileList(folderPath);
			ExcelCombiner excelCombiner = new ExcelCombiner(fileList);
			
			String fileName = JOptionPane.showInputDialog(frame, "Please input the filename for the master workbook. (.xlsx will be added)");
			fileName += ".xlsx";
			
			excelCombiner.createWorkbook(fileName);
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String [] args) throws InvalidFormatException, IOException {
		FileSelector fileSelector = new FileSelector();
	}
}
