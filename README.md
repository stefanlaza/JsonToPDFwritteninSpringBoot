
A simple microservice written in SpringBoot that prints PDFs from JSON

./mvnw spring-boot:run


Create Invoice:
curl -X POST http://localhost:8080/invoices -H "Content-Type: application/json" -d '{"customerName": "John Doe", "customerAddress": "123 Main St", "invoiceNumber": "INV001", "amount": 100.0}'

Get All Invoices:
curl http://localhost:8080/invoices

Get Invoice PDF:
curl http://localhost:8080/invoices/1/pdf --output invoice.pdf
