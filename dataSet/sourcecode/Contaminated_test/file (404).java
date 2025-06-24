package com.gmail.andersoninfonet.managedbeans;

import java.util.HashMap;



import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;


@ManagedBean
@SessionScoped
public class DataTableBean {

	
	
	public void postProcess(Object document){
		
		HSSFWorkbook work = new HSSFWorkbook();
		work = (HSSFWorkbook) document;
		HSSFSheet plan = work.getSheetAt(0);
		Map<String, HSSFCellStyle> estilos = criarEstilos(work);
		HSSFRow header = plan.getRow(0);
		
		/*String title = "Lista de Toner Novo";
		Cell celula = plan.getRow(0).getCell(0);
		plan.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		celula.setCellValue(title);
		celula.setCellStyle(estilos.get("titulo"));*/
		
		
		for(int i=0; i < header.getPhysicalNumberOfCells(); i++){
			HSSFCell cell = header.getCell(i);
			
			cell.setCellStyle(estilos.get("cabecalho"));
			plan.autoSizeColumn(i);
			
		}
		
	}
	
	public Map<String, HSSFCellStyle> criarEstilos(Workbook wb){
		
		Map<String, HSSFCellStyle> estilos = new HashMap<String, HSSFCellStyle>();
		
		HSSFCellStyle estilo;
		
		/*Font tituloFont = wb.createFont();
		tituloFont.setFontHeightInPoints((short)16);
		tituloFont.setColor(IndexedColors.WHITE.getIndex());
		estilo = wb.createCellStyle();
		estilo.setAlignment(CellStyle.ALIGN_CENTER);
		estilo.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		estilo.setFillPattern(CellStyle.SOLID_FOREGROUND);
		estilo.setFont(tituloFont);
		estilos.put("titulo", estilo);*/
		
		Font header = wb.createFont();
		header.setColor(IndexedColors.WHITE.getIndex());
		estilo = (HSSFCellStyle) wb.createCellStyle();
		estilo.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		estilo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estilo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		estilo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		estilo.setBorderRight(HSSFCellStyle.BORDER_THIN);
		estilo.setBorderTop(HSSFCellStyle.BORDER_THIN);
		estilo.setFont(header);
		estilos.put("cabecalho", estilo);
		
		return estilos;
	}
		
}
