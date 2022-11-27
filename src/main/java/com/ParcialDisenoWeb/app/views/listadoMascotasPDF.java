package com.ParcialDisenoWeb.app.views;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ParcialDisenoWeb.app.entity.Peet;

@Component("Peet")
public class listadoMascotasPDF extends AbstractPdfView{
	
	private static final String[] header = { "Id", "Nombre","Familia", "Edad" , "Dueño" , "Celular Dueño", "Direccion"};

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Peet> listadoPeet = (List<Peet>) model.get("peet");
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.open();
		
		PdfPTable titulos = new PdfPTable(1);
		PdfPCell celda = null;
		
		Font fuenteTitulo = FontFactory.getFont("Montserrat", 20);
		
		celda = new PdfPCell(new Phrase("Listado de Mascotas", fuenteTitulo));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(248, 186, 186));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(15);
		
		titulos.addCell(celda);
		titulos.setSpacingAfter(30);
		
		PdfPTable tablaMascotas = new PdfPTable(7);
		
		for (int i = 0; i < header.length; i++) {
			tablaMascotas.addCell(header[i]);
		}
		
		listadoPeet.forEach(peet ->{
			tablaMascotas.addCell(peet.getId());
			tablaMascotas.addCell(peet.getNombre());
			tablaMascotas.addCell(peet.getFamilia());
			tablaMascotas.addCell(""+peet.getEdad());
			tablaMascotas.addCell(peet.getDueno());
			tablaMascotas.addCell(peet.getCelular());
			tablaMascotas.addCell(peet.getDireccion());
		});
		
		document.add(titulos);
		document.add(tablaMascotas);
		
	}

}
