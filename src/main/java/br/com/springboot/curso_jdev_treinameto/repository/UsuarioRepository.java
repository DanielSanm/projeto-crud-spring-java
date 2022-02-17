package br.com.springboot.curso_jdev_treinameto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.curso_jdev_treinameto.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")	// notação para SQL
	// o trim() ignora os espaços e o upper() deixa tudo maiusculo
	List<Usuario> buscarPorNome(String nome);
}
