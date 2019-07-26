import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		//frame.setSize(300, 500);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		fileSelect = new JFileChooser()	;
		fileSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		/*
		fileSelect.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent click) {
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		*/
		
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
		
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			frame.setVisible(false);
			frame.dispose();
			System.exit(0);
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String [] args) throws InvalidFormatException, IOException {
		FileSelector fileSelector = new FileSelector();
	}
}
