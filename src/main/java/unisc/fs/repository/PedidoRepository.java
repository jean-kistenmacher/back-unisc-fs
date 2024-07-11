package unisc.fs.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import unisc.fs.entity.PedidoEntity;

import java.util.UUID;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<PedidoEntity, UUID> {
}
