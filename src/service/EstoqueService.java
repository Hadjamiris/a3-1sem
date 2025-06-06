package service;

import dao.ProdutoDAO;
import model.Produto;

public class EstoqueService {
    private ProdutoDAO produtoDAO;

    public EstoqueService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void entrada(Produto produto, int quantidade) {
        int novaQuantidade = produto.getQuantidade() + quantidade;
        produto.setQuantidade(novaQuantidade);

        produtoDAO.atualizar(produto);

        System.out.println("Entrada registrada: " + quantidade + " unidades adicionadas.");
        if (novaQuantidade > produto.getQuantidadeMaxima()) {
            System.out.println("⚠️ Atenção: quantidade ultrapassou o máximo permitido!");
        }
    }

    public void saida(Produto produto, int quantidade) {
        int novaQuantidade = produto.getQuantidade() - quantidade;
        if (novaQuantidade < 0) {
            System.out.println("❌ Erro: não é possível retirar mais do que existe no estoque.");
            return;
        }
        produto.setQuantidade(novaQuantidade);

        produtoDAO.atualizar(produto);

        System.out.println("Saída registrada: " + quantidade + " unidades removidas.");
        if (novaQuantidade < produto.getQuantidadeMinima()) {
            System.out.println("⚠️ Atenção: estoque abaixo do mínimo!");
        }
    }
}
