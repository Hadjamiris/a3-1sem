package view;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;
import java.util.Map; // Importação necessária

public class TelaRelatorios extends JFrame {
    private ProdutoDAO produtoDAO;

    // Componentes da interface
    private JTextArea txtAreaRelatorio;
    private JButton btnListaPrecos, btnBalanco, btnAbaixoMinimo, btnAcimaMaximo, btnPorCategoria, btnVoltar;

    public TelaRelatorios() {
        // Inicializar DAO
        produtoDAO = new ProdutoDAO();

        // Configurar a janela
        setTitle("Relatórios de Estoque");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar um painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 10, 10));
        btnListaPrecos = new JButton("Lista de Preços");
        btnBalanco = new JButton("Balanço Físico/Financeiro");
        btnAbaixoMinimo = new JButton("Produtos Abaixo do Mínimo");
        btnAcimaMaximo = new JButton("Produtos Acima do Máximo");
        btnPorCategoria = new JButton("Quantidade por Categoria");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnListaPrecos);
        painelBotoes.add(btnBalanco);
        painelBotoes.add(btnAbaixoMinimo);
        painelBotoes.add(btnAcimaMaximo);
        painelBotoes.add(btnPorCategoria);
        painelBotoes.add(btnVoltar);
        painelPrincipal.add(painelBotoes, BorderLayout.WEST);

        // Área de texto para relatórios
        txtAreaRelatorio = new JTextArea(15, 40);
        txtAreaRelatorio.setEditable(false);
        painelPrincipal.add(new JScrollPane(txtAreaRelatorio), BorderLayout.CENTER);

        // Adicionar o painel ao JFrame
        add(painelPrincipal);

        // Ações dos botões
        btnListaPrecos.addActionListener(e -> gerarListaPrecos());
        btnBalanco.addActionListener(e -> gerarBalanco());
        btnAbaixoMinimo.addActionListener(e -> gerarAbaixoMinimo());
        btnAcimaMaximo.addActionListener(e -> gerarAcimaMaximo());
        btnPorCategoria.addActionListener(e -> gerarPorCategoria());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void gerarListaPrecos() {
        List<Produto> produtos = produtoDAO.listar();
        txtAreaRelatorio.setText("=== Lista de Preços ===\n");
        produtos.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
        for (Produto p : produtos) {
            txtAreaRelatorio.append(p.getNome() + " - Preço: R$" + p.getPrecoUnitario() +
                    " - Unidade: " + p.getUnidade() + " - Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria") + "\n");
        }
    }

    private void gerarBalanco() {
        List<Produto> produtos = produtoDAO.listar();
        txtAreaRelatorio.setText("=== Balanço Físico/Financeiro ===\n");
        produtos.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
        double totalEstoque = 0;
        for (Produto p : produtos) {
            double valorTotalProduto = p.getPrecoUnitario() * p.getQuantidade();
            totalEstoque += valorTotalProduto;
            txtAreaRelatorio.append(p.getNome() + " - Quantidade: " + p.getQuantidade() +
                    " - Valor Total: R$" + String.format("%.2f", valorTotalProduto) +
                    " - Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria") + "\n");
        }
        txtAreaRelatorio.append("=== Valor Total do Estoque: R$" + String.format("%.2f", totalEstoque) + " ===\n");
    }

    private void gerarAbaixoMinimo() {
        List<Produto> produtos = produtoDAO.listar();
        txtAreaRelatorio.setText("=== Produtos Abaixo do Mínimo ===\n");
        boolean encontrado = false;
        for (Produto p : produtos) {
            if (p.getQuantidade() < p.getQuantidadeMinima()) {
                encontrado = true;
                txtAreaRelatorio.append(p.getNome() + " - Mínima: " + p.getQuantidadeMinima() +
                        " - Estoque: " + p.getQuantidade() + "\n");
            }
        }
        if (!encontrado) {
            txtAreaRelatorio.append("Nenhum produto abaixo do mínimo.\n");
        }
    }

    private void gerarAcimaMaximo() {
        List<Produto> produtos = produtoDAO.listar();
        txtAreaRelatorio.setText("=== Produtos Acima do Máximo ===\n");
        boolean encontrado = false;
        for (Produto p : produtos) {
            if (p.getQuantidade() > p.getQuantidadeMaxima()) {
                encontrado = true;
                txtAreaRelatorio.append(p.getNome() + " - Máxima: " + p.getQuantidadeMaxima() +
                        " - Estoque: " + p.getQuantidade() + "\n");
            }
        }
        if (!encontrado) {
            txtAreaRelatorio.append("Nenhum produto acima do máximo.\n");
        }
    }

    private void gerarPorCategoria() {
        txtAreaRelatorio.setText("=== Quantidade por Categoria ===\n");
        Map<String, Integer> contagem = produtoDAO.contarProdutosPorCategoria(); // Chamada ao novo método

        if (contagem.isEmpty()) {
            txtAreaRelatorio.append("Nenhum produto encontrado ou erro ao contar por categoria.\n");
        } else {
            for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
                txtAreaRelatorio.append("Categoria: " + entry.getKey() + " - Total de Produtos: " + entry.getValue() + "\n");
            }
        }
    }
}