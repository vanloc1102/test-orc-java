package com.demo.ocr.utils;

import com.demo.ocr.model.ImageTextDto;
import com.demo.ocr.service.OCRService;
import com.demo.ocr.service.PDFService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonService.class);
    final OCRService ocrService;
    final PDFService pdfService;

    public ImageTextDto processUploadedFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Invalid file type");
        }

        String result;
        try {
            switch (contentType) {
                case "application/pdf":
                    result = handlePDFFile(file);
                    break;
                case "image/jpeg":
                case "image/png":
                case "image/gif":
                    result = handleImageFile(file);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }
        } catch (Exception e) {
            log.error("Error processing file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Error processing file: " + e.getMessage(), e);
        }
        
        return ImageTextDto.builder()
                .fileName(file.getOriginalFilename())
                .text(result)
                .build();
    }

    private String handlePDFFile(MultipartFile file) throws IOException, TesseractException {
        if (isPDFImageBased(file)) {
            List<String> ocrResults = pdfService.extractTextFromPDFImages(file, ocrService);
            return String.join("\n", ocrResults);
        }
        return pdfService.extractTextFromPDF(file);
    }

    private String handleImageFile(MultipartFile file) throws IOException, TesseractException {
        return ocrService.extractTextFromImage(file);
    }

    private boolean isPDFImageBased(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text.trim().isEmpty();
    }
}
