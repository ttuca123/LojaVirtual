package com.mobile.zenus.lojavirtual.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.adapter.ResumoProdutoAdapter;
import com.mobile.zenus.lojavirtual.util.MockUtil;
import com.mobile.zenus.lojavirtual.vo.Produto;

import java.util.List;

public class ActResumoCompras extends AppCompatActivity {

    ListView mLstResumoCompras;
    Button mBtnFinalizarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resumo_compras);

        mLstResumoCompras = (ListView) findViewById(R.id.lstResumoCompras);

        List<Produto> produtos = MockUtil.popularProdutos();

        mLstResumoCompras.setAdapter(new ResumoProdutoAdapter(this, produtos));

        mBtnFinalizarCompra = (Button) findViewById(R.id.btnFinalizarCompra);

        mBtnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.Dialogo));


//                View viewSecundaria = LayoutInflater.inflate(R.layout.dialog_confirma_venda, null, false);

                alertDialog.setTitle("Resumo Total: R$ 30,00 em compras");

                alertDialog.setMessage("Confirma transação?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder alertDialogConfirmacao = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.Dialogo));



                        alertDialogConfirmacao.setTitle("Compra Efetuada");

                        alertDialogConfirmacao.setMessage("Sua compra foi realizada com sucesso!!!");

                        alertDialogConfirmacao.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogConfirmacao.show();
                    }
                });

                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });



    }
}
