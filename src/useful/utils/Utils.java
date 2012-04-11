package useful.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

public class Utils {

	private static final Object[] MONTHS = { null, "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
			is.close();
			os.close();
			is = null;
			os = null;
		} catch (Exception ex) {
		}
	}

	public static Bitmap decodeFile(File f, int max_size) {
		Bitmap b = null;
		Config quality = Config.ARGB_8888;
		int IMAGE_MAX_SIZE = 1024;
		if (max_size > 0)
			IMAGE_MAX_SIZE = max_size;
		try {
			if (android.os.Build.VERSION.SDK_INT < 9) {
				IMAGE_MAX_SIZE = 256;
				quality = Config.ARGB_4444;
			}

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();

			int scale = 1;
			if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
				scale = (int) Math.pow(
						2,
						(int) Math.round(Math.log(IMAGE_MAX_SIZE
								/ (double) Math.max(o.outHeight, o.outWidth))
								/ Math.log(0.5)));
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inPreferredConfig = quality;
			o2.inDither = false;
			o2.inPurgeable = true;
			o2.inInputShareable = true;
			o2.inTempStorage = new byte[32 * 1024];
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
		}

		return b;
	}

	public static boolean getOnlineStatus(Activity ack) {

    boolean status = false;

		  int rcode;
		  try {
		  
		  String testUrl = "http://www.apple.com/library/test/success.html";
		  HttpURLConnection connection;
		  
		  URL url = new URL(testUrl);
		  
		  connection = (HttpURLConnection) url.openConnection();
		  
		  connection.setConnectTimeout(4000);
		  connection.setRequestMethod("GET");
		  
		  rcode = connection.getResponseCode();
		  
		 if (rcode == 200) { BufferedReader in = null; in = new
		  BufferedReader(new InputStreamReader( url.openStream()));
		  
		  String t;
		  while ((t = in.readLine()) != null) { if
		  (t.toLowerCase().contains("success")) status = true; } } } catch
		 (Exception e) { status = false; }
		  
		  return status;
		 
	}

	public static Bitmap processBitmap(Bitmap bm) {

		Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_4444);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height, deafaultPaint);
		canvas.drawBitmap(bitmap, 0, height, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmap.getHeight(), paint);
		return bitmap;

	}

	public static String formatDate(String date) {
		String month = "";
		String datecolumns[] = date.split("/");

		try {
			month = (String) MONTHS[Integer.parseInt((String) datecolumns[1])];
		} catch (Exception e) {
			// Defaults to Jan
			month = (String) MONTHS[1];
		}

		String formatedDate = datecolumns[2] + "-" + month + "-"
				+ datecolumns[0];
		return formatedDate;
	}

	private static Object getConnection(String u) {
		URL url = null;
		URLConnection tc = null;

		// Create URL and connection Objects
		try {
			url = new URL(u);
			tc = url.openConnection();
		}

		catch (MalformedURLException e) {
			return null;
		}

		catch (IOException e) {
			return null;
		}

		catch (Exception e) {
			return null;
		}

		return tc;

	}

	public static JSONObject getJSON(String url1) {
		JSONObject myjson = null;

		BufferedReader in = null;
		URLConnection tc = (URLConnection) getConnection(url1);

		if (tc == null) {
			// This needs to return some kind of error object
			return null;
		}

		try {

			in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
			String line = "";
			String JsonContent = "";
			while ((line = in.readLine()) != null) {
				JsonContent = JsonContent + line;
			}
			myjson = new JSONObject(JsonContent);
		}

		catch (IOException e) {
			return null;
		}

		catch (JSONException e) {
			return null;
		}

		catch (Exception e) {
			return null;
		}

		return myjson;
	}

	public static JSONObject getJsonFromFile(String path) {
		JSONObject json = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					new File(path)));
			String line = "";
			StringBuilder jsonContent = new StringBuilder();
			while ((line = in.readLine()) != null) {
				jsonContent.append(line);
			}
			json = new JSONObject(jsonContent.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
