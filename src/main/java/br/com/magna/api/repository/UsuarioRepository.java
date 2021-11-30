package br.com.magna.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.api.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	@Query("SELECT u FROM UsuarioEntity u WHERE u.login = :login")
	Optional<UsuarioEntity> findByLogin(String login);
	
	Boolean existsByLogin(String login);

	String deleteByLogin(String login);
	
	Page<UsuarioEntity> findByLogin(String login, Pageable paginacao);
}
