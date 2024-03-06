package com.generation.identificai.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.generation.identificai.model.Servicos;

public interface ServicosRepository extends JpaRepository<Servicos, Long> {
	
    public List<Servicos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

    public List<Servicos> findByPreco(BigDecimal preco);

    public List<Servicos> findByUsuarioId(Long usuarioId);

	public List<Servicos> findByPrecoGreaterThan(BigDecimal zero);

	public List<Servicos> findByUsuarioIdAndPrecoEquals(Long usuarioId, BigDecimal zero);

	public List<Servicos> findByUsuarioIdAndPrecoGreaterThan(Long usuarioId, BigDecimal zero);
}
