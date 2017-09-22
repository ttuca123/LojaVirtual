package com.mobile.zenus.lojavirtual.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.adapter.ResumoProdutoAdapter;
import com.mobile.zenus.lojavirtual.view.ActResumoView;
import com.mobile.zenus.lojavirtual.vo.Produto;

import java.util.ArrayList;
import java.util.List;

public class ActResumoCompras extends AppCompatActivity implements ActResumoView {

    ListView mLstResumoCompras;
    Button mBtnFinalizarCompra;
    Button mBtnCalculaTotal;

    TextView mTotal;
    ResumoProdutoAdapter resumoProdutoAdapter;

    List<Produto> produtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resumo_compras);

        mLstResumoCompras = (ListView) findViewById(R.id.lstResumoCompras);

        mTotal = (TextView) findViewById(R.id.txtResumo);

        mBtnCalculaTotal = (Button) findViewById(R.id.btnCalculaTotal);

        Intent it = getIntent();

        produtos = new ArrayList<Produto>();

        final List<Produto>  produtosAdapter = it.getParcelableArrayListExtra("produtos");

        produtos.addAll(produtosAdapter);

        resumoProdutoAdapter = new ResumoProdutoAdapter(this, this);

        resumoProdutoAdapter.addAll(produtosAdapter);

        mLstResumoCompras.setAdapter(resumoProdutoAdapter);

        mBtnFinalizarCompra = (Button) findViewById(R.id.btnFinalizarCompra);

        mBtnCalculaTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (Double.parseDouble(calcularTotal())>0.0) {

//                    desabilitarBotaoCalcularTotal();

                    habilitarBotaoFinalizar();

                }
            }
        });
    }


    public void finalizarCompra(final View viewSecundaria){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewSecundaria.getContext());

        String total = calcularTotal();

        alertDialog.setTitle(getString(R.string.desc_total, total));

        alertDialog.setMessage(getString(R.string.confirm_transaction));

        alertDialog.setPositiveButton(getResources().getString(R.string.btn_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder alertDialogConfirmacao = new AlertDialog.Builder(viewSecundaria.getContext());

                alertDialogConfirmacao.setTitle(getResources().getString(R.string.purshase_made));

                alertDialogConfirmacao.setMessage(getResources().getString(R.string.purshase_sucessful));

                alertDialogConfirmacao.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent it = new Intent(ActResumoCompras.this, ActMain.class);

                        startActivity(it);
                    }
                });
                alertDialogConfirmacao.show();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.btn_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mTotal.setVisibility(View.GONE);

                habilitarRecalculo();
            }
        });


        alertDialog.show();

    }

    private boolean verificarValorVazio(){

        boolean isValorVazio=false;

        for(int i=0; i< mLstResumoCompras.getAdapter().getCount(); i++){

            Produto produto = (Produto) mLstResumoCompras.getAdapter().getItem(i);

            if(produto.getQuantidade()==0){
                isValorVazio=true;
                break;
            }
        }
        return isValorVazio;

    }


    private Double somarTotal(boolean isValorVazio){

        Double total = 0.0;

        if(!isValorVazio) {

            for (int i = 0; i < mLstResumoCompras.getAdapter().getCount(); i++) {

                Produto produto = (Produto) mLstResumoCompras.getAdapter().getItem(i);

                total += produto.getQuantidade()*produto.getPreco();
            }
        }

        return total;

    }



    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public String calcularTotal(){

        mTotal.setVisibility(View.VISIBLE);

        Double total =0.0;

        boolean isValorVazio = verificarValorVazio();

        if(!isValorVazio) {

            total = somarTotal(isValorVazio);

            preencherTotal(total.toString());

        }else{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle(getResources().getString(R.string.title_error));

            alert.setMessage(getResources().getString(R.string.msg_error));

            alert.setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    mTotal.setVisibility(View.GONE);
                    habilitarRecalculo();
                }
            });

            alert.show();
        }

        return total.toString();
    }

    @Override
    public void preencherTotal(String total) {

        String desc = getString(R.string.desc_total, total);

        mTotal.setText(desc);
    }

    @Override
    public void habilitarRecalculo() {

        mTotal.setText(getString(R.string.waiting_recalc));
        habilitarBotaoCalcularTotal();
        desabilitarBotaoFinalizar();
    }

    @Override
    public void habilitarBotaoFinalizar() {

        mBtnFinalizarCompra.setEnabled(true);
        mBtnFinalizarCompra.setAlpha(1);
    }

    @Override
    public void habilitarBotaoCalcularTotal() {

        mBtnCalculaTotal.setText(getString(R.string.recalc_total));
        mBtnCalculaTotal.setEnabled(true);
        mBtnCalculaTotal.setAlpha(1);
    }

    @Override
    public void desabilitarBotaoFinalizar() {

        mBtnFinalizarCompra.setAlpha(0.5f);
        mBtnFinalizarCompra.setEnabled(false);

    }

    @Override
    public void desabilitarBotaoCalcularTotal() {

        mBtnCalculaTotal.setText(getString(R.string.recalc_total));
        mBtnCalculaTotal.setEnabled(false);
        mBtnCalculaTotal.setAlpha(0.5f);
    }



}
