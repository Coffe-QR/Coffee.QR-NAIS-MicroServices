package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ItemRepository;

import java.awt.*;
import java.awt.List;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@Service
public class PDFService {
    @Autowired
    private ItemRepository itemRepository;


    public byte[] generatePDFForCapacity(String type) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title = new Paragraph("The most sold " + type, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            var items = itemRepository.findTop30SoldItems();

            // Creating table with 5 columns
            PdfPTable table = new PdfPTable(new float[]{2, 4, 2, 2, 2});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Adding table headers
            String[] headers = {"Name", "Description", "Type", "Price", "Number Sold"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items) {
                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Number sold
                cell = new PdfPCell(new Phrase(String.valueOf(itemRepository.findItemPurchaseCount(item.getId())), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);
            }

            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePDFNotSoldItem(String type) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
            System.out.println("TU SAM 2");

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title = new Paragraph("The most sold " + type, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            var items = itemRepository.findItemsNotSoldInLastSixMonths();

            // Creating table with 5 columns
            PdfPTable table = new PdfPTable(new float[]{2, 4, 2, 2});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            System.out.println("TU SAM 3");

            // Adding table headers
            String[] headers = {"Name", "Description", "Type", "Price"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items) {

                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);
            }

            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePDFBasedOnItemDescription(double price, String type) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont2 = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont2 = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont2 = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title2 = new Paragraph("The most sold " + type, titleFont2);
            title2.setAlignment(Element.ALIGN_CENTER);
            title2.setSpacingAfter(20);
            document.add(title2);

            var items2 = itemRepository.findItemsWithinBudget(price);

            // Creating table with 5 columns
            PdfPTable table2 = new PdfPTable(new float[]{2, 4, 2, 2});
            table2.setWidthPercentage(100);
            table2.setSpacingBefore(10f);
            table2.setSpacingAfter(10f);

            // Adding table headers
            String[] headers2 = {"Name", "Description", "Type", "Price"};
            for (String header : headers2) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont2));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table2.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items2) {
                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);
            }

            document.add(table2);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }


    public byte[] allPdfs(String type, double price) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Set document margins
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title = new Paragraph("The most sold " + type, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            var items = itemRepository.findTop30SoldItems();

            // Creating table with 5 columns
            PdfPTable table = new PdfPTable(new float[]{2, 4, 2, 2, 2});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Adding table headers
            String[] headers = {"Name", "Description", "Type", "Price", "Number Sold"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items) {
                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);

                // Number sold
                cell = new PdfPCell(new Phrase(String.valueOf(itemRepository.findItemPurchaseCount(item.getId())), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table.addCell(cell);
            }

            document.add(table);


            Font titleFont1 = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont1 = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont1 = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
            System.out.println("TU SAM 2");

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title1 = new Paragraph("The most sold " + type, titleFont1);
            title1.setAlignment(Element.ALIGN_CENTER);
            title1.setSpacingAfter(20);
            document.add(title1);

            var items1 = itemRepository.findItemsNotSoldInLastSixMonths();

            // Creating table with 5 columns
            PdfPTable table1 = new PdfPTable(new float[]{2, 4, 2, 2});
            table1.setWidthPercentage(100);
            table1.setSpacingBefore(10f);
            table1.setSpacingAfter(10f);

            // Adding table headers
            String[] headers1 = {"Name", "Description", "Type", "Price"};
            for (String header : headers1) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table1.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items1) {

                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont1));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table1.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont1));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table1.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont1));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table1.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table1.addCell(cell);
            }

            document.add(table1);


            Font titleFont2 = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 76, 153));
            Font textFont2 = new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(50, 50, 50));
            Font headerFont2 = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);

            // Adding a title
            type = type == null ? "item" : type;
            Paragraph title2 = new Paragraph("The most sold " + type, titleFont2);
            title2.setAlignment(Element.ALIGN_CENTER);
            title2.setSpacingAfter(20);
            document.add(title2);

            var items2 = itemRepository.findItemsWithinBudget(price);

            // Creating table with 5 columns
            PdfPTable table2 = new PdfPTable(new float[]{2, 4, 2, 2});
            table2.setWidthPercentage(100);
            table2.setSpacingBefore(10f);
            table2.setSpacingAfter(10f);

            // Adding table headers
            String[] headers2 = {"Name", "Description", "Type", "Price"};
            for (String header : headers2) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont2));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(5);
                table2.addCell(headerCell);
            }
            System.out.println(type);
            for (Item item : items2) {
                if((type.equals("FOOD") || type.equals("DRINK") ) && !type.equals(item.getType().toString())) continue;
                // Name
                PdfPCell cell = new PdfPCell(new Phrase(item.getName(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Description
                cell = new PdfPCell(new Phrase(item.getDescription(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Type
                cell = new PdfPCell(new Phrase(item.getType().toString(), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);

                // Price
                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice()), textFont2));
                cell.setBorder(Rectangle.BOX);
                cell.setPadding(5);
                table2.addCell(cell);
            }

            document.add(table2);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

}
