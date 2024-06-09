package com.microservice.jsontopdfsb.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.microservice.jsontopdfsb.model.Invoice;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class InvoiceService {

    private final List<Invoice> invoices = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Autowired
    private SpringTemplateEngine templateEngine;

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setId(idCounter.incrementAndGet());
        }
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices);
    }

    public Invoice getInvoiceById(Long id) {
        return invoices.stream()
                .filter(invoice -> invoice.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public byte[] generatePdf(Invoice invoice) throws Exception {
        Context context = new Context();
        context.setVariable("invoice", invoice);

        String processedHtml = templateEngine.process("invoice", context);
        OutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processedHtml);
        renderer.layout();
        renderer.createPDF(outputStream);

        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }
}