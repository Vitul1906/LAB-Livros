package br.vitor_costa_lemos.acervo.repositorio;

//Aluno: Vitor Costa Lemos / RA = 10438932

import br.vitor_costa_lemos.acervo.entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAutorIgnoreCase(String autor);
    List<Livro> findByAnoPublicacao(int anoPublicacao);
    List<Livro> findByTituloContainingIgnoreCase(String termo);
    boolean existsByTituloIgnoreCaseAndAutorIgnoreCase(String titulo, String autor);
}

