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

        WebSettings webSettings= webSubject.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);  // for HTML5 local storage
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webSubject.setWebChromeClient(new WebChromeClient());
        webSubject.setWebViewClient(new TopAlignedClient());

        webSubject.setWebViewClient(new SameView());

        webSubject.loadUrl("https://apply.idb-bisew.info/Home/CourseDeatils/13");

    }
    //generated
    private final class TopAlignedClient extends WebViewClient {

        // Modern override (deprecated String url version avoided)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri u = request.getUrl();
            // If the navigation includes a fragment, reload without it.
            Uri noFrag = u.buildUpon().fragment(null).build();
            if (!u.equals(noFrag)) {
                view.loadUrl(noFrag.toString());
                return true; // we handled it
            }
            return false; // let WebView load normally
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // Force scroll to top
            view.scrollTo(0, 0);

            // Inject JavaScript to kill auto-scroll
            view.evaluateJavascript(
                    "(function() {" +
                            "   window.scrollTo(0,0);" +
                            "   let tries = 0;" +
                            "   let interval = setInterval(function() {" +
                            "       window.scrollTo(0,0);" +
                            "       tries++;" +
                            "       if (tries > 20) clearInterval(interval);" + // keep for ~2s
                            "   }, 100);" +
                            "   document.querySelectorAll('input,textarea,select').forEach(function(el) {" +
                            "       el.blur();" +  // remove focus so page won't jump
                            "   });" +
                            "})();",
                    null
            );
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (webSubject != null && webSubject.canGoBack()) {
//            webSubject.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }

    //generated

    private class SameView extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            view.scrollTo(0,0);
//        }
    }
}