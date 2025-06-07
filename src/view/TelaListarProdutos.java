package view;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.border.Border; // Importação necessária
import java.awt.*;
import java.util.List;

public class TelaListarProdutos extends JFrame {
    private ProdutoDAO produtoDAO;

    // Componentes da interface
    private JTextArea txtAreaLista;
    private JButton btnVoltar;

    public TelaListarProdutos() {
        // Inicializar DAO
        produtoDAO = new ProdutoDAO();

        // Configurar a janela
        setTitle("Listar Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar um painel principal para conter os componentes
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aplicar a borda no painel

        // Área de texto para listar produtos
        txtAreaLista = new JTextArea(15, 50);
        txtAreaLista.setEditable(false);
        painelPrincipal.add(new JScrollPane(txtAreaLista), BorderLayout.CENTER);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        painelPrincipal.add(btnVoltar, BorderLayout.SOUTH);

        // Adicionar o painel principal ao JFrame
        add(painelPrincipal);

        // Ação do botão
        btnVoltar.addActionListener(e -> dispose());

        // Carregar e exibir produtos
        carregarProdutos();
    }

    private void carregarProdutos() {
        List<Produto> produtos = produtoDAO.listar();
        txtAreaLista.setText("");
        if (produtos.isEmpty()) {
            txtAreaLista.append("Nenhum produto cadastrado.\n");
        } else {
            for (Produto p : produtos) {
                txtAreaLista.append("ID: " + p.getId() + " - Nome: " + p.getNome() +
                        " - Quantidade: " + p.getQuantidade() + " - Preço: R$" + p.getPrecoUnitario() +
                        " - Unidade: " + p.getUnidade() +
                        " - Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria") +
                        " - Mínima: " + p.getQuantidadeMinima() + " - Máxima: " + p.getQuantidadeMaxima() + "\n");
            }
        }
    }
}