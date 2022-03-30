package com.example.dogapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.adapter.DogsAdapter;
import com.example.dogapp.databinding.FragmentListBinding;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentListBinding binding;
    private DogsAdapter mAdapter;
    private DogsViewModel mViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        return inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initValues();
        } catch(Exception e){
            Log.e("ListFragment",e.getMessage());
        }
    }

    private void initValues(){
        mViewModel = new ViewModelProvider(this).get(DogsViewModel.class);
        RecyclerView.LayoutManager linearLayout = new GridLayoutManager(getContext(), 2);
        binding.rvDogApp.setLayoutManager(linearLayout);
        mViewModel.init();
        mAdapter = new DogsAdapter(getContext(), new DogsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DogBreed item) {
//                pass item to Detail Fragment
                DetailFragment detailFragment = DetailFragment.newInstance(item);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.add(R.id.fragmentContainerView, detailFragment);
                ft.addToBackStack("detail");
                ft.commit();
            }
        });
        binding.rvDogApp.setAdapter(mAdapter);
        mViewModel.getDogBreeds().observe(getViewLifecycleOwner(), new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                if(dogBreeds!=null){
                    Log.i("ListFragment",dogBreeds.toString());
                    mAdapter.setDogBreedList(dogBreeds);
                }
            }
        });
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }
}