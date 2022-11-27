package com.ParcialDisenoWeb.app.views;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.ParcialDisenoWeb.app.entity.Booking;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("Booking")
public class listadoCitasPDF extends AbstractPdfView{
	
	private static final String[] header = { "Id", "Fecha","Doctor", "Paciente" , "Tipo" , "Detalles"};

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Booking> listadoPeet = (List<Booking>) model.get("booking");
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.open();
		
		PdfPTable titulos = new PdfPTable(1);
		PdfPCell celda = null;
		
		Font fuenteTitulo = FontFactory.getFont("Montserrat", 20);
		
		celda = new PdfPCell(new Phrase("Listado de Citas", fuenteTitulo));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(248, 186, 186));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(15);
		
		titulos.addCell(celda);
		titulos.setSpacingAfter(30);
		
		PdfPTable tablaCitas = new PdfPTable(6);
		
		for (int i = 0; i < header.length; i++) {
			tablaCitas.addCell(header[i]);
		}
		
		listadoPeet.forEach(peet ->{
			tablaCitas.addCell(peet.getId());
			tablaCitas.addCell(peet.getFecha());
			tablaCitas.addCell(peet.getDoctor());
			tablaCitas.addCell(peet.getPaciente());
			tablaCitas.addCell(peet.getTipo());
			tablaCitas.addCell(peet.getDetalles());
		});
		
		document.add(titulos);
		document.add(tablaCitas);
		
	}

}