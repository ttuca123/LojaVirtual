package com.mobile.zenus.lojavirtual.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Tuca on 13/09/2017.
 */

public class Produto implements Parcelable {


    Long seqProduto;

    String descProduto;

    Double preco;

    Integer quantidade;

    boolean isSelecionado;

    String pathImagem;



    public Produto(){

    }

    protected Produto(Parcel in) {
        descProduto = in.readString();
        quantidade = in.readInt();
        isSelecionado = in.readByte() != 0;
        preco = in.readDouble();
        pathImagem = in.readString();
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public boolean isSelecionado() {
        return isSelecionado;
    }

    public void setSelecionado(boolean selecionado) {
        isSelecionado = selecionado;
    }



    public String getPathImagem() {
        return pathImagem;
    }

    public void setPathImagem(String pathImagem) {
        this.pathImagem = pathImagem;
    }

    public Long getSeqProduto() {
        return seqProduto;
    }

    public void setSeqProduto(Long seqProduto) {
        this.seqProduto = seqProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public Double getPreco() {
        if(preco==null){
            preco=0.0;
        }
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {

        if(quantidade==null){

            quantidade=new Integer(1);
        }

        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {



        this.quantidade = quantidade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descProduto);
        dest.writeInt(getQuantidade().intValue());
        dest.writeByte((byte) (isSelecionado ? 1 : 0));
        dest.writeDouble(preco);
        dest.writeString(pathImagem);
    }
}
