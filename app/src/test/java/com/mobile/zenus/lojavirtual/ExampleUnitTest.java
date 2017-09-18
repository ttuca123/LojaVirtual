package com.mobile.zenus.lojavirtual;

import com.mobile.zenus.lojavirtual.activity.ActMain;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void verificarFragmentosNulo() throws Exception {
        ActMain mainActivity = new ActMain();

        mainActivity.irParaFragmento(null);
    }
}