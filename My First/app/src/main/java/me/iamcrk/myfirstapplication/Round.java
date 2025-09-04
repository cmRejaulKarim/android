package me.iamcrk.myfirstapplication;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Round extends AppCompatActivity {

    private WebView webSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_round);

        webSubject = findViewById(R.id.webSubject);

        WebSettings webSettings = webSubject.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);  // for HTML5 local storage
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webSubject.setWebChromeClient(new WebChromeClient());
        webSubject.setWebViewClient(new SoftLockClient());

        webSubject.loadUrl("https://apply.idb-bisew.info/Home/CourseDeatils/13");
    }

    private static String stripFragment(String url) {
        try {
            Uri u = Uri.parse(url);
            return u.buildUpon().fragment(null).build().toString();
        } catch (Exception e) {
            return url;
        }
    }

    private final class SoftLockClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String cleanUrl = stripFragment(request.getUrl().toString());
            view.loadUrl(cleanUrl);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // Scroll to top immediately
            view.scrollTo(0, 0);

            // Inject JS: prevent site auto-scroll, but allow user scroll
            view.evaluateJavascript(
                    "(function() {" +
                            "   window.scrollTo(0,0);" +
                            "   let tries = 0;" +
                            "   let interval = setInterval(function() {" +
                            "       window.scrollTo(0,0);" +
                            "       tries++;" +
                            "       if (tries > 3) clearInterval(interval);" + // stop after ~2s//200ms
                            "   }, 30);" +
                            "   document.querySelectorAll('input,textarea,select').forEach(function(el) {" +
                            "       el.blur();" + // prevent focus from stealing scroll
                            "   });" +
                            "})();",
                    null
            );
        }
    }

}