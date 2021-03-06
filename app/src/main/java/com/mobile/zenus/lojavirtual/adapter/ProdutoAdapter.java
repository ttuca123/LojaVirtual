package com.mobile.zenus.lojavirtual.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.vo.Produto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tuca on 13/09/2017.
 */



public class ProdutoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Produto> produtos;

    private LayoutInflater inflater;

    public ProdutoAdapter(Context context, ArrayList<Produto> produtos){
        this.context=context;
        this.produtos = produtos;

    }

    public ArrayList<Produto> getProdutos(){

        return produtos;
    }

    public ArrayList<Produto> getProdutosSelecionados(){
        ArrayList<Produto> produtosSelecionados = new ArrayList<Produto>();

        for(Produto produto: produtos){
            if(produto.isSelecionado()){
                produtosSelecionados.add(produto);
            }
        }

        return produtosSelecionados;
    }


    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {

        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return produtos.get(position).getSeqProduto();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

       View gridView = convertView;

        if(convertView == null){


            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.produto_item, null);
        }

       final View textView = gridView;

        ImageView mImagemProduto = (ImageView) gridView.findViewById(R.id.imgProduto);
        TextView  mPreco = (TextView) gridView.findViewById(R.id.txtDescPreco);
        TextView  mDescricao = (TextView) gridView.findViewById(R.id.txtDescricao);
        final CheckBox mChkProduto = (CheckBox) gridView.findViewById(R.id.chkProduto);


        mChkProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mChkProduto.isChecked()){

                    Toast.makeText(textView.getContext(), "Produto "+produtos.get(position).getDescProduto() +" foi selecionado", Toast.LENGTH_SHORT).show();
                    produtos.get(position).setSelecionado(Boolean.TRUE);


                }else{
                    Toast.makeText(textView.getContext(), "Produto "+produtos.get(position).getDescProduto() +" foi retirado", Toast.LENGTH_SHORT).show();
                    produtos.get(position).setSelecionado(Boolean.FALSE);
                }
            }
        });
        mImagemProduto.setImageResource(R.drawable.common_google_signin_btn_icon_light);

        Picasso.with(gridView.getContext())
                .load(produtos.get(position).getPathImagem()).into(mImagemProduto);

        mPreco.setText(produtos.get(position).getPreco().toString());

        mDescricao.setText(produtos.get(position).getDescProduto());




        return gridView;
    }
}
