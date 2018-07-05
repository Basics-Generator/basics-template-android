package nbs.androidtemplate.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import nbs.androidtemplate.App;


/**
 * Utility class containing method for http call and json parsing
 */
public class Requester
{
    public static final String TAG = Requester.class.getSimpleName();

    private static final String REQUEST_TAG = "req_";
    private static final int REQUEST_TIMEOUT_MS = 10 * 1000;
    private static final int REQUEST_CH_TIMEOUT_MS = 10 * 1000;
    private static final String API_KEY = "Api-Key";
    private static final String API_KEY1 = "api_key";
    static final String UTF_8 = "UTF-8";


    private Requester() {
    }

    private static void addRequestPolicy(Request<?> request) {
        addCustomRequestPolicy(request, REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	}

	private static void addCustomRequestPolicy(Request<?> request, int defaultTimout, int defaultMaxRetries, float defaultBackoffMult) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                defaultTimout,
                defaultMaxRetries,
                defaultBackoffMult));
    }

    /**
     * Get method to get a json object
     * @param url
     * @param putInCache put result in cache or not
     * @param listener
     * @param errorListener
     */
    //*************************************************************************
	// GET REQUEST
	//*************************************************************************
    public static void getJsonObjectFromServer(String url, boolean putInCache, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new CachingJsonObjectRequest(url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
//                headers.put(API_KEY, App.getInstance().getValue(API_KEY1));
                return headers;
            }
        };
        request.setShouldCache(putInCache);
        addRequestPolicy(request);

        App.getInstance().addToRequestQueue(request, REQUEST_TAG + url.hashCode());
    }

    static void getStringFromServer(String url, boolean putInCache, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Utf8StringRequest stringObjReq = new Utf8StringRequest(Request.Method.GET, url, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
//                headers.put(API_KEY, App.getInstance().getValue(API_KEY1));
                return headers;
            }
        };
        stringObjReq.setShouldCache(putInCache);
        addRequestPolicy(stringObjReq);

        App.getInstance().addToRequestQueue(stringObjReq, REQUEST_TAG + url.hashCode());
    }


    /**
     * post request with header
     * @param url
     * @param data
     * @param listener
     * @param errorListener
     * @param userToken
     */
    public static void postJSONWithHeader(final String url, final Object data, Response.Listener<String> listener, final Response.ErrorListener errorListener, final String userToken)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
//                headers.put("X-API-TOKEN", BuildConfig.X_API_TOKEN);
                if (userToken != null) {
                    headers.put("Authorization", "Bearer " + userToken);
                }
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = data.toString();
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return new byte[0];
                }
            }
        };
        addCustomRequestPolicy(postRequest, REQUEST_CH_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(postRequest, REQUEST_TAG + "post");
    }

    public static void postWithHeader(final String url, final Map<String, String> data, Response.Listener<String> listener, final Response.ErrorListener errorListener, final String userToken)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
//                headers.put("X-API-TOKEN", BuildConfig.X_API_TOKEN);
                if (userToken != null) {
                    headers.put("Authorization", "Bearer " + userToken);
                }
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        addCustomRequestPolicy(postRequest, REQUEST_CH_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        App.getInstance().addToRequestQueue(postRequest, REQUEST_TAG + "post");
    }


    /**
     * post request with header
     * @param url
     * @param data
     * @param listener
     * @param errorListener
     */
    public static void putJSONWithHeader(String url, final JSONObject data, final Map<String, String> customHeaders, Response.Listener<String> listener, final Response.ErrorListener errorListener)
    {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
//                headers.put("X-API-TOKEN", BuildConfig.X_API_TOKEN);
                headers.putAll(customHeaders);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = data.toString();
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return new byte[0];
                }
            }
        };
        addCustomRequestPolicy(postRequest, REQUEST_CH_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(postRequest, REQUEST_TAG + "put");
    }

    /**
     * Extracts a {@link Cache.Entry} from a {@link NetworkResponse}.
     * Cache-control headers are ignored. SoftTtl == 3 mins, ttl == 24 hours.
     * @param response The network response to parse headers from
     * @return a cache entry for the given response, or null if the response is not cacheable.
     */
    private static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        String serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = 180000L; // in 3 minutes cache will be hit, but also refreshed on background
//        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = now;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }

    /**
     * Method to get the cache of a request
     * @param url
     * @return
     */
    public static Cache.Entry getCache(String url) {
        com.android.volley.Cache cache = App.getInstance().getRequestQueue().getCache();
        return cache.get(url);
    }

    public static class CachingJsonObjectRequest extends JsonObjectRequest {

        CachingJsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(url, null, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, UTF_8));
                JSONObject jsonResponse = new JSONObject(jsonString);
                jsonResponse.put("headers", new JSONObject(response.headers));

                return Response.success(jsonResponse, parseIgnoreCacheHeaders(response));

            } catch (UnsupportedEncodingException | JSONException e) {
                return Response.error(new ParseError(e));
            }
        }
    }

    public static class Utf8StringRequest extends StringRequest {

        Utf8StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
                String utf8String = new String(response.data, UTF_8);
                return Response.success(utf8String, parseIgnoreCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Erreur lors du parsing de la reponse", e);
            }

            return null;
        }
    }
}
