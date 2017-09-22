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

/**
 * Created by Tuca on 18/09/2017.
 */

public class ResumoProdutoAdapter extends ArrayAdapter<Produto> implements ActResumoPresenter{

    private  Context context;

    private LayoutInflater inflater;
    private ActResumoView resumoView;
    private View listView;


    public ResumoProdutoAdapter (ActResumoView resumoView, Context context){
        super(context, R.layout.compra_resumo_item);
        this.context = context;
        this.resumoView = resumoView;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        listView = inflater.inflate(R.layout.compra_resumo_item, null);

    }

    @NonNull
    @Override
    public  View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listView = convertView;

        if(listView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            listView = inflater.inflate(R.layout.compra_resumo_item, null);
        }

        Produto produto = getItem(position);

        TextView mDescricao = (TextView) listView.findViewById(R.id.txtDescricao);

        TextView mValorUnitario = (TextView) listView.findViewById(R.id.txtPrecoUnitario);

        TextView mTotalParcial = (TextView) listView.findViewById(R.id.txtTotalParcial);

        EditText mEdtQuantidade = (EditText) listView.findViewById(R.id.edtQtd);

        mDescricao.setText(produto.getDescProduto());

        mValorUnitario.setText(produto.getPreco().toString());

        Double totalParcial = produto.getPreco()*produto.getQuantidade();

        mTotalParcial.setText(totalParcial.toString());

        if(mEdtQuantidade.getText().toString().isEmpty() || mEdtQuantidade.getText().toString().equals("0")){

            mEdtQuantidade.setError(listView.getResources().getString(R.string.msg_error_item));
            mEdtQuantidade.requestFocus();
            mValorUnitario.setText("####");
            mTotalParcial.setText("######");


            verificarOperacaoCalculo(mEdtQuantidade.getText().toString());
        }

        mEdtQuantidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                EditText editar = (EditText) view;

                if(!editar.getText().toString().isEmpty()){

                    int valor=Integer.parseInt(editar.getText().toString());

                    getItem(position).setQuantidade(valor);

                    notifyDataSetChanged();
                }

                verificarOperacaoCalculo(editar.getText().toString());
            }
        });

        mEdtQuantidade.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                EditText editText = (EditText) view;

                verificarOperacaoCalculo(editText.getText().toString());

                return false;
            }
        });


        mEdtQuantidade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView texto, int actionId, KeyEvent event) {

                if(!texto.getText().toString().isEmpty()){

                    int valor=Integer.parseInt(texto.getText().toString());

                    getItem(position).setQuantidade(valor);

                    notifyDataSetChanged();
                }

                verificarOperacaoCalculo(texto.getText().toString());


                return false;
            }
        });

        return listView;
    }

    private void verificarOperacaoCalculo(String texto){
        if(!texto.isEmpty()){
            resumoView.habilitarBotaoCalcularTotal();

            resumoView.habilitarBotaoFinalizar();
        }else{

            resumoView.desabilitarBotaoFinalizar();

            resumoView.desabilitarBotaoCalcularTotal();

        }
    }



    private Double calculaTotalParcial(int quantidade, Double valorUnitario){

        if(quantidade==0){
            quantidade=1;
        }

        return quantidade*valorUnitario;
    }
}
