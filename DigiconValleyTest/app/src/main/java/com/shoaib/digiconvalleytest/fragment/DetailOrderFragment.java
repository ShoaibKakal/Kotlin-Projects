package com.shoaib.digiconvalleytest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shoaib.digiconvalleytest.MainActivity;
import com.shoaib.digiconvalleytest.Model.Item;
import com.shoaib.digiconvalleytest.Model.Order;
import com.shoaib.digiconvalleytest.R;
import com.shoaib.digiconvalleytest.adapter.ApiOrderAdapter;
import com.shoaib.digiconvalleytest.network.ApiClient;
import com.shoaib.digiconvalleytest.network.ApiService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Item> itemArrayList;
    private RecyclerView recyclerviewApiOrders;
    private ApiOrderAdapter apiOrderAdapter;

    public DetailOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailOrderFragment newInstance(String param1, String param2) {
        DetailOrderFragment fragment = new DetailOrderFragment();
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
        return inflater.inflate(R.layout.fragment_detail_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerviewApiOrders = view.findViewById(R.id.recyclerview_api_orders);
        itemArrayList = new ArrayList<>();
        getAllOrders(49);

    }



    private void getAllOrders(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tailordesk.azurewebsites.net/TailorGetApi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Item>> call = apiService.getAllOrders(id);

        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {

                if (response.isSuccessful())
                {
                    itemArrayList = response.body();
                    for (int i=0; i< itemArrayList.size(); i++)
                    {
                        apiOrderAdapter = new ApiOrderAdapter(itemArrayList, getContext());
                        recyclerviewApiOrders.setAdapter(apiOrderAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }








}