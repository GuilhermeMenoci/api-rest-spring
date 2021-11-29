package br.com.magna.api.service;

import org.modelmapper.ModelMapper;
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

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<UsuarioDto> listPage(String login, Pageable paginacao) {
		if (login == null) {
			Page<UsuarioEntity> usuarios = usuarioRepository.findAll(paginacao);
			return pageDto(usuarios);
		} else {
			Page<UsuarioEntity> usuarios = usuarioRepository.findByLogin(login, paginacao);
			return pageDto(usuarios);
		}
	}

	// Listando usuario por login
	public UsuarioDto getLogin(String login) throws NotFoundException {
		UsuarioEntity usuario = usuarioRepository.findByLogin(login);
		UsuarioDto usuarioDto = convertDto(usuario);
		if (usuarioDto == null) {
			throw new NotFoundException();
		}
		return usuarioDto;
	}

	// Verificando se o usuario já tem um cadastro
	public Boolean verificaUsuario(UsuarioDto loginUsuario) throws NotFoundException {
		Boolean verificaUser = usuarioRepository.existsByLogin(loginUsuario.getLogin());
		return verificaUser;
	}

	// Criando usuario
	public UsuarioDto createUsuarioDto(UsuarioDto usuarioDto) throws NotFoundException {
		if (verificaUsuario(usuarioDto)) {
			System.out.println("Usuario já cadastrado");
			return null;
		} else {
			UsuarioEntity usuario = usuarioRepository.save(convertEntity(usuarioDto));
			UsuarioDto usuarioDtoSave = convertDto(usuario);
			return usuarioDtoSave;
		}
	}

	// Atualizando evento
	public UsuarioDto update(String login, UsuarioDto usuarioDto) throws NotFoundException {
		UsuarioEntity usuario = usuarioRepository.findByLogin(login);
		UsuarioDto usuarioDtoAntigo = convertDto(usuario);
		BeanUtils.copyProperties(usuarioDto, usuarioDtoAntigo, "login");
		UsuarioEntity convertEntity = convertEntity(usuarioDto);
		convertEntity.setId(usuario.getId());

		UsuarioEntity usuarioAtualizado = usuarioRepository.save(convertEntity);
		return convertDto(usuarioAtualizado);

	}

	// Deletando um usuario
	public void delete(String login) throws NotFoundException {
		usuarioRepository.deleteByLogin(login);
	}

	
	
	
	// CONVERSORES//

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

//	// Convertando a lista de Entity para Dto
//	public List<UsuarioDto> listDto(List<UsuarioEntity> usuario) {
//		List<UsuarioDto> user = usuario.stream().map(UsuarioDto::new).collect(Collectors.toList());
//		return user;
//	}
//	
//	// Convertando a Page de Entity para Dto
//	public Page<UsuarioDto> pageDto(Page<UsuarioEntity> usuario) {
//		// Page<EventoDto> eventos =
//		// evento.stream().map(EventoDto::new).collect(Collectors.toList());
//		return usuario.map(UsuarioDto::new);
//	}

}
