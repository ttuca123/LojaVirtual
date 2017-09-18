package com.mobile.zenus.lojavirtual.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuca on 17/09/2017.
 */

public class StorageUtil {

    StorageReference mStorageRef;

    public  List<File> buscarImagensProdutos(StorageReference mStorageRef) throws IOException {

        this.mStorageRef = mStorageRef;

       final List<File> imagens = new ArrayList<File>();

        List<String> pathProdutos = MockUtil.popularNomeProdutos();

        for(String pathProduto: pathProdutos){

            final File fileTemp = File.createTempFile(pathProduto, "png");

            imagens.add(fileTemp);

        }

        return imagens;
    }
}
