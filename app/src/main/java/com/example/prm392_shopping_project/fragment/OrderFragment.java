package com.example.prm392_shopping_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.database.OrderDB;
import com.example.prm392_shopping_project.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    OrderDB db ;
    TextView tv_count, tv_amount, tv_order;
    List<Order> list;

    public OrderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_total_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new OrderDB(getContext());
        tv_amount = view.findViewById(R.id.tv_total_amount);
        tv_order = view.findViewById(R.id.tv_order);
        tv_count = view.findViewById(R.id.tv_count);

        list = new ArrayList<>();
        list = db.getAll();

        tv_count.setText("Total: " + list.size());
        long total_amount = 0;
        String order = "";
        for (int i = 0; i<list.size(); i++) {
                total_amount += list.get(i).getTotalBill();
                order += list.get(i);
        }
        tv_amount.setText("Total Amount: " + total_amount);
        tv_order.setText(order);
    }
}
