package com.agira.bhinfotech.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.modal.Product;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductAdapter extends RecyclerView.Adapter
        <ProductAdapter.CustomViewHolder> implements Filterable {

    private List<Product> mDataList;
    private List<Product> mDataListFilter;

    public List<Product> getDataList() {

        return mDataList;
    }

    public ProductAdapter(List<Product> mDataList) {
        this.mDataListFilter = mDataList;
        this.mDataList = mDataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView tvName;

        @BindView(R.id.txtDesc)
        TextView tvDesc;

        @BindView(R.id.txtPrice)
        TextView tvPrice;

        @BindView(R.id.txtCount)
        TextView tvCount;

        @OnClick({R.id.btnAdd,
                R.id.btnRemove})
        void onClick(View v) {

            Product bean = mDataListFilter.get(getPosition());

            switch (v.getId()) {

                case R.id.btnAdd: {
                    int count = bean.getCount();
                    bean.setCount(count + 1);
                    break;
                }

                case R.id.btnRemove: {
                    int count = bean.getCount();
                    bean.setCount(count - 1);
                    break;
                }

            }

            notifyItemChanged(getPosition());
        }

        void onBindData(int position) {
            Product bean = mDataListFilter.get(position);

            tvName.setText(bean.getName());
            tvDesc.setText(bean.getBrand());
            tvPrice.setText(Html.fromHtml(bean.getPrice()));
            tvCount.setText(String.valueOf(bean.getCount()));
        }

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.onBindData(position);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_row_product, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataListFilter = mDataList;
                } else {
                    List<Product> filteredList = new LinkedList<>();
                    for (Product row : mDataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDataListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataListFilter = (LinkedList<Product>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {

        return mDataListFilter.size();
    }

}