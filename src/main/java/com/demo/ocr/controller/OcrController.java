package com.demo.ocr.controller;

import com.demo.ocr.model.ImageTextDto;
import com.demo.ocr.utils.CommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/version/1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class OcrController {

    final CommonService commonService;

    @PostMapping(value = "/images/extract", consumes = {"multipart/form-data"})
    @ResponseStatus(value = HttpStatus.OK)
    public ImageTextDto extractText(MultipartFile file) {
        return commonService.processUploadedFile(file);
    }

}
