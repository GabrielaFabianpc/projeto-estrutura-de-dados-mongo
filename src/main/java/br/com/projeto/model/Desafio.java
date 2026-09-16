package br.com.projeto.model;

import java.util.List;

public record Desafio(String id, String nome, String cursoId, String periodoId, String turmaId, List<String> alunoIds) {
}
