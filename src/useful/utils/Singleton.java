package useful.utils;

import java.util.ArrayList;
import java.util.HashMap;

// A Singleton Class, Useful to save Config Parameters and initializing stuff
public class Singleton {
	private static Singleton singleton = null;

	private String assetUrl = "file:///android_asset";
	// Create Booleans here
	private boolean isAppActive = false;
	private boolean isonline = true;
	private String accountUrl;
	
	private HashMap<String, Integer> downloadStatus;

	public static Singleton getInstance() {
		if (singleton == null){
		  singleton = new Singleton();
		}
		return singleton;
	}
	
	private Singleton() {
		if(downloadStatus == null){
			downloadStatus = new HashMap<String, Integer>();
		}
	}

	public Integer getDownloadStatus(String url) {
		return downloadStatus.get(url);
	}

	public void setDownloadStatus(String key, Integer value) {
		this.downloadStatus.put(key, value);
	}

	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}

	public boolean isIsonline() {
		return isonline;
	}

	public void setAppActive(boolean isAppActive) {
		this.isAppActive = isAppActive;
	}

	public boolean isAppActive() {
		return isAppActive;
	}

	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

  public void setAccountUrl(String accountUrl) {
    this.accountUrl = accountUrl;
  }

  public String getAccountUrl() {
    return accountUrl;
  }

}
