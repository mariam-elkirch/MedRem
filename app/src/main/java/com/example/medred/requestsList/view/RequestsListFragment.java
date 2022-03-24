package com.example.medred.requestsList.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medred.databinding.FragmentRequestsListBinding;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.model.Repository;
import com.example.medred.model.Request;
import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;
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
        binding.rvRequests.hasFixedSize();
        requestsPresenter = new RequestsListPresenter(this, Repository.
                getInstance(this.getContext(), FirebaseManager.getInstance(this.getContext()),
                        ConcreteLocalSource.getInstance(this.getContext())));
        requestsPresenter.getRequests();
    }

    @Override
    public void onAcceptRequest(Request request) {
        requestsPresenter.acceptRequest(request);
    }

    @Override
    public void onRejectRequest(Request request) {
        requestsPresenter.rejectRequest(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void getRequests(List<Request> requests) {
        Log.i("TAG", "inside fragment");
        if(requests.size() == 0){
            binding.rvRequests.setVisibility(View.GONE);
        }
        else{
            binding.rvRequests.setVisibility(View.VISIBLE);
            adapter.setRequests(requests);
        }
    }

    @Override
    public void onSuccessAcceptingRequest(Boolean isAccepted) {
        if(isAccepted){
            Toast.makeText(this.getContext(), "Request is accepted successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this.getContext(), "Couldn't accept the request, some error happened", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessRejectingRequest(Boolean isRejected) {
        if(isRejected){
            Toast.makeText(this.getContext(), "Request is rejected successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this.getContext(), "Couldn't reject the request, some error happened", Toast.LENGTH_SHORT).show();
        }
    }
}