package com.demo.ocr.service;

import com.demo.ocr.service.serviceImpl.OCRServiceImpl;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface PDFService {
    String extractTextFromPDF(MultipartFile file) throws IOException;
    List<String> extractTextFromPDFImages(MultipartFile file, OCRService ocrService) throws IOException, TesseractException;
    MultipartFile createMultipartFileFromImage(BufferedImage image, String fileName) throws IOException;
}
