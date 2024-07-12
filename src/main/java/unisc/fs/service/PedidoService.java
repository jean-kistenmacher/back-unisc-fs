package unisc.fs.service;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.enterprise.context.ApplicationScoped;

import unisc.fs.dto.Sessao;
import unisc.fs.entity.PedidoEntity;
import unisc.fs.exception.PedidoNotFoundException;
import unisc.fs.repository.PedidoRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.Logger;

@ApplicationScoped
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private static final Logger LOGGER = Logger.getLogger(PedidoService.class);

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoEntity criarPedido(PedidoEntity pedidoEntity){
        pedidoRepository.persist(pedidoEntity);
        return pedidoEntity;
    }

    public List<PedidoEntity> findAll(){
        return pedidoRepository.findAll().list();
    }

    public PedidoEntity findById(UUID pedidoId) {
        return (PedidoEntity) pedidoRepository.findByIdOptional(pedidoId)
                .orElseThrow(PedidoNotFoundException::new);
    }

    public PedidoEntity atualizaPedido(UUID pedidoId, PedidoEntity pedidoEntity) {
        var pedido = findById(pedidoId);
        pedido.setStatus(pedidoEntity.getStatus());
        pedidoRepository.persist(pedido);
        return pedido;
    }

    public void deleteById(UUID pedidoId) {
        var pedido  = findById(pedidoId);
        pedidoRepository.deleteById(pedido.getId());
    }

    public List<PedidoEntity> findByUser(String usuario) {
        return pedidoRepository.list("usuario", usuario);
    }

    public List<PedidoEntity> findPedidoBySessao(Sessao sessao){
        return pedidoRepository.find("filme = ?1 and sessao = ?2 and date = ?3",sessao.getFilme(), sessao.getSessao(), sessao.getDate()).list();
    }

    public byte[] gerarComprovantePDF(UUID pedidoId) throws IOException {
        var pedido  = findById(pedidoId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Comprovante de Compra")
                .setFontColor(new DeviceRgb(0, 0, 255))
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        document.add(new Paragraph("Email do Cliente: " + pedido.getUsuario())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Filme: " + pedido.getFilme())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Sessão: " + pedido.getSessao())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Poltrona: " + pedido.getPoltrona())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Data: " + pedido.getDate())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));



        document.close();

        return outputStream.toByteArray();
    }

    public byte[] gerarBoletoPDF(UUID pedidoId) throws IOException {
        var pedido  = findById(pedidoId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("BOLETO")
                .setFontColor(new DeviceRgb(0, 0, 255))
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        document.add(new Paragraph("Email do Cliente: " + pedido.getUsuario())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Filme: " + pedido.getFilme())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Sessão: " + pedido.getSessao())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Poltrona: " + pedido.getPoltrona())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Data: " + pedido.getDate())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.add(new Paragraph("Valor: R$" + pedido.getPreco())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(5));

        document.close();

        return outputStream.toByteArray();
    }
}
