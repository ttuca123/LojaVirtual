package com.mobile.zenus.lojavirtual.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resumo_compras);

        mLstResumoCompras = (ListView) findViewById(R.id.lstResumoCompras);

        mTotal = (TextView) findViewById(R.id.txtResumo);

        mBtnCalculaTotal = (Button) findViewById(R.id.btnCalculaTotal);

        Intent it = getIntent();

        List<Produto>  produtos = it.getParcelableArrayListExtra("produtos");

        resumoProdutoAdapter = new ResumoProdutoAdapter(this, this, produtos);

        mLstResumoCompras.setAdapter(resumoProdutoAdapter);

        mLstResumoCompras.setClickable(true);

        mBtnFinalizarCompra = (Button) findViewById(R.id.btnFinalizarCompra);

        mBtnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View viewSecundaria) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewSecundaria.getContext());

                String total = calcularTotal();

                alertDialog.setTitle("Resumo Total: R$ " + total + " em compras");

                alertDialog.setMessage("Confirma transação?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder alertDialogConfirmacao = new AlertDialog.Builder(viewSecundaria.getContext());

                        alertDialogConfirmacao.setTitle("Compra Efetuada");

                        alertDialogConfirmacao.setMessage("Sua compra foi realizada com sucesso!!!");

                        alertDialogConfirmacao.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();



                        mTotal.setVisibility(View.GONE);

                        habilitarRecalculo();
                    }
                });


                alertDialog.show();
            }
        });
        mBtnCalculaTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Double total = Double.parseDouble(calcularTotal());

                if(total>0.0) {

                    desabilitarBotaoCalcularTotal();

                    habilitarBotaoFinalizar();

                }
            }
        });
    }



    @Override
    public String calcularTotal(){

        mTotal.setVisibility(View.VISIBLE);

        Double total=0.0;
        boolean isValorVazio = false;

        for(int i=0; i< mLstResumoCompras.getCount(); i++){

            Produto produto = (Produto) mLstResumoCompras.getItemAtPosition(i);

//            EditText qtd = (EditText) view.findViewById(R.id.edtQtd);
//
//            TextView preco = (TextView) view.findViewById(R.id.txtPrecoUnitario);

            if(produto.getQuantidade()==0){
                isValorVazio=true;
                break;
            }
            total += (produto.getQuantidade()*produto.getPreco());
        }


        if(!isValorVazio) {

            preencherTotal(total.toString());

        }else{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Erro");

            alert.setMessage("Por favor, preencha todos os campos referentes á quantidade dos produtos");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

        mTotal.setText("Total - R$ "+total);
    }

    @Override
    public void habilitarRecalculo() {

        mTotal.setText("Aguardando Recalculo");
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

        mBtnCalculaTotal.setText("Recalcular Total");
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

        mBtnCalculaTotal.setText("Recalcular Total");
        mBtnCalculaTotal.setEnabled(false);
        mBtnCalculaTotal.setAlpha(0.5f);
    }



}
