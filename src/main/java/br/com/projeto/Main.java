package br.com.projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.projeto.config.MongoConfig;
import br.com.projeto.model.Aluno;
import br.com.projeto.model.Curso;
import br.com.projeto.model.Desafio;
import br.com.projeto.model.Periodo;
import br.com.projeto.model.Turma;
import br.com.projeto.repository.AlunoRepository;
import br.com.projeto.repository.CursoRepository;
import br.com.projeto.repository.DesafioRepository;
import br.com.projeto.repository.PeriodoRepository;
import br.com.projeto.repository.TurmaRepository;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CursoRepository cursos = new CursoRepository(MongoConfig.database());

    private static final TurmaRepository turmas = new TurmaRepository(MongoConfig.database());

    private static final PeriodoRepository periodos = new PeriodoRepository(MongoConfig.database());

    private static final AlunoRepository alunos = new AlunoRepository(MongoConfig.database());

    private static final DesafioRepository desafios = new DesafioRepository(MongoConfig.database());

    public static void main(String[] args) {
        try {
            exibirCabecalho();

            int opcao;

            do {
                exibirMenuPrincipal();
                opcao = lerNumero();

                switch (opcao) {
                    case 1 -> crudCurso();
                    case 2 -> crudTurma();
                    case 3 -> crudPeriodo();
                    case 4 -> crudAluno();
                    case 5 -> crudDesafio();
                    case 6 -> listarAlunosTurma();
                    case 7 -> listarAlunosDesafio();
                    case 0 -> System.out.println("\nSistema encerrado com sucesso.");
                    default -> System.out.println("\n[!] Opção inválida. Tente novamente.");
                }

            } while (opcao != 0);

        } finally {
            MongoConfig.close();
        }
    }

    private static void exibirCabecalho() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("        SISTEMA ACADÊMICO - MONGODB");
        System.out.println("==============================================");
        System.out.println("Conectado ao banco: projeto_faculdade");
    }

    private static void exibirMenuPrincipal() {
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("                 MENU PRINCIPAL");
        System.out.println("----------------------------------------------");
        System.out.println("1 - Gerenciar Cursos");
        System.out.println("2 - Gerenciar Turmas");
        System.out.println("3 - Gerenciar Períodos");
        System.out.println("4 - Gerenciar Alunos");
        System.out.println("5 - Gerenciar Desafios");
        System.out.println();
        System.out.println("6 - Listar alunos de uma turma");
        System.out.println("7 - Listar alunos de um desafio");
        System.out.println();
        System.out.println("0 - Sair");
        System.out.println("----------------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirSubmenu(String entidade) {
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("              GERENCIAR " + entidade.toUpperCase());
        System.out.println("----------------------------------------------");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Excluir");
        System.out.println("0 - Voltar");
        System.out.println("----------------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerNumero() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    // ==================== CURSOS ====================

    private static void crudCurso() {
        exibirSubmenu("Cursos");

        switch (lerNumero()) {
            case 1 -> {
                Curso curso = cursos.create(
                        new Curso(null, lerTexto("Nome do curso: ")));

                System.out.println("\n[OK] Curso cadastrado com sucesso!");
                exibirCurso(curso);
            }

            case 2 -> {
                List<Curso> lista = cursos.findAll();

                titulo("CURSOS CADASTRADOS");

                if (lista.isEmpty()) {
                    System.out.println("Nenhum curso cadastrado.");
                } else {
                    lista.forEach(Main::exibirCurso);
                }
            }

            case 3 -> {
                String id = lerTexto("ID do curso: ");

                cursos.findById(id).ifPresentOrElse(
                        Main::exibirCurso,
                        () -> System.out.println("\n[!] Curso não encontrado."));
            }

            case 4 -> {
                String id = lerTexto("ID do curso: ");
                String nome = lerTexto("Novo nome: ");

                boolean atualizado = cursos.update(id, nome);

                System.out.println(atualizado
                        ? "\n[OK] Curso atualizado com sucesso!"
                        : "\n[!] Curso não encontrado.");
            }

            case 5 -> {
                String id = lerTexto("ID do curso: ");

                boolean excluido = cursos.delete(id);

                System.out.println(excluido
                        ? "\n[OK] Curso excluído com sucesso!"
                        : "\n[!] Curso não encontrado.");
            }

            case 0 -> {
                return;
            }

            default -> System.out.println("\n[!] Opção inválida.");
        }
    }

    private static void exibirCurso(Curso curso) {
        System.out.println();
        System.out.println("ID   : " + curso.id());
        System.out.println("Nome : " + curso.nome());
        System.out.println("----------------------------------------------");
    }

    // ==================== TURMAS ====================

    private static void crudTurma() {
        exibirSubmenu("Turmas");

        switch (lerNumero()) {
            case 1 -> {
                Turma turma = turmas.create(
                        new Turma(null, lerTexto("Nome da turma: ")));

                System.out.println("\n[OK] Turma cadastrada com sucesso!");
                exibirTurma(turma);
            }

            case 2 -> {
                List<Turma> lista = turmas.findAll();

                titulo("TURMAS CADASTRADAS");

                if (lista.isEmpty()) {
                    System.out.println("Nenhuma turma cadastrada.");
                } else {
                    lista.forEach(Main::exibirTurma);
                }
            }

            case 3 -> {
                String id = lerTexto("ID da turma: ");

                turmas.findById(id).ifPresentOrElse(
                        Main::exibirTurma,
                        () -> System.out.println("\n[!] Turma não encontrada."));
            }

            case 4 -> {
                String id = lerTexto("ID da turma: ");
                String nome = lerTexto("Novo nome: ");

                boolean atualizado = turmas.update(id, nome);

                System.out.println(atualizado
                        ? "\n[OK] Turma atualizada com sucesso!"
                        : "\n[!] Turma não encontrada.");
            }

            case 5 -> {
                String id = lerTexto("ID da turma: ");

                boolean excluido = turmas.delete(id);

                System.out.println(excluido
                        ? "\n[OK] Turma excluída com sucesso!"
                        : "\n[!] Turma não encontrada.");
            }

            case 0 -> {
                return;
            }

            default -> System.out.println("\n[!] Opção inválida.");
        }
    }

    private static void exibirTurma(Turma turma) {
        System.out.println();
        System.out.println("ID   : " + turma.id());
        System.out.println("Nome : " + turma.nome());
        System.out.println("----------------------------------------------");
    }

    // ==================== PERÍODOS ====================

    private static void crudPeriodo() {
        exibirSubmenu("Períodos");

        switch (lerNumero()) {
            case 1 -> {
                Periodo periodo = periodos.create(
                        new Periodo(null, lerTexto("Nome do período: ")));

                System.out.println("\n[OK] Período cadastrado com sucesso!");
                exibirPeriodo(periodo);
            }

            case 2 -> {
                List<Periodo> lista = periodos.findAll();

                titulo("PERÍODOS CADASTRADOS");

                if (lista.isEmpty()) {
                    System.out.println("Nenhum período cadastrado.");
                } else {
                    lista.forEach(Main::exibirPeriodo);
                }
            }

            case 3 -> {
                String id = lerTexto("ID do período: ");

                periodos.findById(id).ifPresentOrElse(
                        Main::exibirPeriodo,
                        () -> System.out.println("\n[!] Período não encontrado."));
            }

            case 4 -> {
                String id = lerTexto("ID do período: ");
                String nome = lerTexto("Novo nome: ");

                boolean atualizado = periodos.update(id, nome);

                System.out.println(atualizado
                        ? "\n[OK] Período atualizado com sucesso!"
                        : "\n[!] Período não encontrado.");
            }

            case 5 -> {
                String id = lerTexto("ID do período: ");

                boolean excluido = periodos.delete(id);

                System.out.println(excluido
                        ? "\n[OK] Período excluído com sucesso!"
                        : "\n[!] Período não encontrado.");
            }

            case 0 -> {
                return;
            }

            default -> System.out.println("\n[!] Opção inválida.");
        }
    }

    private static void exibirPeriodo(Periodo periodo) {
        System.out.println();
        System.out.println("ID   : " + periodo.id());
        System.out.println("Nome : " + periodo.nome());
        System.out.println("----------------------------------------------");
    }

    // ==================== ALUNOS ====================

    private static void crudAluno() {
        exibirSubmenu("Alunos");

        switch (lerNumero()) {
            case 1 -> {
                Aluno aluno = alunos.create(
                        new Aluno(
                                null,
                                lerTexto("Nome do aluno: "),
                                lerTexto("RA: "),
                                lerTexto("ID da turma: ")));

                System.out.println("\n[OK] Aluno cadastrado com sucesso!");
                exibirAluno(aluno);
            }

            case 2 -> {
                List<Aluno> lista = alunos.findAll();

                titulo("ALUNOS CADASTRADOS");

                if (lista.isEmpty()) {
                    System.out.println("Nenhum aluno cadastrado.");
                } else {
                    lista.forEach(Main::exibirAluno);
                }
            }

            case 3 -> {
                String id = lerTexto("ID do aluno: ");

                alunos.findById(id).ifPresentOrElse(
                        Main::exibirAluno,
                        () -> System.out.println("\n[!] Aluno não encontrado."));
            }

            case 4 -> {
                String id = lerTexto("ID do aluno: ");
                String nome = lerTexto("Novo nome: ");
                String ra = lerTexto("Novo RA: ");
                String turmaId = lerTexto("ID da turma: ");

                boolean atualizado = alunos.update(id, nome, ra, turmaId);

                System.out.println(atualizado
                        ? "\n[OK] Aluno atualizado com sucesso!"
                        : "\n[!] Aluno não encontrado.");
            }

            case 5 -> {
                String id = lerTexto("ID do aluno: ");

                boolean excluido = alunos.delete(id);

                System.out.println(excluido
                        ? "\n[OK] Aluno excluído com sucesso!"
                        : "\n[!] Aluno não encontrado.");
            }

            case 0 -> {
                return;
            }

            default -> System.out.println("\n[!] Opção inválida.");
        }
    }

    private static void exibirAluno(Aluno aluno) {
        System.out.println();
        System.out.println("ID       : " + aluno.id());
        System.out.println("Nome     : " + aluno.nome());
        System.out.println("RA       : " + aluno.ra());
        System.out.println("Turma ID : " + aluno.turmaId());
        System.out.println("----------------------------------------------");
    }

    // ==================== DESAFIOS ====================

    private static List<String> lerIdsAlunos() {
        String ids = lerTexto("IDs dos alunos separados por vírgula: ");

        return ids.isBlank()
                ? new ArrayList<>()
                : Arrays.stream(ids.split(","))
                        .map(String::trim)
                        .filter(id -> !id.isBlank())
                        .toList();
    }

    private static void crudDesafio() {
        exibirSubmenu("Desafios");

        switch (lerNumero()) {
            case 1 -> {
                Desafio desafio = desafios.create(
                        new Desafio(
                                null,
                                lerTexto("Nome do desafio: "),
                                lerTexto("ID do curso: "),
                                lerTexto("ID do período: "),
                                lerTexto("ID da turma: "),
                                lerIdsAlunos()));

                System.out.println("\n[OK] Desafio cadastrado com sucesso!");
                exibirDesafio(desafio);
            }

            case 2 -> {
                List<Desafio> lista = desafios.findAll();

                titulo("DESAFIOS CADASTRADOS");

                if (lista.isEmpty()) {
                    System.out.println("Nenhum desafio cadastrado.");
                } else {
                    lista.forEach(Main::exibirDesafio);
                }
            }

            case 3 -> {
                String id = lerTexto("ID do desafio: ");

                desafios.findById(id).ifPresentOrElse(
                        Main::exibirDesafio,
                        () -> System.out.println("\n[!] Desafio não encontrado."));
            }

            case 4 -> {
                String id = lerTexto("ID do desafio: ");

                Desafio desafio = new Desafio(
                        id,
                        lerTexto("Novo nome: "),
                        lerTexto("ID do curso: "),
                        lerTexto("ID do período: "),
                        lerTexto("ID da turma: "),
                        lerIdsAlunos());

                boolean atualizado = desafios.update(desafio);

                System.out.println(atualizado
                        ? "\n[OK] Desafio atualizado com sucesso!"
                        : "\n[!] Desafio não encontrado.");
            }

            case 5 -> {
                String id = lerTexto("ID do desafio: ");

                boolean excluido = desafios.delete(id);

                System.out.println(excluido
                        ? "\n[OK] Desafio excluído com sucesso!"
                        : "\n[!] Desafio não encontrado.");
            }

            case 0 -> {
                return;
            }

            default -> System.out.println("\n[!] Opção inválida.");
        }
    }

    private static void exibirDesafio(Desafio desafio) {
        System.out.println();
        System.out.println("ID         : " + desafio.id());
        System.out.println("Nome       : " + desafio.nome());
        System.out.println("Curso ID   : " + desafio.cursoId());
        System.out.println("Período ID : " + desafio.periodoId());
        System.out.println("Turma ID   : " + desafio.turmaId());
        System.out.println("Alunos IDs : " + desafio.alunoIds());
        System.out.println("----------------------------------------------");
    }

    // ==================== RELACIONAMENTOS ====================

    private static void listarAlunosTurma() {
        String id = lerTexto("ID da turma: ");

        List<Aluno> lista = alunos.findByTurma(id);

        titulo("ALUNOS DA TURMA");

        turmas.findById(id).ifPresent(
                turma -> System.out.println("Turma: " + turma.nome() + "\n"));

        if (lista.isEmpty()) {
            System.out.println("Nenhum aluno encontrado para esta turma.");
            return;
        }

        lista.forEach(aluno -> {
            System.out.println("Nome : " + aluno.nome());
            System.out.println("RA   : " + aluno.ra());
            System.out.println("----------------------------------------------");
        });
    }

    private static void listarAlunosDesafio() {
        String id = lerTexto("ID do desafio: ");

        desafios.findById(id).ifPresentOrElse(desafio -> {
            titulo("ALUNOS DO DESAFIO");

            System.out.println("Desafio: " + desafio.nome());
            System.out.println();

            if (desafio.alunoIds().isEmpty()) {
                System.out.println("Nenhum aluno relacionado a este desafio.");
                return;
            }

            desafio.alunoIds().forEach(alunoId -> alunos.findById(alunoId).ifPresent(aluno -> {
                System.out.println("Nome : " + aluno.nome());
                System.out.println("RA   : " + aluno.ra());
                System.out.println("----------------------------------------------");
            }));

        }, () -> System.out.println("\n[!] Desafio não encontrado."));
    }

    private static void titulo(String titulo) {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("              " + titulo);
        System.out.println("==============================================");
    }
}