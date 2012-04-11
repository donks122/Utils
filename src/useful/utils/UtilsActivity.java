package useful.utils;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class UtilsActivity extends Activity {
    /** Called when the activity is first created. */
  private SQLiteDatabase database;
  private DataBaseHelper helper;
  private Singleton singleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        singleton = Singleton.getInstance();
        singleton.setIsonline(Utils.getOnlineStatus(this));
        
        helper = DataBaseHelper.getInstance(this);
        database = helper.getWritableDatabase();
        String sql = "SOME SQL";
        database.execSQL(sql);
        
    }
}