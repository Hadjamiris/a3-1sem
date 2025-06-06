package view;

import dao.CategoriaDAO;
import model.Categoria;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCategoria extends JFrame {
    private CategoriaDAO categoriaDAO;

    // Componentes da interface
    private JTextField txtNome;
    private JComboBox<String> cbTamanho, cbEmbalagem;
    private JButton btnCadastrar, btnVoltar;

    public TelaCadastroCategoria() {
        // Inicializar DAO
        categoriaDAO = new CategoriaDAO();

        // Configurar a janela
        setTitle("Cadastrar Categoria");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Categoria", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Campo Nome
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nome da Categoria:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        // Campo Tamanho
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tamanho embalagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        cbTamanho = new JComboBox<>(new String[]{"Pequeno", "Médio", "Grande"});
        add(cbTamanho, gbc);

        // Campo Embalagem
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Material embalagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        cbEmbalagem = new JComboBox<>(new String[]{"Lata", "Vidro", "Plástico"});
        add(cbEmbalagem, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(painelBotoes, gbc);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> {
            try {
                Categoria categoria = new Categoria();
                categoria.setNome(txtNome.getText());
                categoria.setTamanho((String) cbTamanho.getSelectedItem());
                categoria.setEmbalagem((String) cbEmbalagem.getSelectedItem());
                categoriaDAO.inserir(categoria);
                JOptionPane.showMessageDialog(this, "Categoria cadastrada com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar categoria: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void limparCampos() {
        txtNome.setText("");
        cbTamanho.setSelectedIndex(0);
        cbEmbalagem.setSelectedIndex(0);
    }
}