package bme.hw.pdf;

import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import bme.hw.entities.Order;
import bme.hw.order.OrderRepository;
import liquibase.pro.packaged.H;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final OrderRepository orderRepository;

    public PdfController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    @RoleSecured({ Role.ROLE_ADMIN })
    public ResponseEntity<byte[]> createPdf(@PathVariable("orderId") Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order is not present in database"));
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 500);
            contentStream.showText("OrderId: " + orderId);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 480);
            contentStream.showText("Time: " + order.getOrderTime());
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 460);
            contentStream.showText("Type: " + order.getOrderType());
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 440);
            contentStream.showText("User: " + order.getCustomer().getUsername());
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 420);
            contentStream.showText("Address: " + order.getAddress().getAddress());
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 400);
            contentStream.showText("Meal: " + order.getMeal().getName() + " Price: "
                    + (order.getMeal().getPrice() -(order.getMeal().getPrice() * (order.getCoupon() == null ? 0 : order.getCoupon().getPercentage()) / 100)));
            contentStream.endText();
            contentStream.close();
            document.save("order_" + orderId + "_" + UUID.randomUUID() +".pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Here you have to set the actual filename of your pdf
            String filename = "order_" + orderId + "_" + UUID.randomUUID() +".pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
            document.close();
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Error while generating pdf");
        }
    }
}
