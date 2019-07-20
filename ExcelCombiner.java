import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.monitorjbl.xlsx.StreamingReader;

public class ExcelCombiner {
	private FileList fileList = new FileList();
	private SXSSFWorkbook workbook = new SXSSFWorkbook();
	
	public void createWorkbook() throws IOException, InvalidFormatException {
		FileOutputStream out = new FileOutputStream(new File("MIMIC_DATABASE.xlsx"));
		createSheets();
		workbook.write(out);
		out.close();
		workbook.close();
	}
	
	public void createSheets() throws InvalidFormatException, IOException {
		for (File file : fileList.getFiles()) {
			
			Workbook fileBook = StreamingReader.builder()
					.rowCacheSize(100)
					.bufferSize(4096)
					.open(file);
			System.out.println(file.getName());
			
			String sheetName = file.getName().replace(".xlsx", "");
			//New Sheet in Workbook that we're copying info into
			//SXSSFSheet newSheet = workbook.createSheet(sheetName);
			//Old Sheet, fileBook should just be an excel file with one sheet
			//Sheet oldSheet = fileBook.getSheetAt(0);
			
			//newSheet.setRandomAccessWindowSize(100);
			
			for (Sheet oldSheet : fileBook) {
				SXSSFSheet newSheet = workbook.createSheet(sheetName);
				int i = 0;
				for (Row oldRow : oldSheet) {
					SXSSFRow newRow = newSheet.createRow(i);
					i++;
					for (int j = 0; j < oldRow.getLastCellNum(); j++) {
						Cell cell = oldRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell newCell = newRow.createCell(j);
						/*
						DataFormatter dataFormatter = new DataFormatter();
						String cellValue = dataFormatter.formatCellValue(cell);
						*/
						String cellValue = cell.getStringCellValue();
						newCell.setCellValue(cellValue);
					}
				}
			}
			fileBook.close();
		}
		//workbook.close();
	}
	
	public static void main(String[] args) throws IOException, InvalidFormatException {
		ExcelCombiner excelCombiner = new ExcelCombiner();
		excelCombiner.createWorkbook();
	}
}
