package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.Choose_friendListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.ChooseFriendAdapter;
import com.example.appchatrealtime.adapter.CreateConversationAdapter;
import com.example.appchatrealtime.databinding.CreateconversationFragmentBinding;
import com.example.appchatrealtime.model.CreateMessage;
import com.example.appchatrealtime.model.Friend;
import com.example.appchatrealtime.model.Invite_User;
import com.example.appchatrealtime.model.ItemCreateConversation;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.viewmodels.CreateConversationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateConversationFragment extends Fragment  {
    CreateConversationViewModel createConversationViewModel;
    CreateConversationAdapter createConversationAdapter;
    ChooseFriendAdapter createConversationAdapterChooseFriend;
    ArrayList<Friend> arrayList=new ArrayList<>();
    private CreateMessage createMessage=new CreateMessage();
    private int dem=0;
    private int count;
    private String id_Sender;
    public static CreateConversationFragment newInstance() {

        Bundle args = new Bundle();

        CreateConversationFragment fragment = new CreateConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        CreateconversationFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.createconversation_fragment, container, false);
        View view = binding.getRoot();

        binding.setLifecycleOwner(getActivity());
        createConversationViewModel=new ViewModelProvider(getActivity()).get(CreateConversationViewModel.class);
        binding.setCreateconversation(createConversationViewModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        createConversationViewModel.getArrayListMutableLiveData(getActivity()).observe(getActivity(), new Observer<ArrayList<ItemCreateConversation>>()  {
            @Override
            public void onChanged(ArrayList<ItemCreateConversation> itemCreateConversations) {
                createConversationAdapter =new CreateConversationAdapter(itemCreateConversations,getActivity());
                createConversationAdapterChooseFriend =new ChooseFriendAdapter(arrayList,getActivity());
                binding.recyclerview2.setAdapter(createConversationAdapterChooseFriend);
                binding.recyclerview2.setLayoutManager(mLayoutManager1);
                createConversationAdapter.setChoose_friendListerner(new Choose_friendListerner() {
                    @Override
                    public void addFriend(Friend friend) {
                        arrayList.add(friend);
                        dem++;
                        binding.relaBottom.setVisibility(View.VISIBLE);
                        createConversationAdapterChooseFriend.notifyDataSetChanged();
                    }

                    @Override
                    public void removeFriend(Friend friend) {
                        for(int i=0;i<arrayList.size();i++){
                            if(arrayList.get(i).getId().equals(friend.getId())){
                                arrayList.remove(i);
                                dem--;
                                if(dem==0) binding.relaBottom.setVisibility(View.GONE);
                                break;
                            }
                        }
                        createConversationAdapterChooseFriend.notifyDataSetChanged();
                    }

                });

                binding.recyclerview.setAdapter(createConversationAdapter);
                binding.recyclerview.setLayoutManager(mLayoutManager);
                binding.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        createConversationAdapter.getFilter().filter(s);
                        return false;
                    }
                });

           }
        });
        firebase fb=new firebase();
        DatabaseReference databaseReference =fb.getDatabaseReference().child("ListMessage");
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(getActivity());
        String id_Host=sharedPreferencesModel.getString("idHost","");
        CreateArrListMessage();
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase fb=new firebase();
                DatabaseReference databaseReference =fb.getDatabaseReference().child("ListMessage");
                id_Sender=conversion(arrayList);
                createMessage=new CreateMessage(id_Host,id_Sender);
                if(CheckCreate(id_Host,id_Sender)) {
                    databaseReference.child(String.valueOf(count)).setValue(createMessage);
                }

                sharedPreferencesModel.saveString("id_guest",AddId(id_Host+","+id_Sender));
                Fragment fragment=TopicFragment.newInstance();
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame,ChatFragment.newInstance(),"Chat_frag");
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        binding.txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                createConversationAdapter.notifyDataSetChanged();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=CreateConversationFragment.newInstance();
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame,TopicFragment.newInstance(),"topic_frag");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
    private String conversion( ArrayList<Friend> s){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.size();i++){
            sb.append(s.get(i).getId()+",");
        }
        return sb.toString();
    }
    private ArrayList<Invite_User> arr=new ArrayList<>();
    private Boolean CheckCreate(String id_Host,String id_Guest){

        for(int i=0;i<arr.size();i++){
           if(AddId(arr.get(i).getInvite_send()+arr.get(i).getInvite_receive()).equals(AddId(id_Guest+id_Host))){
               return false;
            }
        }

        return true;
    }
    private void CreateArrListMessage(){
        firebase fb =new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arr.clear();
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++
                ) {
                    arr.add(new Invite_User(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()),String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue())));
                }

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        };
        databaseReference.addValueEventListener(postMessage);
    }
    String AddId(String s){
        List<String> list= Arrays.asList(s.split(","));
        Collections.sort(list);
        String chuoi="";
        for (int i=0;i<list.size();i++){
            chuoi+=list.get(i)+",";
        }
        return chuoi;

    }



}
