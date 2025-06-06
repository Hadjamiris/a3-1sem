package view;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        // Configurar a janela
        setTitle("Controle de Estoque - Menu Inicial");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Controle de Estoque", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitulo, gbc);

        // Botões de navegação
        JButton btnCadastrarCategoria = new JButton("Cadastrar Categoria");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(btnCadastrarCategoria, gbc);

        JButton btnEditarCategorias = new JButton("Editar Categorias");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btnEditarCategorias, gbc);

        JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btnCadastrarProduto, gbc);

        JButton btnListarProdutos = new JButton("Listar Produtos");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btnListarProdutos, gbc);

        JButton btnEntrada = new JButton("Registrar Entrada");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(btnEntrada, gbc);

        JButton btnSaida = new JButton("Registrar Saída");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(btnSaida, gbc);

        JButton btnRelatorios = new JButton("Relatórios");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(btnRelatorios, gbc);

        // Ações dos botões
        btnCadastrarCategoria.addActionListener(e -> new TelaCadastroCategoria().setVisible(true));
        btnEditarCategorias.addActionListener(e -> new TelaEditarCategorias().setVisible(true));
        btnCadastrarProduto.addActionListener(e -> new TelaCadastroProduto().setVisible(true));
        btnListarProdutos.addActionListener(e -> new TelaListarProdutos().setVisible(true));
        btnEntrada.addActionListener(e -> new TelaRegistrarEntrada().setVisible(true));
        btnSaida.addActionListener(e -> new TelaRegistrarSaida().setVisible(true));
        btnRelatorios.addActionListener(e -> new TelaRelatorios().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }
}