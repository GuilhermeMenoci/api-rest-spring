package br.com.magna.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.EventoEntity;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long>{
	List<EventoEntity> findByCidade(String cidade);
	
	@Query("SELECT e FROM EventoEntity e WHERE e.codigo = :codigo")
	Optional<EventoEntity> findByCodigo(Long codigo);
	
	Boolean existsByCodigo(Long codigo);
	
	String deleteByCodigo(Long codigo);
	
	Page<EventoEntity> findByCodigo(Long codigo, Pageable paginacao);
}
