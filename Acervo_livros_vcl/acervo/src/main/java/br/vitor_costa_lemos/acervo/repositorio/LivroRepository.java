package br.vitor_costa_lemos.acervo.repositorio;

import br.vitor_costa_lemos.acervo.entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTituloIgnoreCaseAndAutorIgnoreCase(String titulo, String autor);

    List<Livro> findByAutorIgnoreCase(String autor);

    List<Livro> findByAnoPublicacao(int ano);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);
}

