package view;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.Categoria;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroProduto extends JFrame {
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

    // Componentes da interface
    private JTextField txtNome, txtPreco, txtUnidade, txtQuantidade, txtQuantidadeMinima, txtQuantidadeMaxima;
    private JComboBox<Categoria> cbCategorias;
    private JButton btnCadastrar, btnVoltar;

    public TelaCadastroProduto() {
        // Inicializar DAOs
        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();

        // Configurar a janela
        setTitle("Cadastrar Produto");
        setSize(400, 450); // Aumentei a altura para incluir o JComboBox
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Produto", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Campo Nome
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nome do Produto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        // Campo Preço Unitário
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Preço Unitário:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtPreco = new JTextField(20);
        add(txtPreco, gbc);

        // Campo Unidade
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Unidade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        txtUnidade = new JTextField(20);
        add(txtUnidade, gbc);

        // Campo Quantidade
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        txtQuantidade = new JTextField(20);
        add(txtQuantidade, gbc);

        // Campo Quantidade Mínima
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Quantidade Mínima:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        txtQuantidadeMinima = new JTextField(20);
        add(txtQuantidadeMinima, gbc);

        // Campo Quantidade Máxima
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Quantidade Máxima:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        txtQuantidadeMaxima = new JTextField(20);
        add(txtQuantidadeMaxima, gbc);

        // Campo Categoria
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Categoria:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        cbCategorias = new JComboBox<>();
        carregarCategorias();
        add(cbCategorias, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(painelBotoes, gbc);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> {
            try {
                Produto produto = new Produto();
                produto.setNome(txtNome.getText());
                produto.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
                produto.setUnidade(txtUnidade.getText());
                produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                produto.setQuantidadeMinima(Integer.parseInt(txtQuantidadeMinima.getText()));
                produto.setQuantidadeMaxima(Integer.parseInt(txtQuantidadeMaxima.getText()));
                // Associar a categoria selecionada
                produto.setCategoria((Categoria) cbCategorias.getSelectedItem());
                produtoDAO.inserir(produto);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtUnidade.setText("");
        txtQuantidade.setText("");
        txtQuantidadeMinima.setText("");
        txtQuantidadeMaxima.setText("");
    }

    private void carregarCategorias() {
        List<Categoria> categorias = categoriaDAO.listar();
        if (categorias.isEmpty()) {
            cbCategorias.addItem(new Categoria(0, "Nenhuma categoria cadastrada", "", ""));
            JOptionPane.showMessageDialog(this, "Nenhuma categoria cadastrada. Cadastre uma categoria primeiro!");
        } else {
            for (Categoria c : categorias) {
                cbCategorias.addItem(c);
            }
        }
    }
}