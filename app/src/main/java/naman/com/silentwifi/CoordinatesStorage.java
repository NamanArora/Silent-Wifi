package naman.com.silentwifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Naman on 11-04-2017.
 */

public class CoordinatesStorage extends SQLiteOpenHelper {
    private static final int db_version = 1;
    private static final String db_name = "Coordinates.db";

    public static class DB implements BaseColumns
    {
        public static final String table_name = "LOCATION";
        public static final String latitude = "latitude";
        public static final String longitude = "longitude";
    }

    public CoordinatesStorage(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DB.table_name + "("
                + DB.latitude + " FLOAT,"
                + DB.longitude + " FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
