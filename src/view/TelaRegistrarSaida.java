package view;

import dao.ProdutoDAO;
import model.Produto;
import service.EstoqueService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRegistrarSaida extends JFrame {
    private ProdutoDAO produtoDAO;
    private EstoqueService estoqueService;

    // Componentes da interface
    private JComboBox<String> cbProdutos;
    private JTextField txtQuantidade;
    private JButton btnRegistrar, btnVoltar;

    public TelaRegistrarSaida() {
        // Inicializar DAO e Serviço
        produtoDAO = new ProdutoDAO();
        estoqueService = new EstoqueService();

        // Configurar a janela
        setTitle("Registrar Saída de Produto");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Registrar Saída", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Campo Produto
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Produto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        cbProdutos = new JComboBox<>();
        carregarProdutos();
        add(cbProdutos, gbc);

        // Campo Quantidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtQuantidade = new JTextField(20);
        add(txtQuantidade, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar Saída");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(painelBotoes, gbc);

        // Ações dos botões
        btnRegistrar.addActionListener(e -> {
            try {
                String nomeProduto = (String) cbProdutos.getSelectedItem();
                List<Produto> produtos = produtoDAO.listar();
                Produto produto = produtos.stream()
                        .filter(p -> p.getNome().equals(nomeProduto))
                        .findFirst()
                        .orElse(null);
                if (produto != null) {
                    int quantidade = Integer.parseInt(txtQuantidade.getText());
                    estoqueService.saida(produto, quantidade);
                    JOptionPane.showMessageDialog(this, "Saída registrada com sucesso!");
                    txtQuantidade.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarProdutos() {
        List<Produto> produtos = produtoDAO.listar();
        for (Produto p : produtos) {
            cbProdutos.addItem(p.getNome());
        }
    }
}