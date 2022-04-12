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

	public Page<UsuarioDto> listPage(Pageable paginacao) {

		Page<UsuarioEntity> usuarios = usuarioRepository.findAll(paginacao);
		logger.info("Usuarios listados");
		return usuarios.map(map -> modelMapper.map(map, UsuarioDto.class));

	}

	public UsuarioDto getLogin(String login) {

		UsuarioEntity usuarioOptional = usuarioRepository.findByLogin(login)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum usuario encontrado com login: " + login));
		return convertDto(usuarioOptional);

	}

	public Boolean verificaUsuario(UsuarioDto loginUsuario) {
		Boolean verificaUser = false;

		if (Boolean.TRUE.equals(usuarioRepository.existsByLogin(loginUsuario.getLogin()))) {
			logger.error("Já existe um usuario cadastrado com login: {}", loginUsuario.getLogin());
			throw new ParametroInvalidoException(
					"Já existe um usuario cadastrado com login: " + loginUsuario.getLogin());
		}

		return verificaUser;

	}

	public UsuarioDto createUsuarioDto(UsuarioDto usuarioDto) {

		verificaUsuario(usuarioDto);

		UsuarioEntity usuario = usuarioRepository.save(convertEntity(usuarioDto));
		UsuarioDto usuarioDtoSave = convertDto(usuario);
		logger.info("Usuario cadastrado com sucesso");
		return usuarioDtoSave;

	}

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

	public void delete(String login) {

		usuarioRepository.deleteByLogin(login);
		logger.info("Usuario deletado com login: {}", login);

	}

	public UsuarioDto convertDto(UsuarioEntity usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	public UsuarioEntity convertEntity(UsuarioDto usuario) {
		return modelMapper.map(usuario, UsuarioEntity.class);
	}

}
