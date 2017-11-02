package com.akatsuki.shout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.audiofx.BassBoost.Settings;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	WebView web1;
	ImageView animationpanel;
	AnimationDrawable animationControler;
	
	private ValueCallback<Uri> mUploadMessage;  
	private final static int FILECHOOSER_RESULTCODE=1;  

	 @Override  
	 protected void onActivityResult(int requestCode, int resultCode,  
	                                    Intent intent) {  
	  if(requestCode==FILECHOOSER_RESULTCODE)  
	  {  
	   if (null == mUploadMessage) return;  
	            Uri result = intent == null || resultCode != RESULT_OK ? null  
	                    : intent.getData();  
	            mUploadMessage.onReceiveValue(result);  
	            mUploadMessage = null;  
	  }
	  }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
	
    	if (!DetectConnection.checkInternetConnection(this)) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
			dialog.setMessage("Please Cheack Your Internet Connection and try again."
	        		   );
	           dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
	        	   @Override
	        	   public void onClick(DialogInterface dialog, int which) {
	        		   startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
	        		   ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
	        		   new intent.setComponent(cName); 
	        		   dialog.cancel();
	        		   
	        		   
	        		   finish();
					
	        	   }
	           });
	           dialog.show();	 
	           findViewById(R.id.splash).setVisibility(View.GONE);
               //show webview
               findViewById(R.id.webView1).setVisibility(View.GONE);
			} 
      	else { 
      			
      			
      			animationpanel=(ImageView) findViewById(R.id.imageView1);
      	        animationpanel.setImageResource(R.drawable.frameanimation);
      	        animationControler=(AnimationDrawable)animationpanel.getDrawable();
      	        animationControler.start();
		        web1=(WebView) findViewById(R.id.webView1);
		        web1.getSettings().setJavaScriptEnabled(true);
		        web1.getSettings().setDomStorageEnabled(true);
		        web1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		        web1.getSettings().setLoadsImagesAutomatically(true);
		        web1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		        web1.getSettings().setSavePassword(true);
		        web1.getSettings().setSaveFormData(true);
		        web1.getSettings().setAppCacheMaxSize(5*1024*1024);
		        web1.getSettings().setAppCachePath("");
		        web1.getSettings().setDatabaseEnabled(true);
		        web1.setLayerType(View.LAYER_TYPE_HARDWARE, null);

	
		
		web1.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                findViewById(R.id.splash).setVisibility(View.GONE);
                //show webview
                findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                animationControler.stop();
                
            }


        });     

		web1.loadUrl("http://shout.batikrom.com/sandbox/index.php");
	
		web1.setWebChromeClient(new WebChromeClient()  
	    {  
		
			
	           //The undocumented magic method override  
	           //Eclipse will swear at you if you try to put @Override here  
	        // For Android 3.0+
	        public void openFileChooser(ValueCallback<Uri> uploadMsg) {  

	            mUploadMessage = uploadMsg;  
	            Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
	            i.addCategory(Intent.CATEGORY_OPENABLE);  
	            i.setType("image/*");  
	            MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);  

	           }

	        // For Android 3.0+
	           public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
	           mUploadMessage = uploadMsg;
	           Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	           i.addCategory(Intent.CATEGORY_OPENABLE);
	           i.setType("*/*");
	           MainActivity.this.startActivityForResult(
	           Intent.createChooser(i, "File Browser"),
	           FILECHOOSER_RESULTCODE);
	           }

	        //For Android 4.1
	           public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
	               mUploadMessage = uploadMsg;  
	               Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
	               i.addCategory(Intent.CATEGORY_OPENABLE);  
	               i.setType("image/*");  
	               MainActivity.this.startActivityForResult( Intent.createChooser( i, "File Chooser" ), MainActivity.FILECHOOSER_RESULTCODE );

	           }

	    });  

      	}

		
	}
	
	
	
	//when back button press perform this
			@Override
			public void onBackPressed() {
			 
			    if (web1.canGoBack()) {
			        web1.goBack();
			    } else {
			    	super.onBackPressed();
		      
			    }
	 }
			
}
