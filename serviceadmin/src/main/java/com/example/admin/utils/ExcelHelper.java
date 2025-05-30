package com.example.admin.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.admin.domain.UserEntity;

import io.jsonwebtoken.io.IOException;

public class ExcelHelper {
	public static List<UserEntity> getEmployeesObjs(InputStream is)
	{
		List<UserEntity> usrlst=new ArrayList<>();
		
		try {
			
			//create work book object
			Workbook workbook=new XSSFWorkbook(is);
			
			//get sheet
			Sheet sheet = workbook.getSheet("userdata");
			
			Iterator<Row> rowItr = sheet.iterator();
			  // 1. Create a CreationHelper
	        CreationHelper createHelper = workbook.getCreationHelper();

	        // 2. Create a DataFormat
	        DataFormat dataFormat = createHelper.createDataFormat();
	        String dateFormat = "yyyy-MM-dd"; // Example format
	        short format = dataFormat.getFormat(dateFormat);

	        // 3. Create a CellStyle
	        CellStyle cellStyle = workbook.createCellStyle();
	        cellStyle.setDataFormat(format);

	        // 4. Convert LocalDate to Date
	        LocalDate localDate = LocalDate.now();
	        java.util.Date date = java.sql.Date.valueOf(localDate); // Use java.sql.Date or java.time.Instant
			
			int rowCount=0;
			while(rowItr.hasNext())
			{
				Row row = rowItr.next();

				if(rowCount==0)
				{
					rowCount++;
					continue;
				}
				UserEntity user=new UserEntity();
				Iterator<Cell> cell = row.iterator();
	
				System.out.println("row count:"+rowCount);
				int cellCount=1;
				
				while(cell.hasNext())
				{
					Cell next = cell.next();
					switch (cellCount)
					{
					case 1: {
						user.setUserName(next.getStringCellValue());
						System.err.println(user.getUserName());
						
						break;
					}
					case 2:
					{
						user.setAddress(next.getStringCellValue());
						System.err.println(user.getAddress());
						break;
					}
					case 3:
					{
						user.setEmail(next.getStringCellValue());
						System.err.println(user.getEmail());
						break;
					}
					case 4:
					{
						user.setMobileNumber(next.getStringCellValue());
						System.err.println(user.getMobileNumber());
						break;
					}
					case 5:{
						user.setDob(localDate);
					}
					case 6:
					{
						user.setUserImg(next.getStringCellValue());
					}
					default:
						break;
					}//switch end
					cellCount++;
					System.out.println("cell count:"+cellCount);
				}//cell iterator
				usrlst.add(user);
				usrlst.stream().forEach(e->System.out.println("user list:"+e));
			}
			//row iterator
			workbook.close();
		} //try end
		catch (Exception e) {
			e.printStackTrace();
		}
		return usrlst;
	}
	
	//download excel file
	//@Override
	public static ByteArrayOutputStream empToExcel(List<UserEntity> user) throws java.io.IOException { //working

		 String[] HEADERs = { "id","userName","address","email","mobileNumber","dob","userImg" };
		 
	    try (Workbook workbook = new XSSFWorkbook();
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	    	
	      Sheet sheet = workbook.createSheet("test");
	     
	      
	     System.out.println("get First row num:"+ sheet.getFirstRowNum());
	      sheet.getScenarioProtect();
	      // Header
	      Row headerRow = sheet.createRow(0);
	      
	      Row row2 = sheet.getRow(0);
	      
	  //    headerRow.getCell(0).setAsActiveCell();
	      
	      //set first row locked
	     // row2.getRowStyle().setLocked(true);
	      
	      //cell.getCellStyle().setLocked(true);
	      
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	  
	        cell.setCellValue(HEADERs[col]);
	      }
	      
	      int rowIdx = 1;
	      for (UserEntity u : user) {
	    	 
	        Row row = sheet.createRow(rowIdx++);

	        row.createCell(0).setCellValue(u.getId());
	        row.createCell(1).setCellValue(u.getUserName());
	        row.createCell(2).setCellValue(u.getAddress());
	        row.createCell(3).setCellValue(u.getEmail());
	        row.createCell(4).setCellValue(u.getMobileNumber());
	        row.createCell(5).setCellValue(u.getDob());
//	        row.createCell(2).setCellValue(emps.getDob());?
//	        row.createCell(5).setCellValue(u.getPinCode());
	        row.createCell(6).setCellValue(u.getUserImg());
//	        row.createCell(2).setCellValue(emps.getDob());
//	        row.createCell(3).setCellValue(emps.getName());	 
	        
	      }
	      workbook.write(out);
	      return out;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
}
