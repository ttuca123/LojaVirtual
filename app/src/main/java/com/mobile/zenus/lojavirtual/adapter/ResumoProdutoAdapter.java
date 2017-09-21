package com.mobile.zenus.lojavirtual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.presenter.ActResumoPresenter;
import com.mobile.zenus.lojavirtual.view.ActResumoView;
import com.mobile.zenus.lojavirtual.vo.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tuca on 18/09/2017.
 */

public class ResumoProdutoAdapter extends ArrayAdapter<Produto> implements ActResumoPresenter{

    private  Context context;
    private  List<Produto> produtos;


    private LayoutInflater inflater;
    private ActResumoView resumoView;



    public ResumoProdutoAdapter (ActResumoView resumoView, Context context, List<Produto> produtos){
        super(context, R.layout.compra_resumo_item, produtos);
        this.context = context;
        this.produtos = new ArrayList<Produto>();
        this.produtos.addAll(produtos);
        this.resumoView = resumoView;

    }





    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;

        if(listView == null){

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            listView = inflater.inflate(R.layout.compra_resumo_item, null);
        }

         TextView mDescricao = (TextView) listView.findViewById(R.id.txtDescricao);

         TextView mValorUnitario = (TextView) listView.findViewById(R.id.txtPrecoUnitario);
         final TextView mTotalParcial = (TextView) listView.findViewById(R.id.txtTotalParcial);
         EditText mEdtQuantidade = (EditText) listView.findViewById(R.id.edtQtd);

         mDescricao.setText((position+1) + " - "+produtos.get(position).getDescProduto());

         mValorUnitario.setText(produtos.get(position).getPreco().toString());

         int valor = mEdtQuantidade.getText().toString().isEmpty()?1:Integer.parseInt(mEdtQuantidade.getText().toString());

         produtos.get(position).setQuantidade(valor);

         produtos.get(position).setPreco(Double.parseDouble(mValorUnitario.getText().toString()));

         Double totalParcial = calculaTotalParcial(produtos.get(position).getQuantidade()
                , produtos.get(position).getPreco());

         mTotalParcial.setText(totalParcial.toString());

        mEdtQuantidade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if(!v.getText().toString().isEmpty() && !v.getText().toString().equals("0")){

                    int quantidade = Integer.parseInt(v.getText().toString());

                    Double totalParcial = calculaTotalParcial(quantidade,
                            produtos.get(position).getPreco());

                    mTotalParcial.setText(totalParcial.toString());

                    resumoView.habilitarRecalculo();

                    notifyDataSetChanged();

                }

                   return false;
            }
        });

        return listView;
    }

    private Double calculaTotalParcial(int quantidade, Double valorUnitario){

        if(quantidade==0){
            quantidade=1;
        }

        return quantidade*valorUnitario;
    }
}
