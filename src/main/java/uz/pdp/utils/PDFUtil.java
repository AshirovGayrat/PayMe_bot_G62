package uz.pdp.utils;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.entity.Transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class PDFUtil {

    public static SendDocument createAndBuyPdf(Transfer transfer, Long chatId) {


        String resourcePath = Paths.get("src/main/resources", "reciept.pdf").toString();
        File file = new File(resourcePath);
        file.getParentFile().mkdirs();

        try {
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Bu pul o'tkazmaning cheki."));
            document.add(new Paragraph("Jo'natuvchining karta raqami : " + transfer.getFrom()));
            document.add(new Paragraph("Qabul qilayotganni karta raqami : " + transfer.getTo()));
            document.add(new Paragraph("Jo'natilgan mablag' : " + transfer.getAmount()));
            document.add(new Paragraph("Sana: " + transfer.getTime()));

            document.close();
            System.out.println("✅ PDF yaratildi: " + resourcePath);

            return sendPdf(chatId);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public static SendDocument sendPdf( Long chatId ) {

        System.out.println("send Pdf");

        String resourcePAth = Paths.get("src/main/resources", "reciept.pdf").toString();
        File file = new File(resourcePAth);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(file));

        return sendDocument;
    }

}
