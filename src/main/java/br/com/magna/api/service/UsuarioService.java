package br.com.magna.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

	// Listando todos os usuarios Entity
	public List<UsuarioEntity> listEntity() {
		List<UsuarioEntity> user = usuarioRepository.findAll();
		return user;
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
		try {
			verificaUsuario(usuarioDto);

			UsuarioEntity usuario = usuarioRepository.save(convertEntity(usuarioDto));
			UsuarioDto usuarioDtoSave = convertDto(usuario);
			return usuarioDtoSave;
		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Usuario já cadastrado");
		}
		return null;
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

	// Conversor ModelMapper de Entity para Dto
	public UsuarioDto convertDto(UsuarioEntity usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	// Conversor ModelMapper de Entity para Dto
	public UsuarioEntity convertEntity(UsuarioDto usuario) {
		return modelMapper.map(usuario, UsuarioEntity.class);
	}

	// Convertando a lista de Entity para Dto
	public List<UsuarioDto> listDto(List<UsuarioEntity> usuario) {
		List<UsuarioDto> user = usuario.stream().map(UsuarioDto::new).collect(Collectors.toList());
		return user;
	}

}
