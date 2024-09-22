package com.app.model;

public class Carrinho {
    private int id;
    private User user;
    private Produto[] produtos;
    private float valorTotal;
    private float valor_Frete;

    public Carrinho(User user, Produto[] produtos, float valorTotal, float valor_Frete) {
        this.id = id;
        this.user = user;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
        this.valor_Frete = valor_Frete;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produto[] getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto[] produtos) {
        this.produtos = produtos;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getValor_Frete() {
        return valor_Frete;
    }

    public void setValor_Frete(float valor_Frete) {
        this.valor_Frete = valor_Frete;
    }
}
