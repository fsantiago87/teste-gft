package gft.teste.contabilidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gft.teste.contabilidade.entity.Contabilidade;

import java.util.Optional;

public interface ContabilidadeRepository extends JpaRepository<Contabilidade, Long> {

    Optional<Contabilidade> findByNome(String nome);
}
