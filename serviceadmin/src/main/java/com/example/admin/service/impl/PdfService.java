package com.example.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.domain.UserEntity;
import com.example.admin.repository.UserRepo;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
@Service
public class PdfService {

	@Autowired
	private UserRepo userRepo;
	
//	 public ByteArrayInputStream createPdf(String content) {
//	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//	        PdfWriter writer = new PdfWriter(out);
//	        PdfDocument pdfDoc = new PdfDocument(writer);
//	        Document document = new Document(pdfDoc);
//
//	        document.add(new Paragraph(content));
//
//	        document.close();
//
//	        return new ByteArrayInputStream(out.toByteArray());
//	    }
	
	
	    public ByteArrayInputStream createPdfWithTable() {
	    	
	    	List<UserEntity> all = userRepo.findAll();
	    	
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
 
	        PdfWriter writer = new PdfWriter(out);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        // Define table with 3 columns (or based on your data)
	        float[] columnWidths = {150F, 150F, 150F,150F,150F}; // Customize column width
	        Table table = new Table(columnWidths);

	        // Add table header
	        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
	        table.addHeaderCell(new Cell().add(new Paragraph("Name")));
	        table.addHeaderCell(new Cell().add(new Paragraph("Email")));
	        table.addHeaderCell(new Cell().add(new Paragraph("Dob")));
	        table.addHeaderCell(new Cell().add(new Paragraph("address")));

	        
	        
	        Integer id=0;
	        // Add table rows from data
	        for (UserEntity row : all) {
	        	
	        	id++;
	        	table.addCell(String.valueOf(id));
	        	table.addCell(row.getUserName());
	        	table.addCell(row.getEmail());
	        	table.addCell(String.valueOf(row.getDob()));
	        	table.addCell(row.getAddress());
	        	
//	            for (String cellData : row) {
//	                table.addCell(new Cell().add(new Paragraph(cellData)));
//	            }
	        }

	        document.add(new Paragraph("User List")); // Title
	        document.add(table);

	        document.close();

	        return new ByteArrayInputStream(out.toByteArray());
	    }
}
