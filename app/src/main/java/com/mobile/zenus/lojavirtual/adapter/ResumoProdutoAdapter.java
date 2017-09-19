package com.mobile.zenus.lojavirtual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.vo.Produto;

import java.util.List;

/**
 * Created by Tuca on 18/09/2017.
 */

public class ResumoProdutoAdapter extends ArrayAdapter<Produto> {

    private final Context context;
    private final List<Produto> produtos;
    private LayoutInflater inflater;

    public ResumoProdutoAdapter (Context context, List<Produto> produtos){
        super(context, R.layout.compra_resumo_item, produtos);
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;

        if(listView == null){

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            listView = inflater.inflate(R.layout.compra_resumo_item, null);
        }

        TextView mDescricao = (TextView) listView.findViewById(R.id.txtDescricao);

        final TextView mValorUnitario = (TextView) listView.findViewById(R.id.txtPrecoUnitario);
        final TextView mTotal = (TextView) listView.findViewById(R.id.txtTotal);
        final EditText mEdtQuantidade = (EditText) listView.findViewById(R.id.edtQtd);

        mDescricao.setText((position+1) + " - "+produtos.get(position).getDescProduto());
        mValorUnitario.setText(produtos.get(position).getPreco().toString());

        mEdtQuantidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mEdtQuantidade.getText().length()>0){
                    Double total = Integer.parseInt(mEdtQuantidade.getText().toString())*Double.parseDouble(mValorUnitario.getText().toString());
                    mTotal.setText("R$ "+ total.toString());
                }
            }
        });



        return listView;
    }
}
