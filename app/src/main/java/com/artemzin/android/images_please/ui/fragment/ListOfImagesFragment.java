package com.artemzin.android.images_please.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artemzin.android.images_please.Loggi;
import com.artemzin.android.images_please.R;
import com.artemzin.android.images_please.model.ImagesFinderModel;
import com.artemzin.android.images_please.model.SafeRunnable;
import com.artemzin.android.images_please.model.TaskListener;
import com.artemzin.android.images_please.ui.adapter.ImagesAdapter;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public class ListOfImagesFragment extends Fragment {

    public static ListOfImagesFragment newInstance() {
        return new ListOfImagesFragment();
    }

    private static final String TAG = ListOfImagesFragment.class.getSimpleName();

    private ImagesFinderModel mImagesFinderModel = new ImagesFinderModel();
    private ImagesAdapter mImagesAdapter;

    @InjectView(R.id.list_of_images_loading_ui)
    View mLoadingUiView;

    @InjectView(R.id.list_of_images_error_ui)
    View mErrorUiView;

    @InjectView(R.id.list_of_images_empty_ui)
    View mEmptyUiView;

    @InjectView(R.id.list_of_images_content_ui)
    ListView mContentUiView;

    // of course, usually this functionality located in "BaseFragment"

    //region ui states, sorry for boilerplate code..

    private void setUiStateLoading() {
        mLoadingUiView.setVisibility(View.VISIBLE);
        mErrorUiView.setVisibility(View.GONE);
        mEmptyUiView.setVisibility(View.GONE);
        mContentUiView.setVisibility(View.GONE);
    }

    private void setUiStateError() {
        mLoadingUiView.setVisibility(View.GONE);
        mErrorUiView.setVisibility(View.VISIBLE);
        mEmptyUiView.setVisibility(View.GONE);
        mContentUiView.setVisibility(View.GONE);
    }

    private void setUiStateEmpty() {
        mLoadingUiView.setVisibility(View.GONE);
        mErrorUiView.setVisibility(View.GONE);
        mEmptyUiView.setVisibility(View.VISIBLE);
        mContentUiView.setVisibility(View.GONE);
    }

    private void setUiStateContent() {
        mLoadingUiView.setVisibility(View.GONE);
        mErrorUiView.setVisibility(View.GONE);
        mEmptyUiView.setVisibility(View.GONE);
        mContentUiView.setVisibility(View.VISIBLE);
    }

    //endregion


    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        mImagesAdapter = new ImagesAdapter(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_images, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        mContentUiView.setAdapter(mImagesAdapter);
    }

    @Override public void onStart() {
        super.onStart();
        reloadImagesFromSdCard();
    }

    @OnClick({R.id.list_of_images_error_ui_try_again_button, R.id.list_of_images_empty_ui_try_again_button})
    public void tryLoadImagesAgain() {
        reloadImagesFromSdCard();
    }

    private void reloadImagesFromSdCard() {
        mImagesFinderModel.asyncFindPossibleImageFiles(Environment.getExternalStorageDirectory(), new FindPossibleImageFilesListener());
    }

    class FindPossibleImageFilesListener extends TaskListener<List<File>> {

        boolean isStateOk() {
            return isVisible() && getView() != null;
        }

        @Override public void onPreExecute() {
            new SafeRunnable() {
                @Override public void safeRun() {
                    if (isStateOk()) {
                        setUiStateLoading();
                    }
                }
            }.run();
        }

        @Override public void onProblemOccurred(Throwable t) {
            Loggi.e(TAG, FindPossibleImageFilesListener.class.getSimpleName() + " onProblemOccurred", t);

            new SafeRunnable() {
                @Override public void safeRun() {
                    if (isStateOk()) {
                        setUiStateError();
                    }
                }
            }.run();
        }

        @Override public void onDataProcessed(final List<File> imageFilesList) {
            new SafeRunnable() {
                @Override protected void safeRun() {
                    if (isStateOk()) {
                        setUiStateContent();
                        mImagesAdapter.setData(getActivity(), imageFilesList);
                    }
                }
            }.run();
        }
    }
}
