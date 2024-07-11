package unisc.fs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import unisc.fs.dto.Sessao;
import unisc.fs.entity.PedidoEntity;
import unisc.fs.exception.PedidoNotFoundException;
import unisc.fs.repository.PedidoRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PedidoService {

    private final PedidoRepository pedidoRepository;

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
}
