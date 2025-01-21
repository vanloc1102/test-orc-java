package com.demo.ocr.service;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OCRService {
    String extractTextFromImage(MultipartFile file) throws IOException, TesseractException;
}
