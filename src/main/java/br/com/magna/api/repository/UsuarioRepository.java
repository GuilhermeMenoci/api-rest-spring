package br.com.magna.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	@Query("SELECT u FROM UsuarioEntity u WHERE u.login = :login")
	UsuarioEntity findByLogin(String login);
	
	Boolean existsByLogin(String login);

	String deleteByLogin(String login);
}
