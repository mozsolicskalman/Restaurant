package bme.hw.pdf;

import bme.hw.entities.Order;
import bme.hw.order.OrderRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/pdf")
public class PdfController {
    private final OrderRepository orderRepository;

    public PdfController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    public String createPdf(@PathVariable("orderId") Long orderId ){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order is not present in database"));
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = null;
        String path = null;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.showText("OrderId: " + orderId);
            contentStream.newLine();
            contentStream.showText("Time: " + order.getOrderTime());
            contentStream.newLine();
            contentStream.showText("Type: " + order.getOrderType());
            contentStream.newLine();
            contentStream.showText("User: " + order.getCustomer().getUsername());
            contentStream.newLine();
            contentStream.showText("Address: " + order.getAddress().getAddress());
            contentStream.newLine();
            contentStream.showText("Meal: " + order.getMeal() + " Price: "
                    + (order.getMeal().getPrice() -(order.getMeal().getPrice() * order.getCoupon().getPercentage() / 100)));
            contentStream.endText();
            contentStream.close();
            path = "order_" + orderId +".pdf";
            document.save("order_" + orderId +".pdf");
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while generating pdf");
        }
        return path;
    }
}
