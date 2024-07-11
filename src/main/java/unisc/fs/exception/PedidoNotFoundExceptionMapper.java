package unisc.fs.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PedidoNotFoundExceptionMapper implements ExceptionMapper<PedidoNotFoundException> {
    @Override
    public Response toResponse(PedidoNotFoundException e) {
        return Response.ok("Pedido não encontrado").status(Response.Status.NOT_FOUND.getStatusCode(),"Pedido not found").build();
    }
}
