package seleniumFrameworkDesign.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + filePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap <String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap <String, String>>>(){});
		return data;
	}
	
	public Object[][] getExcelDataToObject(String filePath) throws IOException
	{
		FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + filePath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		XSSFRow row = sheet.getRow(0);
		int columnCount = row.getLastCellNum();
		Object[][] data = new Object[rowCount-1][columnCount];
		
		for (int i = 0; i < rowCount-1; i++)
		{
			row = sheet.getRow(i+1);
			for (int j = 0; j < columnCount; j++)
			{
				DataFormatter formatter = new DataFormatter();
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell);
			}
		}
		return data;
	}
}
