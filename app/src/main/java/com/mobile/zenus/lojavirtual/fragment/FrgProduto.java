package com.mobile.zenus.lojavirtual.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.android.gms.plus.PlusOneButton;
import com.mobile.zenus.lojavirtual.R;
import com.mobile.zenus.lojavirtual.activity.ActMain;
import com.mobile.zenus.lojavirtual.activity.ActResumoCompras;
import com.mobile.zenus.lojavirtual.adapter.ProdutoAdapter;
import com.mobile.zenus.lojavirtual.util.MockUtil;
import com.mobile.zenus.lojavirtual.util.StorageUtil;
import com.mobile.zenus.lojavirtual.vo.Produto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link FrgProduto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgProduto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgProduto extends Fragment {

    View view;
    StorageReference mStorageRef;
    Button btnAdd;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String STORAGE_PATH = "gs://cloudstorage-77f43.appspot.com";


    private OnFragmentInteractionListener mListener;

    public FrgProduto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrgProduto.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgProduto newInstance(String param1, String param2) {
        FrgProduto fragment = new FrgProduto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mStorageRef = FirebaseStorage.getInstance()
                .getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg_produto, container, false);

        initializeViewProdutos();

        return view;
    }


    private void initializeViewProdutos(){

        btnAdd = (Button) view.findViewById(R.id.btnAdcionarCarrinho);


        List<File> imagens = new ArrayList<File>();
        StorageUtil storageUtil = new StorageUtil();

        try {

            imagens = storageUtil.buscarImagensProdutos(mStorageRef);

        } catch (IOException e) {

            Toast.makeText(view.getContext(), "Erro ao carregar arquivo", Toast.LENGTH_SHORT).show();

        }

        GridView gridView = (GridView) view.findViewById(R.id.gdvProdutos);

        final List<Produto> produtos =   MockUtil.popularProdutos();

        final ProdutoAdapter produtoAdapter = new ProdutoAdapter(view.getContext(), produtos);

        gridView.setAdapter(produtoAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont =0;
                for(Produto produto: produtoAdapter.getProdutos()){
                    if(produto.isSelecionado()){
                        cont++;
                        break;
                    }
                }
                if(cont>0){
                    Intent it = new Intent(view.getContext(), ActResumoCompras.class);
//                    Produto[] produtos = produtoAdapter.getProdutos();
//
//                    it.putExtra("produtos", produtoAdapter.getProdutos());
                    startActivity(it);

//                    Toast.makeText(v.getContext(), "Existe pelo menos um produto selecionado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Não Existe nenhum produto selecionado", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
