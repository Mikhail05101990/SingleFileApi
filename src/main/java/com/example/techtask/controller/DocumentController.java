package com.example.techtask.controller;

import com.example.techtask.service.CrptApi;
import com.example.techtask.model.Document;
import com.example.techtask.repository.DocumentRepository;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1")
public class DocumentController {
  private CrptApi docService;

  public DocumentController()
  {
    docService = new CrptApi(TimeUnit.MINUTES, 3);
  }

  @PostMapping("create-document")
  public void postMethodName(@RequestBody Document doc, @RequestParam String signature) {
    docService.CreateDocument(doc);
  }
}
