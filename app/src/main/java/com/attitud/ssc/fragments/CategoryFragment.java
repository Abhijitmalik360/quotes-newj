package com.attitud.ssc.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attitud.ssc.CategoryActivity;
import com.attitud.ssc.MyApplication;
import com.attitud.ssc.adapters.CategoryAdapter;
import com.attitud.ssc.cls.CategoryData;
import com.attitud.ssc.cls.MyReference;
import com.attitud.ssc.databinding.FragmentCategoryBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements CategoryAdapter.onCategoryClickListener {


private FragmentCategoryBinding binding;
private CategoryAdapter adapter;
private ArrayList<CategoryData> arrayList = new ArrayList<>();
    public CategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        return  binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        loadCatData();

    }

    private void init(){
        adapter = new CategoryAdapter(arrayList,this);
        binding.rvView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvView.setAdapter(adapter);
        binding.rvView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }



    private void loadCatData(){
        MyApplication.firestore.collection(MyReference.CATEGORY).get()
                .addOnSuccessListener(querySnapshot -> {
            if (querySnapshot.isEmpty()){
                Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
                return;
            }

            for (QueryDocumentSnapshot documentSnapshot  : querySnapshot){
                CategoryData data = documentSnapshot.toObject(CategoryData.class);
                data.setId(documentSnapshot.getId());
                arrayList.add(data);
                adapter.notifyDataSetChanged();

            }


        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error is "+ e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void categoryClickListener(int position) {
        CategoryData data = arrayList.get(position);
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("id",data.getId());
        intent.putExtra("name",data.getName());
        startActivity(intent);
    }
}