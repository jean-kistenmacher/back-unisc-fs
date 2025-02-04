package unisc.fs.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import unisc.fs.dto.Sessao;
import unisc.fs.entity.PedidoEntity;
import unisc.fs.service.PedidoService;
import java.io.IOException;
import java.util.UUID;




@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

    private static final Logger LOGGER = Logger.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GET
    public Response findAll(){
        var pedidos= pedidoService.findAll();
        return Response.ok(pedidos).build();
    }

    @POST
    @Transactional
    public Response criarPedido(PedidoEntity pedidoEntity){
        return Response.ok(pedidoService.criarPedido(pedidoEntity)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID pedidoId){
        return Response.ok(pedidoService.findById(pedidoId)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizaPedido(@PathParam("id") UUID pedidoId, PedidoEntity pedidoEntity){
        return Response.ok(pedidoService.atualizaPedido(pedidoId, pedidoEntity)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") UUID pedidoId){
        pedidoService.deleteById(pedidoId);
        return Response.noContent().build();
    }

    @GET
    @Path("/usuario/{usuario}")
    public Response findByUser(@PathParam("usuario") String usuario){
        return Response.ok(pedidoService.findByUser(usuario)).build();
    }

    @POST
    @Path("/sessao")
    public Response findBySessao(Sessao sessao){
        return Response.ok(pedidoService.findPedidoBySessao(sessao)).build();
    }

    @GET
    @Path("/comprovante/{id}")
    @Produces("application/pdf")
    public Response gerarComprovantePDF(@PathParam("id") UUID pedidoId) throws IOException {
        byte[] pdfBytes = pedidoService.gerarComprovantePDF(pedidoId);
        return Response.ok(pdfBytes)
                .header("Content-Disposition", "attachment; filename=comprovante.pdf")
                .build();
    }

    @GET
    @Path("/boleto/{id}")
    @Produces("application/pdf")
    public Response gerarBoletoPDF(@PathParam("id") UUID pedidoId) throws IOException {
        byte[] pdfBytes = pedidoService.gerarBoletoPDF(pedidoId);
        return Response.ok(pdfBytes)
                .header("Content-Disposition", "attachment; filename=boleto.pdf")
                .build();
    }
}
