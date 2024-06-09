package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.LocalRepository;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;


@Service
public class PDFService {
    @Autowired
    private LocalRepository localRepository;
    @Autowired
    private ItemRepository itemRepository;

    public byte[] generatePDFForCapacity(int minCapacity,int maxCapacity) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));

            // Adding a title
            Paragraph title = new Paragraph("Locals with Capacity Range betweeen " + minCapacity+ " - "+maxCapacity, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            List<Local> locals = localRepository.findByCapacityBetween(minCapacity,maxCapacity);

            for (Local local : locals) {
                PdfPTable table = new PdfPTable(new float[]{1, 2});
                table.setWidthPercentage(100); // Full width
                //table.setSpacingBefore(10f); // Space before table starts

                PdfPCell cell;

                // Local Name
                cell = new PdfPCell(new Phrase("Name: " + local.getName(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase("Description: " + local.getDescription(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Capacity
                cell = new PdfPCell(new Phrase("Capacity: " + local.getCapacity(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // City
                cell = new PdfPCell(new Phrase("City: " + local.getCity(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Country
                cell = new PdfPCell(new Phrase("Country: " + local.getCountry(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                document.add(table);
                document.add(new Paragraph("\n")); // Add a blank line between entries
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }


    public byte[] generatePDFForPrice(int minPrice,int maxPrice) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));

            // Adding a title
            Paragraph title = new Paragraph("Items with Price Range betweeen " + minPrice+ " - "+maxPrice, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            List<Item> items = itemRepository.findByPriceBetween(minPrice,maxPrice);

            for (Item item : items) {
                PdfPTable table = new PdfPTable(new float[]{1, 2});
                table.setWidthPercentage(100); // Full width
                //table.setSpacingBefore(10f); // Space before table starts

                PdfPCell cell;

                // Local Name
                cell = new PdfPCell(new Phrase("Name: " + item.getName(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase("Type: " + item.getType(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase("Description: " + item.getDescription(), textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // Capacity
                cell = new PdfPCell(new Phrase("Price: " + item.getPrice() + "$", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);


                document.add(table);
                document.add(new Paragraph("\n")); // Add a blank line between entries
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

}
