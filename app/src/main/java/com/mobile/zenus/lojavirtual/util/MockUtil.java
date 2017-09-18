package com.mobile.zenus.lojavirtual.util;

import com.mobile.zenus.lojavirtual.vo.Produto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuca on 17/09/2017.
 */

public class MockUtil {

   static String prefixo = "https://firebasestorage.googleapis.com/v0/b/cloudstorage-77f43.appspot.com/o/frutas%2F";

   static  String sufixo = ".png?alt=media&token=1eec5dbd-afd9-4b9b-a606-09cf9e4da8bb";

    public static List<String> popularNomeProdutos() {

        List<String> nomeProdutos = new ArrayList<String>();
        nomeProdutos.add("abacaxi");
        nomeProdutos.add("bananas" );
        nomeProdutos.add("laranja");
        nomeProdutos.add("maça");
        nomeProdutos.add("morango");
        nomeProdutos.add("uva");


        return nomeProdutos;
    }

    public static List<Produto> popularProdutos() {

        List<String> nomesProdutos = popularNomeProdutos();

        List<Produto> produtos = new ArrayList<Produto>();

        for(String nomeProduto: nomesProdutos){
            
            Produto produto = new Produto();
            produto.setSeqProduto(1l);
            produto.setDescProduto(nomeProduto);
            produto.setPreco(new Double(3.5));
            produto.setPathImagem(prefixo+nomeProduto+sufixo);
            produtos.add(produto);

        }

        return produtos;
    }
}
