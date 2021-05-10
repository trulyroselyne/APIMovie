package com.example.apimovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apimovie.MovieActivityDetail;
import com.example.apimovie.R;
import com.example.apimovie.adapters.movie.NowPlayingAdapter;
import com.example.apimovie.helper.OnItemClickListener;
import com.example.apimovie.models.movie.NowPlayingResult;
import com.example.apimovie.networks.Consts;
import com.example.apimovie.networks.GetRetrofit;
import com.example.apimovie.networks.MovieService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMovie extends Fragment implements OnItemClickListener<Integer> {

    private RecyclerView rvMovie;
    private NowPlayingAdapter nowPlayingAdapter;

    public FragmentMovie(){

    }

    public static FragmentMovie newInstance(){

        FragmentMovie fragment = new FragmentMovie();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_fragment, container, false);

        rvMovie = view.findViewById(R.id.rv_Movie);
        rvMovie.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMovie.setHasFixedSize(true);

        nowPlayingAdapter = new NowPlayingAdapter();
        nowPlayingAdapter.setClickListener(this);
        load();
        return view;
    }

    private void load() {

        MovieService service = GetRetrofit.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("api_key", Consts.APIKEY);
        params.put("language", Consts.LANGUAGE);
        params.put("page", Consts.PAGE);
        Call<NowPlayingResult> call = service.nowPlayingMovies(params);

        call.enqueue(new Callback<NowPlayingResult>() {
            @Override
            public void onResponse(Call<NowPlayingResult> call, Response<NowPlayingResult> response) {
                if (response.isSuccessful() && response.body().getNowPlayingList() != null){
                    nowPlayingAdapter.setNowPlayingList(response.body().getNowPlayingList());
                    rvMovie.setAdapter(nowPlayingAdapter);
                }else {
                    Log.d(Consts.APIERROR, "error");
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResult> call, Throwable t) {

                Log.d(Consts.APIERROR, "error");
            }
        });
    }

    @Override
    public void onClick(Integer id) {
        Intent intent = new Intent(getActivity(), MovieActivityDetail.class);
        if (id != null){
            intent.putExtra("TV ID", id);
            startActivity(intent);
        }
    }
}
