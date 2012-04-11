package useful.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/useful.utils/databases/";

	private static String DB_NAME = "database.db";

	private SQLiteDatabase myDataBase;

	private Context myContext;

	private static DataBaseHelper dataBaseHelper;

	private DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 3);
		myContext = context;
		File dbfile = new File(DB_PATH + DB_NAME);
		long filesize = 0;
		if (dbfile.exists())
			filesize = dbfile.length();
		if (filesize < 100)
			copyDataBase();

	}

	public static DataBaseHelper getInstance(Context context) {
		if (dataBaseHelper == null)
			dataBaseHelper = new DataBaseHelper(context);
		return dataBaseHelper;
	}

	private void copyDataBase() {
		// Open your local db as the input stream
		InputStream myInput;
		try {

			File rootdir = new File(DB_PATH);
			if (!rootdir.exists())
				rootdir.mkdirs();

			File databasePath = myContext.getDatabasePath(DB_NAME);
			if(databasePath.exists())
				databasePath.delete();
			databasePath.createNewFile();

			myInput = myContext.getAssets().open(DB_NAME);

			// Path to the just created empty db
			String outFileName = DB_PATH + DB_NAME;

			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (IOException e) {
		}

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String databasePath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(databasePath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
