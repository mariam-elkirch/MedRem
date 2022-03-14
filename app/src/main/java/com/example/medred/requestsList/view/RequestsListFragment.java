package com.example.medred.requestsList.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.medred.databinding.FragmentRequestsListBinding;
import com.example.medred.model.Request;
import com.example.medred.requestsList.presenter.RequestsListPresenter;
import com.example.medred.requestsList.presenter.RequestsListPresenterInterface;

import java.util.ArrayList;
import java.util.List;


public class RequestsListFragment extends Fragment implements OnRequestClickListener, RequestsListViewInterface{

    private FragmentRequestsListBinding binding;
    private RequestsListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestsListPresenterInterface requestsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    //TODO: complete requests presenter parameter
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RequestsListAdapter(new ArrayList<>(), this);
        binding.rvRequests.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(RequestsListFragment.this.getContext());
        binding.rvRequests.setLayoutManager(layoutManager);
        binding.rvRequests.setAdapter(adapter);
        requestsPresenter = new RequestsListPresenter(this);
        requestsPresenter.getRequests(this);
    }

    @Override
    public void onAcceptRequest(String senderEmail) {
        requestsPresenter.acceptRequest(senderEmail);
    }

    @Override
    public void onRefuseRequest(String senderEmail) {
        requestsPresenter.refuseRequest(senderEmail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void getRequests(List<Request> requests) {
        if(requests.size() == 0){
            binding.rvRequests.setVisibility(View.GONE);
        }
        else{
            binding.rvRequests.setVisibility(View.VISIBLE);
            adapter.setRequests(requests);
        }
    }
}