package br.com.magna.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.entity.UsuarioEntity;
import br.com.magna.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<UsuarioDto> listPage(Pageable paginacao) throws Exception {
		try {
			Page<UsuarioEntity> usuarios = usuarioRepository.findAll(paginacao);
			logger.info("Usuarios listados");
			return usuarios.map(map -> modelMapper.map(map, UsuarioDto.class));
			//return pageDto(usuarios);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}

	// Listando usuario por login
	public UsuarioDto getLogin(String login) throws NotFoundException, Exception {
		try {
			Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByLogin(login);
			UsuarioEntity usuario = usuarioOptional.orElseThrow(() -> new NotFoundException());
			UsuarioDto usuarioDto = convertDto(usuario);
			logger.info("Usuario com login: " + "'" + login + "'" + " listado");
			return usuarioDto;
		} catch (NotFoundException ex) {
			logger.error(ex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	// Verificando se o usuario já tem um cadastro
	public Boolean verificaUsuario(UsuarioDto loginUsuario) throws NotFoundException, Exception {
		Boolean verificaUser = false;
		try {
			verificaUser = usuarioRepository.existsByLogin(loginUsuario.getLogin());
			return verificaUser;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return verificaUser;
		}
	}

	// Criando usuario
	public UsuarioDto createUsuarioDto(UsuarioDto usuarioDto) throws IllegalArgumentException, Exception {
		try {
			if (verificaUsuario(usuarioDto)) {
				logger.info("Usuario já cadastrado");
				return null;
			} else {
				UsuarioEntity usuario = usuarioRepository.save(convertEntity(usuarioDto));
				UsuarioDto usuarioDtoSave = convertDto(usuario);
				logger.info("Usuario cadastrado com sucesso");
				return usuarioDtoSave;
			}
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	// Atualizando evento
	public UsuarioDto update(String login, UsuarioDto usuarioDto) throws NotFoundException, 
	EntityNotFoundException ,Exception {
		try {
			UsuarioEntity usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new NotFoundException());
			UsuarioDto usuarioDtoAntigo = convertDto(usuario);
			BeanUtils.copyProperties(usuarioDto, usuarioDtoAntigo, "login");
			UsuarioEntity convertEntity = convertEntity(usuarioDto);
			convertEntity.setId(usuario.getId());
			UsuarioEntity usuarioAtualizado = usuarioRepository.save(convertEntity);
			logger.info("Usuario com login: " + "'" + usuarioDto.getLogin() + "'" + " atualizado");
			return convertDto(usuarioAtualizado);
		} catch(NotFoundException ex) {
			logger.error("NotFound");
		} catch(EntityNotFoundException ex) {
			logger.error("Evento não encontrado");
		} catch(Exception ex) {
			logger.error("Evento não encontrado");
		}	
		return null;
	}

	// Deletando um usuario
	public void delete(String login) throws Exception {
		try {
			usuarioRepository.deleteByLogin(login);
			logger.info("Usuario com login: " + login+ " deletado");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	// Construtor do UsuarioDto
	public UsuarioDto usuarioDto(UsuarioEntity usuario) {
		UsuarioDto dto = new UsuarioDto();
		dto.setLogin(usuario.getLogin());
		dto.setSenha(usuario.getSenha());
		dto.setEvento(usuario.getEvento());
		return dto;
	}

	// Conversor ModelMapper de Entity para Dto
	public UsuarioDto convertDto(UsuarioEntity usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	// Conversor ModelMapper de Entity para Dto
	public UsuarioEntity convertEntity(UsuarioDto usuario) {
		return modelMapper.map(usuario, UsuarioEntity.class);
	}

	// Conversor Page de Entity para Dto
	public Page<UsuarioDto> pageDto(Page<UsuarioEntity> usuarioEntity) {
		return usuarioEntity.map(convert -> this.usuarioDto(convert));
	}

}
