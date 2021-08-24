package com.example.appchatrealtime.bottomnavigation.listmessage.view.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.bottomnavigation.listmessage.viewmodel.ChatViewModel;
import com.example.appchatrealtime.databinding.ItemMessageImageLeftBinding;
import com.example.appchatrealtime.databinding.ItemMessageImageRightBinding;
import com.example.appchatrealtime.databinding.ItemMessageLeftBinding;
import com.example.appchatrealtime.databinding.ItemMessageRightBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatViewModel> arrayList;
    private Context context;
    private final int left=0;
    private final int right=1;
    private final int rightImage=2;
    private final int leftImage=3;



    public ChatAdapter(ArrayList<ChatViewModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(viewType==left) {
            ItemMessageLeftBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_left, parent, false);

            return new ItemMessaleftViewHoder(binding);
        }else if(viewType ==right) {
         ItemMessageRightBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_right, parent, false);

            return new ItemMessaRightViewHoder(binding);
        }else if(viewType == rightImage){
            ItemMessageImageRightBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_image_right, parent, false);

            return new ItemImageRightViewHoder(binding);
        }else {
            ItemMessageImageLeftBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_image_left, parent, false);

            return new ItemImageLefyViewHoder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ChatViewModel listMessage=arrayList.get(position);
        if(holder instanceof ItemMessaleftViewHoder){
            ((ItemMessaleftViewHoder) holder).bindleft(listMessage);
        } else  if (holder instanceof ItemMessaRightViewHoder){
            ((ItemMessaRightViewHoder) holder).bindright(listMessage);
        }else if(holder instanceof ItemImageLefyViewHoder){
            ((ItemImageLefyViewHoder) holder).bindleft(listMessage);
        }else   ((ItemImageRightViewHoder) holder).bindright(listMessage);


    }




    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }


    public class ItemMessaleftViewHoder extends RecyclerView.ViewHolder {
        public ItemMessageLeftBinding leftBinding;


        public ItemMessaleftViewHoder(@NonNull @NotNull ItemMessageLeftBinding leftBinding) {
            super(leftBinding.getRoot());
            this.leftBinding=leftBinding;
        }

        public void bindleft(ChatViewModel chatViewModel) {
            leftBinding.setViewmodel(chatViewModel);
            leftBinding.executePendingBindings();

        }

        public ItemMessageLeftBinding getAdapterBindingleft(){
            return leftBinding;
        }

    }
    public class ItemMessaRightViewHoder extends RecyclerView.ViewHolder {

        public ItemMessageRightBinding rightBinding;
        public ItemMessaRightViewHoder(@NonNull @NotNull ItemMessageRightBinding rightBinding) {
            super(rightBinding.getRoot());
            this.rightBinding=rightBinding;
        }

        public void bindright(ChatViewModel chatViewModel) {
            rightBinding.setViewmodel(chatViewModel);
            rightBinding.executePendingBindings();

        }

        public ItemMessageRightBinding getAdapterBindingright(){
            return rightBinding;
        }

    }
    public class ItemImageRightViewHoder extends RecyclerView.ViewHolder {

        public ItemMessageImageRightBinding rightBinding;
        public ItemImageRightViewHoder(@NonNull @NotNull ItemMessageImageRightBinding rightBinding) {
            super(rightBinding.getRoot());
            this.rightBinding=rightBinding;
        }

        public void bindright(ChatViewModel chatViewModel) {
            rightBinding.setViewmodel(chatViewModel);
            rightBinding.executePendingBindings();

        }

        public ItemMessageImageRightBinding getAdapterBindingright(){
            return rightBinding;
        }

    }
    public class ItemImageLefyViewHoder extends RecyclerView.ViewHolder {

        public ItemMessageImageLeftBinding leftBinding;
        public ItemImageLefyViewHoder(@NonNull @NotNull ItemMessageImageLeftBinding leftBinding) {
            super(leftBinding.getRoot());
            this.leftBinding=leftBinding;
        }

        public void bindleft(ChatViewModel chatViewModel) {
            leftBinding.setViewmodel(chatViewModel);
            leftBinding.executePendingBindings();

        }

        public ItemMessageImageLeftBinding getAdapterBindingright(){
            return leftBinding;
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(arrayList.get(position).getStatus()){
            if(arrayList.get(position).getImage()){
                return rightImage;
            }else return right;
        }
        else{
            if(arrayList.get(position).getImage()){
                return leftImage;
            }else  return left;
        }
    }
}
