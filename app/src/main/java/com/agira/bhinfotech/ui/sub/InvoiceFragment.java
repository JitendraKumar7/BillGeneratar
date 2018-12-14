package com.agira.bhinfotech.ui.sub;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.lib.viewRenderer.AbstractViewRenderer;
import com.agira.bhinfotech.modal.Product;
import com.agira.bhinfotech.ui.FragmentActivity;
import com.agira.bhinfotech.ui.base.BaseFragment;
import com.agira.bhinfotech.utility.EnglishNumberToWords;
import com.agira.bhinfotech.utility.GestureOverlayViewDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvoiceFragment extends BaseFragment {

    AbstractViewRenderer viewRenderer;

    @BindView(R.id.ivSignature)
    ImageView ivSignature;

    @BindView(R.id.rowLayout)
    LinearLayout rowLayout;

    private int count = 0;
    private double price = 0;

    @BindViews({
            R.id.txtAmount, //0
            R.id.txtQuantity, //1
            R.id.txtInvoiceNo, //2
            R.id.txtWordAmount, //3
            R.id.txtInvoiceDate})
    List<TextView> txtViews;

    @OnClick({R.id.btnShare,
            R.id.ivSignature})
    public void onClick(View v) {

        if (v.getId() == R.id.btnShare) {
            v.setVisibility(View.GONE);

            viewRenderer = new AbstractViewRenderer(_activity, _rootView);
            Fragment frag = ViewerFragment.getInstance(viewRenderer);
            ((FragmentActivity) _activity).addFragment(frag);
        } else {
            GestureOverlayViewDialog dialog = new GestureOverlayViewDialog(_activity) {

                @Override
                protected void getSignature(Bitmap bitmap) {
                    ivSignature.setImageBitmap(bitmap);
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    @SuppressWarnings("All")
    private List<Product> getList() {
        Bundle bundle = this.getArguments();
        return (ArrayList<Product>) bundle.getSerializable(SHARE_DATA);
    }

    private void setTxtView(int index, Object value) {
        String strValue = String.valueOf(value);
        txtViews.get(index).setText(strValue);
    }

    @Override
    protected void onCreate(@NonNull LayoutInflater inflater) {

        List<Product> mDataList = new ArrayList<>(getList());

        for (Product bean : mDataList) {
            RowInvoice invoice = new RowInvoice(inflater);
            rowLayout.addView(invoice.onBindView(bean));
        }

        setTxtView(1, count);
        setTxtView(2, getInvoiceNo());
        setTxtView(4, getInvoiceDate());
        setTxtView(0, String.format("\u20B9 %s", price));
        setTxtView(3, EnglishNumberToWords.doubleConvert(price));
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_invoice;
    }

    class RowInvoice {

        private View _rowView;

        @BindViews({
                R.id.row_txtSrNo, //0
                R.id.row_txtRate, //1
                R.id.row_txtAmount, //2
                R.id.row_txtHSN_SAC, //3
                R.id.row_txtQuantity, //4
                R.id.row_txtDiscount, //5
                R.id.row_txtPercentage, //6
                R.id.row_txtDescription})
        List<TextView> txtViews;

        private String getCount() {
            int count = rowLayout.getChildCount();
            return String.valueOf(count + 1);
        }

        View onBindView(Product bean) {

            count += bean.getCount();
            price += bean.getNewPrice();

            setTxtView(0, getCount());
            setTxtView(7, bean.getName());
            setTxtView(4, bean.getAllCount());
            setTxtView(2, bean.getAllPrice());

            return _rowView;
        }

        RowInvoice(LayoutInflater inflater) {
            this._rowView = inflater.inflate(R.layout.ui_row_invoice_item, null);
            ButterKnife.bind(this, _rowView);
        }

        private void setTxtView(int index, String value) {

            txtViews.get(index).setText(value);
        }

    }

    public String getInvoiceNo() {

        String date = new SimpleDateFormat("MM-yy", Locale.US)
                .format(Calendar.getInstance().getTime());

        return String.format(Locale.US, "BHI/%s/%04d",
                date, new Random().nextInt(10000));
    }

    public String getInvoiceDate() {
        //SimpleDateFormat called without pattern
        return new SimpleDateFormat("dd-MMM-yyyy")
                .format(Calendar.getInstance().getTime());
    }

}
