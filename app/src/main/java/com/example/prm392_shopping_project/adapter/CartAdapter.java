package com.example.prm392_shopping_project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.CartActivity;
import com.example.prm392_shopping_project.Interface.IImageOnClick;
import com.example.prm392_shopping_project.MainActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Event.EventCalculateTotalPrice;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<Cart> cartList;
    Context context;


    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(cartList.get(position).getName());
        holder.price.setText(cartList.get(position).getPrice() + " $");
        holder.quantity.setText(cartList.get(position).getQuantity() + "");
        holder.total.setText(cartList.get(position).getQuantity() * cartList.get(position).getPrice() + " $");
        byte[] Image = cartList.get(position).getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.img.setImageBitmap(bitmap);
        holder.setListener(new IImageOnClick() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                if (value == 1) {
                    if (cartList.get(pos).getQuantity() > 1) {
                        cartList.get(pos).setQuantity(cartList.get(pos).getQuantity() - 1);
                        holder.quantity.setText(cartList.get(pos).getQuantity() + "");
                        holder.total.setText(cartList.get(pos).getQuantity() * cartList.get(pos).getPrice() + " $");
                        EventBus.getDefault().postSticky(new EventCalculateTotalPrice());

                    } else if (cartList.get(pos).getQuantity() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.cartList.remove(pos);
                                EventBus.getDefault().postSticky(new EventCalculateTotalPrice());
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                } else if (value == 2) {
                    if (cartList.get(pos).getQuantity() < 10) {
                        cartList.get(pos).setQuantity(cartList.get(pos).getQuantity() + 1);
                        holder.quantity.setText(cartList.get(pos).getQuantity() + "");
                        holder.total.setText(cartList.get(pos).getQuantity() * cartList.get(pos).getPrice() + " $");
                        EventBus.getDefault().postSticky(new EventCalculateTotalPrice());
                    }

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView price, quantity, name, total;
        ImageView minus, plus, img;
        IImageOnClick listener;

        public void setListener(IImageOnClick listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_giohang_tensp);
            quantity = itemView.findViewById(R.id.item_giohang_soluong);
            total = itemView.findViewById(R.id.item_giohang_giasp2);
            price = itemView.findViewById(R.id.item_giohang_gia);
            img=itemView.findViewById(R.id.item_giohang_image);
            minus = itemView.findViewById(R.id.item_giohang_tru);
            plus = itemView.findViewById(R.id.item_giohang_cong);
            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == minus) {
                listener.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == plus) {
                listener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
