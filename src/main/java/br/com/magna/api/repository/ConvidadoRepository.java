package br.com.magna.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.ConvidadoEntity;

@Repository
public interface ConvidadoRepository extends JpaRepository<ConvidadoEntity, Long>{

	@Query("SELECT c FROM ConvidadoEntity c WHERE c.cpf = :cpf")
	ConvidadoEntity findByCpf(String cpf);
	
	Boolean existsByCpf(String cpf);
	
	String deleteByCpf(String cpf);
}
