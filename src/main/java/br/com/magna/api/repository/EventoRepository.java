package br.com.magna.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.EventoEntity;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long>{
	List<EventoEntity> findByCidade(String cidade);
	
	//Optional<EventoEntity> findByCodigo(Long codigo);
	Optional<EventoEntity> findByCodigo(int codigo);
	
	//Boolean existsByCodigo(Long codigo);
	Boolean existsByCodigo(int codigo);
	
	//String deleteByCodigo(Long codigo);
	String deleteByCodigo(int codigo);
	
	Page<EventoEntity> findByCodigo(Long codigo, Pageable paginacao);
	Page<EventoEntity> findAll(Pageable paginacao);
}
