package br.com.magna.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.ConvidadoEntity;

@Repository
public interface ConvidadoRepository extends JpaRepository<ConvidadoEntity, Long>{

	@Query("SELECT c FROM ConvidadoEntity c WHERE c.cpf = :cpf")
	Optional<ConvidadoEntity> findByCpf(String cpf);
	
	Boolean existsByCpf(String cpf);
	
	String deleteByCpf(String cpf);
	
	Page<ConvidadoEntity> findByCpf(String cpf, Pageable paginacao);
}
