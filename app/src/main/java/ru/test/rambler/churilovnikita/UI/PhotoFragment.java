package ru.test.rambler.churilovnikita.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.test.rambler.churilovnikita.App;
import ru.test.rambler.churilovnikita.PrefHelper;
import ru.test.rambler.churilovnikita.adapters.PhotoAdapter;
import ru.test.rambler.churilovnikita.Constants;
import ru.test.rambler.churilovnikita.Managers.HelpManager;
import ru.test.rambler.churilovnikita.Interfaces.Searchable;
import ru.test.rambler.churilovnikita.model.PhotosList;
import ru.test.rambler.churilovnikita.network.InstagramService;
import ru.test.rambler.churilovnikita.network.RecentResponce;
import ru.test.rambler.churilovnikita.network.ServiceFactory;
import ru.test.rambler.churilovnikita.R;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PhotoFragment extends Fragment implements SearchView.OnQueryTextListener, Searchable {


    @BindView(R.id.rv_photos)
    RecyclerView mPhotoRV;
    @BindView(R.id.txt_empty)
    TextView mEmptyTxt;

    private Unbinder unbinder;
    private InstagramService service;
    private Context mContext;
    private PhotoAdapter adapter;
    private Searchable mCallback;
    private PhotosList photosList;

    @Inject
    PrefHelper prefHelper;
    @Inject
    ServiceFactory serviceFactory;

    private final static String PHOTO_LIST_STATE = "ru.test.rambler.churilovnikita.fragment.photo_list_state";
    private static final int REQUEST_TOKEN = 0;

    public PhotoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        service = serviceFactory.createRetrofitService(InstagramService.class);
        mContext = getActivity();
        mCallback = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_LIST_STATE, photosList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mPhotoRV.setLayoutManager(llm);
        adapter = new PhotoAdapter(mContext, new PhotosList(), mCallback);
        setHasOptionsMenu(true);

        if (!prefHelper.isTokenExist() && HelpManager.isOnline(mContext)) {
            showDialog();
        } else if (savedInstanceState != null) {
            photosList = savedInstanceState.getParcelable(PHOTO_LIST_STATE);
            if (photosList != null) {
                setRvVisible();
                adapter.setData(photosList);
                mPhotoRV.setAdapter(adapter);
            }
        } else {
            loadRecentPhoto();
        }
    }

    void showDialog() {
        if (!HelpManager.isOnline(mContext)) {
            HelpManager.showOfflineDialog(mContext);
            return;
        }
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);

        OAuthDialog dialog = new OAuthDialog();
        dialog.setTargetFragment(PhotoFragment.this, REQUEST_TOKEN);
        dialog.show(ft, "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            loadRecentPhoto();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.compareTo("") == 0)
            loadRecentPhoto();//добавил для удобства
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchByTag(query);
        return false;
    }

    //похорошему нужно вынести в отдельный класс()
    public void searchByTag(String query) {
        if (!HelpManager.isOnline(mContext)) {
            HelpManager.showOfflineDialog(mContext);
            return;
        }
        service.getRecentPhotosByTag(query.trim(), prefHelper.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentResponce>() {
                    @Override
                    public void onCompleted() {
                        setRvVisible();
                        mPhotoRV.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(Constants.TAG, "Get Recent Photos by Tag Error:" + e.getMessage());
                        Toast.makeText(mContext, mContext.getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RecentResponce recentResponce) {
                        photosList = new PhotosList(recentResponce.getData());
                        adapter.setData(photosList);
                    }
                });
    }


    private void setRvVisible() {
        mPhotoRV.setVisibility(View.VISIBLE);
        mEmptyTxt.setVisibility(View.GONE);
    }

    private void loadRecentPhoto() {
        if (!HelpManager.isOnline(mContext)) {
            HelpManager.showOfflineDialog(mContext);
            return;
        }
        service.getRecentPhotos(prefHelper.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentResponce>() {
                    @Override
                    public void onCompleted() {
                        setRvVisible();
                        mPhotoRV.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(Constants.TAG, "Get Recent Photos Error:" + e.getMessage());
                        Toast.makeText(mContext, mContext.getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RecentResponce recentResponce) {
                        photosList = new PhotosList(recentResponce.getData());
                        adapter.setData(photosList);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
