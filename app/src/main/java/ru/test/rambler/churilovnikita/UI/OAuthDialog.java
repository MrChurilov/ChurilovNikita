package ru.test.rambler.churilovnikita.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.test.rambler.churilovnikita.Constants;
import ru.test.rambler.churilovnikita.Managers.HelpManager;
import ru.test.rambler.churilovnikita.R;

/**
 * Created by MrKosherno on 21.07.2016.
 */
public class OAuthDialog extends DialogFragment {
    @BindView(R.id.web_oauth)
    WebView webViewOauth;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private class MyWebViewClient extends WebViewClient {
        /*  @Override
          public void onPageFinished(WebView view, String url) {
              if (url.contains("access_token=")) {
                  saveAccessToken(url);
                  return;
              }
          }*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("access_token=")) {
                saveAccessToken(url);
                sendResult(Activity.RESULT_OK);
                return true;
            }
            return false;
        }
    }

    private void saveAccessToken(String url) {
        String paths[] = url.split("access_token=");
        if (paths.length > 1) {
            HelpManager.setAccessToken(paths[1]);
            dismiss();
            //return;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        webViewOauth
                .loadUrl("https://api.instagram.com/oauth/authorize/?client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.CALLBACK_URL + "&response_type=token");
        webViewOauth.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webViewOauth.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_oauth, container, false);
        unbinder = ButterKnife.bind(this, v);
        webViewOauth = (WebView) v.findViewById(R.id.web_oauth);
        getDialog().setTitle("Use your Instagram account");

        return v;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}
