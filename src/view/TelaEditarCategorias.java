package view;

import dao.CategoriaDAO;
import model.Categoria;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class TelaEditarCategorias extends JFrame {
    private CategoriaDAO categoriaDAO;

    // Componentes da interface
    private JList<Categoria> listaCategorias;
    private DefaultListModel<Categoria> modelLista;
    private JTextField txtNome, txtTamanho, txtEmbalagem;
    private JButton btnAtualizar, btnExcluir, btnVoltar;

    public TelaEditarCategorias() {
        // Inicializar DAO
        categoriaDAO = new CategoriaDAO();

        // Configurar a janela
        setTitle("Editar Categorias");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar um painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel da lista de categorias
        JPanel painelLista = new JPanel(new BorderLayout());
        modelLista = new DefaultListModel<>();
        listaCategorias = new JList<>(modelLista);
        listaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCategorias.addListSelectionListener(e -> carregarDadosCategoria());
        painelLista.add(new JLabel("Selecione uma categoria:"), BorderLayout.NORTH);
        painelLista.add(new JScrollPane(listaCategorias), BorderLayout.CENTER);
        painelPrincipal.add(painelLista, BorderLayout.WEST);

        // Painel de edição
        JPanel painelEdicao = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelEdicao.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNome = new JTextField(20);
        painelEdicao.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelEdicao.add(new JLabel("Tamanho:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtTamanho = new JTextField(20);
        painelEdicao.add(txtTamanho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelEdicao.add(new JLabel("Embalagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtEmbalagem = new JTextField(20);
        painelEdicao.add(txtEmbalagem, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        painelEdicao.add(painelBotoes, gbc);

        painelPrincipal.add(painelEdicao, BorderLayout.CENTER);

        // Adicionar o painel principal ao JFrame
        add(painelPrincipal);

        // Ações dos botões
        btnAtualizar.addActionListener(e -> {
            try {
                Categoria categoria = listaCategorias.getSelectedValue();
                if (categoria != null) {
                    categoria.setNome(txtNome.getText());
                    categoria.setTamanho(txtTamanho.getText());
                    categoria.setEmbalagem(txtEmbalagem.getText());
                    categoriaDAO.atualizar(categoria);
                    JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso!");
                    atualizarLista();
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione uma categoria para atualizar.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                Categoria categoria = listaCategorias.getSelectedValue();
                if (categoria != null) {
                    int confirmacao = JOptionPane.showConfirmDialog(this,
                            "Tem certeza que deseja excluir a categoria '" + categoria.getNome() + "'?",
                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        categoriaDAO.excluir(categoria.getId());
                        JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
                        atualizarLista();
                        limparCampos();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione uma categoria para excluir.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> dispose());

        // Carregar a lista inicial
        atualizarLista();
    }

    private void atualizarLista() {
        modelLista.clear();
        List<Categoria> categorias = categoriaDAO.listar();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma categoria cadastrada.");
        } else {
            for (Categoria c : categorias) {
                modelLista.addElement(c);
            }
        }
    }

    private void carregarDadosCategoria() {
        Categoria categoria = listaCategorias.getSelectedValue();
        if (categoria != null) {
            txtNome.setText(categoria.getNome());
            txtTamanho.setText(categoria.getTamanho());
            txtEmbalagem.setText(categoria.getEmbalagem());
        } else {
            limparCampos();
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtTamanho.setText("");
        txtEmbalagem.setText(""); // Linha corrigida
    }
}