package com.elf.app.models.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;

public class PdfCompressor {

public static float FACTOR = 0.5f;

/**
 * Manipulates a PDF file src with the file dest as result
 * @param src the original PDF
 * @param dest the resulting PDF
 * @throws IOException
 * @throws DocumentException 
 */
public static void manipulatePdf(String src, String dest) throws IOException, DocumentException {
    PdfName key = new PdfName("ITXT_SpecialId");
    PdfName value = new PdfName("123456789");
    PdfReader reader = new PdfReader(src);
    int n = reader.getXrefSize();
    PdfObject object;
    PRStream stream;
    for (int i = 0; i < n; i++) {
        object = reader.getPdfObject(i);
        if (object == null || !object.isStream())
            continue;
        stream = (PRStream)object;
        PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
        if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
            PdfImageObject image = new PdfImageObject(stream);
            BufferedImage bi = image.getBufferedImage();
            if (bi == null) continue;
            int width = (int)(bi.getWidth() * FACTOR);
            int height = (int)(bi.getHeight() * FACTOR);
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
            Graphics2D g = img.createGraphics();
            g.drawRenderedImage(bi, at);
            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(img, "JPG", imgBytes);
            stream.clear();
            stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
            stream.put(PdfName.TYPE, PdfName.XOBJECT);
            stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
            stream.put(key, value);
            stream.put(PdfName.FILTER, PdfName.DCTDECODE);
            stream.put(PdfName.WIDTH, new PdfNumber(width));
            stream.put(PdfName.HEIGHT, new PdfNumber(height));
            stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
            stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
        }
    }
    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
    stamper.close();
    reader.close();
}
}