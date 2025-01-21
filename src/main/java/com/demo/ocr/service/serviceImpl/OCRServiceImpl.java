package com.demo.ocr.service.serviceImpl;

import com.demo.ocr.service.OCRService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OCRServiceImpl implements OCRService {

    public String extractTextFromImage(MultipartFile file) throws IOException, TesseractException {
        Tesseract tesseract = new Tesseract();
        // Set the path to your tessdata directory
        tesseract.setDatapath("D:\\Learn\\Java\\Project\\ocr-demo\\tessdata");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setLanguage("jpn");
        // Save the uploaded file to a temporary location
        Path tempFile = Files.createTempFile("tempImage", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());
        // Perform OCR on the image
        String result = tesseract.doOCR(tempFile.toFile());
        // Delete the temporary file
        Files.delete(tempFile);
        return result;
    }
}
