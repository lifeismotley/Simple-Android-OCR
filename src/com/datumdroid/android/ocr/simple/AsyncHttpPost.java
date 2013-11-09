package com.datumdroid.android.ocr.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpPost extends AsyncTask<String, String, String> {
	private byte[] mData = null;// post data

	private static final String TAG = "SimpleAndroidOCR.java";

	/**
	 * constructor
	 */
	public AsyncHttpPost(byte[] data) {
		mData = data;
	}

	/**
	 * background
	 */
	@Override
	protected String doInBackground(String... params) {
		byte[] result = null;
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(params[0]);// in this case, params[0]
												// is URL
		try {
			// set up post data
			// ArrayList<NameValuePair> nameValuePair = new
			// ArrayList<NameValuePair>();
			// nameValuePair.add(new BasicNameValuePair("file", mData));
			//
			// post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
			// HttpResponse response = client.execute(post);
			// StatusLine statusLine = response.getStatusLine();
			// if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
			//
			// Log.v(TAG,"IT WORKED!!!");
			// result = EntityUtils.toByteArray(response.getEntity());
			// str = new String(result, "UTF-8");
			// } else {
			//
			// Log.v(TAG,"DIDN'T WORK!!!");
			// }
			MultipartEntityBuilder mpe = MultipartEntityBuilder.create();
			// mpe.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			mpe.addPart("file",
					new ByteArrayBody(mData, ContentType.create("image/jpeg"),
							"upload.jpg"));

			post.setEntity(mpe.build());
			HttpResponse resp = client.execute(post);
			Log.w(TAG, "Response code: " + resp.getStatusLine());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					resp.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.v(TAG, str.toString());
		return str.toString();
	}

	/**
	 * on getting result
	 */
	@Override
	protected void onPostExecute(String result) {

		Log.v(TAG, "IT WORKED!!!");
	}
}
