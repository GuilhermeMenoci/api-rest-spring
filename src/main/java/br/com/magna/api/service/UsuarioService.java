package br.com.magna.api.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.entity.UsuarioEntity;
import br.com.magna.api.exception.NaoEncontradoException;
import br.com.magna.api.exception.ParametroInvalidoException;
import br.com.magna.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<UsuarioDto> listPage(Pageable paginacao) {

		Page<UsuarioEntity> usuarios = usuarioRepository.findAll(paginacao);
		logger.info("Usuarios listados");
		return usuarios.map(map -> modelMapper.map(map, UsuarioDto.class));

	}

	// Listando usuario por login
	public UsuarioDto getLogin(String login) {

		UsuarioEntity usuarioOptional = usuarioRepository.findByLogin(login)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum usuario encontrado com login: " + login));
		UsuarioDto usuarioDto = convertDto(usuarioOptional);
		return usuarioDto;

	}

	// Verificando se o usuario já tem um cadastro
	public Boolean verificaUsuario(UsuarioDto loginUsuario) {
		Boolean verificaUser = false;

		if (Boolean.TRUE.equals(usuarioRepository.existsByLogin(loginUsuario.getLogin()))) {
			logger.error("Já existe um usuario cadastrado com login: {}", loginUsuario.getLogin());
			throw new ParametroInvalidoException(
					"Já existe um usuario cadastrado com login: " + loginUsuario.getLogin());
		}

		return verificaUser;

	}

	// Criando usuario
	public UsuarioDto createUsuarioDto(UsuarioDto usuarioDto) {

		verificaUsuario(usuarioDto);

		UsuarioEntity usuario = usuarioRepository.save(convertEntity(usuarioDto));
		UsuarioDto usuarioDtoSave = convertDto(usuario);
		logger.info("Usuario cadastrado com sucesso");
		return usuarioDtoSave;

	}

	// Atualizando evento
	public UsuarioDto update(String login, UsuarioDto usuarioDto) {

		UsuarioEntity usuario = usuarioRepository.findByLogin(login)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum usuario encontrado com login: " + login));
		UsuarioDto usuarioDtoAntigo = convertDto(usuario);
		BeanUtils.copyProperties(usuarioDto, usuarioDtoAntigo, "login");
		UsuarioEntity convertEntity = convertEntity(usuarioDto);
		convertEntity.setId(usuario.getId());
		UsuarioEntity usuarioAtualizado = usuarioRepository.save(convertEntity);
		logger.info("Usuario atualizado com sucesso");
		return convertDto(usuarioAtualizado);

	}

	// Deletando um usuario
	public void delete(String login) {

		usuarioRepository.deleteByLogin(login);
		logger.info("Usuario deletado com login: {}", login);

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
