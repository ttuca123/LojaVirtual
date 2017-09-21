package com.mobile.zenus.lojavirtual.view;

import com.mobile.zenus.lojavirtual.vo.Produto;

import java.util.List;

/**
 * Created by Tuca on 19/09/2017.
 */

public interface ActResumoView {

    public void preencherTotal(String total);

    public void habilitarRecalculo();

    public String calcularTotal();

    public void habilitarBotaoFinalizar();

    public void habilitarBotaoCalcularTotal();

    public void desabilitarBotaoFinalizar();

    public void desabilitarBotaoCalcularTotal();


}
