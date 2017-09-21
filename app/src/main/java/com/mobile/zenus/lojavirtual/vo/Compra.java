package com.mobile.zenus.lojavirtual.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Tuca on 19/09/2017.
 */

public class Compra implements Parcelable {


    private Long seqCompra;

    private Date dtCompra;

    private Double total;

    public Compra(){

    }

    protected Compra(Parcel in) {

        dtCompra = new Date(in.readLong());
        total = in.readDouble();

    }




    public static final Creator<Compra> CREATOR = new Creator<Compra>() {
        @Override
        public Compra createFromParcel(Parcel in) {
            return new Compra(in);
        }

        @Override
        public Compra[] newArray(int size) {
            return new Compra[size];
        }
    };

    public Long getSeqCompra() {
        return seqCompra;
    }

    public void setSeqCompra(Long seqCompra) {
        this.seqCompra = seqCompra;
    }

    public Date getDtCompra() {
        return dtCompra;
    }

    public void setDtCompra(Date dtCompra) {
        this.dtCompra = dtCompra;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeSerializable(dtCompra.getTime());
        dest.writeDouble(total);

    }
}
