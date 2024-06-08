package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.LocalRepository;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFService {
    @Autowired
    private LocalRepository localRepository;

    public byte[] generatePDFForCity(String city) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            List<Local> locals = localRepository.findByCity(city);

            for (Local local : locals) {
                document.add(new Paragraph("Name: " + local.getName()));
                document.add(new Paragraph("Description: " + local.getDescription()));
                document.add(new Paragraph("Capacity: " + local.getCapacity()));
                document.add(new Paragraph("City: " + local.getCity()));
                document.add(new Paragraph("Country: " + local.getCountry()));
                document.add(Paragraph.getInstance("\n")); // Add a blank line between entries
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
