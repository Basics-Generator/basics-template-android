package nbs.androidtemplate;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App extends Application
{
    public static final String TAG = App.class.getSimpleName();

	private static App  mInstance;

	private RequestQueue mRequestQueue;


    public static synchronized App getInstance() {
        return mInstance;
    }

    private static synchronized void setInstance(App instance) {
        mInstance = instance;
    }

	@Override
	public void onCreate()
	{
		super.onCreate();

        setInstance(this);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

	}


	//*************************************************************************
	// REQUEST
	//*************************************************************************
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
