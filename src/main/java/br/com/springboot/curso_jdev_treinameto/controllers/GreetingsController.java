package br.com.springboot.curso_jdev_treinameto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinameto.models.Usuario;
import br.com.springboot.curso_jdev_treinameto.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
	// método GET
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET) 
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    public String retornaOlaMundo(@PathVariable String nome) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	return "Ola mundo " + nome;
    }
    
    
    @GetMapping(value = "listatodos")	// nosso primeiro método de API
    @ResponseBody			// retorna os dados para o corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuarios() {
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	// retorna a lista em JSON -> ResponseEntity
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    
    @PostMapping(value = "salvar") // mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // recebe os dados para salvar
    	Usuario user = usuarioRepository.save(usuario); // o método save, salva e retorna o objeto
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete") // mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<String> deletar(@RequestParam Long iduser) { // recebe os dados para salvar
    	usuarioRepository.deleteById(iduser); // o método save, salva e retorna o objeto
    	return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.CREATED);
    }
    // Obs.: nesse caso não é legal passar o id do usuário por URL, pois uma API
    // deve esconder seus parametros.
    
    @GetMapping(value = "buscaruserid") // mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Usuario> buscarId(@RequestParam(name = "iduser") Long idUser) { // recebe os dados para salvar
    	Usuario usuario = usuarioRepository.findById(idUser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar") // mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // recebe os dados para salvar
    	
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("id não foi informado", HttpStatus.BAD_REQUEST);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario); // ele vai salvar e rodar no banco
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "buscarPorNome") // mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { // recebe os dados para salvar
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	// o trim() elimina os espaços e toUpperCase() deixa tudo maiusculo
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
}
