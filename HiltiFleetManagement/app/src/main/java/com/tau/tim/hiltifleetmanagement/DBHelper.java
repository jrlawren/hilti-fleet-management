package com.tau.tim.hiltifleetmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolSchedule;

import java.util.ArrayList;

/**
 * Created by Tim on 10/17/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String SEARCH_UNIQUE = "Search Unique";
    public static final String SEARCH_LIKE = "Search Like";
    public static int ID;

    private static final String DATABASE_NAME = "hiltiDB";

    private static final String PROJECT_TABLE = "project";
    private static final String PROJECT_PROJECTID = "projectID";
    private static final String PROJECT_CLIENTID = "clientID";
    private static final String PROJECT_PROJECT_NAME = "projectName";
    private static final String PROJECT_STARTDATE = "startDate";
    private static final String PROJECT_ENDDATE = "endDate";

    private static final String OWNED_TOOLS_TABLE = "ownedTools";
    private static final String OWNED_TOOLS_TOOLID = "toolID";
    private static final String OWNED_TOOLS_CLIENTID = "clientID";
    private static final String OWNED_TOOLS_TOOLNAME = "toolName";
    private static final String OWNED_TOOLS_MODELNUMBER = "modelNumber";
    private static final String OWNED_TOOLS_OWNERSHIP = "ownership";
    private static final String OWNED_TOOLS_STATUS = "status";
    private static final String OWNED_TOOLS_PURCHASEDATE = "purchaseDate";
    private static final String OWNED_TOOLS_RETURNDATE = "returnDate";
    private static final String OWNED_TOOLS_REPLACEMENTID = "replacementID";

    private static final String TOOL_INFO_TABLE = "toolInfo";
    private static final String TOOL_INFO_TOOLNAME = "toolName";
    private static final String TOOL_INFO_MODELNUMBER = "modelNumber";
    private static final String TOOL_INFO_PRICE = "price";
    private static final String TOOL_INFO_FEATURES = "features";
    private static final String TOOL_INFO_DESCRIPTION = "description";
    private static final String TOOL_INFO_EFFECTIVEDATE = "effectiveDate";

    private static final String TOOL_SCHEDULE_TABLE = "toolSchedule";
    private static final String TOOL_SCHEDULE_RENTALID = "rentalID";
    private static final String TOOL_SCHEDULE_TOOLID = "toolID";
    private static final String TOOL_SCHEDULE_PROJECTID = "projectID";
    private static final String TOOL_SCHEDULE_STARTDATE = "startDate";
    private static final String TOOL_SCHEDULE_ENDDATE = "endDate";

    private static final String CLIENT_TABLE = "client";
    private static final String CLIENT_CLIENTID = "clientID";
    private static final String CLIENT_CLIENTNAME = "name";
    private static final String CLIENT_CLIENTUSERNAME = "username";
    private static final String CLIENT_EMAIL = "email";
    private static final String CLIENT_PASSWORD = "phone";

    private static final String CREATE_PROJECT = "Create table " + PROJECT_TABLE + " ("
            + PROJECT_PROJECTID + " INTEGER PRIMARY KEY ASC, "
            + PROJECT_CLIENTID + " INTEGER, "
            + PROJECT_PROJECT_NAME + " TEXT, "
            + PROJECT_STARTDATE + " TEXT, "
            + PROJECT_ENDDATE + " TEXT, "
            + "FOREIGN KEY(" + PROJECT_CLIENTID + ") REFERENCES " + CLIENT_TABLE + "(" + CLIENT_CLIENTID + "))";

    private static final String CREATE_OWNED_TOOLS = "create table " + OWNED_TOOLS_TABLE + " ("
            + OWNED_TOOLS_TOOLID + " INTEGER PRIMARY KEY ASC, "
            + OWNED_TOOLS_CLIENTID + " INTEGER, "
            + OWNED_TOOLS_TOOLNAME + " TEXT, "
            + OWNED_TOOLS_MODELNUMBER + " INTEGER,"
            + OWNED_TOOLS_OWNERSHIP + " TEXT, "
            + OWNED_TOOLS_STATUS + " TEXT, "
            + OWNED_TOOLS_PURCHASEDATE + " TEXT, "
            + OWNED_TOOLS_RETURNDATE + " TEXT, "
            + OWNED_TOOLS_REPLACEMENTID + " INTEGER, "
            + "FOREIGN KEY(" + OWNED_TOOLS_CLIENTID + ") REFERENCES " + CLIENT_TABLE + "(" + CLIENT_CLIENTID + ")"
            + "FOREIGN KEY(" + OWNED_TOOLS_MODELNUMBER + ") REFERENCES " + TOOL_INFO_TABLE + "(" + TOOL_INFO_MODELNUMBER + ")"
            + "FOREIGN KEY(" + OWNED_TOOLS_TOOLNAME + ") REFERENCES " + TOOL_INFO_TABLE + "(" + TOOL_INFO_TOOLNAME + "))";

    private static final String CREATE_TOOL_INFO = "create table " + TOOL_INFO_TABLE + " ("
            + TOOL_INFO_TOOLNAME + " TEXT,"
            + TOOL_INFO_MODELNUMBER + " TEXT,"
            + TOOL_INFO_PRICE + " REAL,"
            + TOOL_INFO_FEATURES + " TEXT,"
            + TOOL_INFO_DESCRIPTION + " TEXT,"
            + TOOL_INFO_EFFECTIVEDATE + " TEXT,"
            + " PRIMARY KEY(" + TOOL_INFO_TOOLNAME + ", " + TOOL_INFO_MODELNUMBER + "))";

    private static final String CREATE_TOOL_SCHEDULE = "create table " + TOOL_SCHEDULE_TABLE + " ("
            + TOOL_SCHEDULE_RENTALID + " INTEGER PRIMARY KEY ASC, "
            + TOOL_SCHEDULE_TOOLID + " INTEGER, "
            + TOOL_SCHEDULE_PROJECTID + " INTEGER, "
            + TOOL_SCHEDULE_STARTDATE + " TEXT, "
            + TOOL_SCHEDULE_ENDDATE + " TEXT, "
            + "FOREIGN KEY(" + TOOL_SCHEDULE_TOOLID + ") REFERENCES " + OWNED_TOOLS_TABLE + "(" + OWNED_TOOLS_TOOLID + ")"
            + "FOREIGN KEY(" + TOOL_SCHEDULE_PROJECTID + ") REFERENCES " + PROJECT_TABLE + "(" + PROJECT_PROJECTID + "))";

    private static final String CREATE_CLIENT = "create table " + CLIENT_TABLE + " ("
            + CLIENT_CLIENTID + " INTEGER PRIMARY KEY ASC, "
            + CLIENT_CLIENTNAME + " TEXT, "
            + CLIENT_CLIENTUSERNAME + " TEXT, "
            + CLIENT_EMAIL + " TEXT, "
            + CLIENT_PASSWORD + " TEXT) ";

    public DBHelper(Context context){

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENT);
        db.execSQL(CREATE_TOOL_INFO);
        db.execSQL(CREATE_PROJECT);
        db.execSQL(CREATE_OWNED_TOOLS);
        db.execSQL(CREATE_TOOL_SCHEDULE);

        //Dummy data for the Client table
        db.execSQL("insert into " + CLIENT_TABLE + " values (1, 'Customer', 'HiltiCustomer', 'hilticustomer777@gmail.com', 'fleetmanagement')");
        db.execSQL("insert into " + CLIENT_TABLE + " values (2, 'John Deere Construction equipment', 'Deere', 'hilticustomer777@gmail.com', 'fleetmanagement')");

        //Dummy data for the Project table
        db.execSQL("insert into " + PROJECT_TABLE + " values (1, 1, 'Torrens Building Renovation', '2015-06-01, '2015-12-31')");
        db.execSQL("insert into " + PROJECT_TABLE + " values (2, 1, 'Central Market Expansion', '2015-10-01', '2016-02-28')");
        db.execSQL("insert into " + PROJECT_TABLE + " values (3, 1, 'Bathroom Upgrade', '2016-01-01', '2016-02-15')");
        //db.execSQL("insert into " + PROJECT_TABLE + " values (4, 2, 'ANZ Building Construction', '2015-12-1', '2016-06-30')");
        //db.execSQL("insert into " + PROJECT_TABLE + " values (5, 2, 'Victoria Square Christmas Tree', '2015-11-27', '2016-01-15')");
        //db.execSQL("insert into " + PROJECT_TABLE + " values (6, 2, 'Ernst & Young Office Construction', '2015-11-01', '2016-09-23')");

        //Dummy data for the Tool Information table25
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Standard Drill', 1, 150.00, 'Someone more creative than me, please list some features.', 'Your everyday multipurpose drill!', '2015-01-01')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Standard Drill', 2, 150.00, 'Someone more creative than me, please list some features.', 'Your everyday multipurpose drill! But better!', '2015-10-01')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Angle Grinder' , 1, 200.50, 'Someone more creative than me, please list some features.', 'Cut anything at any angle', '2015-03-01')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Laser Cutter', 1, 550.00, 'Someone more creative than me, please list some features.', 'Cut with the highest precision', '2015-11-27')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Really Big Crane', 1, '500,000.00', 'Someone more creative than me, please list some features.', 'A really big crane for really big things', '2015-01-05')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Circular Saw', 1, '200.00', 'Someone more creative than me, please list some features.', 'It goes round and round really fast!', '2015-01-24')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Circular Saw', 2, '225.00', 'Someone more creative than me, please list some features.', 'It goes round and round even faster!', '2015-06-01')");
        db.execSQL("insert into " + TOOL_INFO_TABLE + " values ('Air Hammer', 1, '175.00', 'Someone more creative than me, please list some features.', 'Not sure how this works...', '2015-04-01')");

        //Dummy data for the Owned Tools
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (1, 1, 'Standard Drill', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (2, 1, 'Standard Drill', 1, 'Owned', 'OK', '2015-06-04', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (3, 1, 'Standard Drill', 1, 'Owned', 'Broken', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (4, 1, 'Standard Drill', 2, 'Owned', 'OK', '2015-10-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (5, 1, 'Angle Grinder', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (6, 1, 'Laser Cutter', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (7, 1, 'Really Big Crane', 1, 'Leased', 'OK', '2016-01-05', '2016-01-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (8, 1, 'Circular Saw', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (9, 1, 'Circular Saw', 2, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (10, 1, 'Air Hammer', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        /*
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (11, 1, 'Angle Grinder', 1, 'Loaned', 'OK', '2015-04-05', '2015-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (12, 1, 'Laser Cutter', 1, 'Leased', 'OK', '2015-06-05', '2015-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (13, 1, 'Circular Saw', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (14, 1, 'Circular Saw', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (15, 1, 'Circular Saw', 1, 'Owned', 'OK', '2015-01-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (16, 1, 'Circular Saw', 1, 'Owned', 'OK', '2015-03-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (17, 1, 'Circular Saw', 2, 'Owned', 'OK', '2015-10-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (18, 1, 'Circular Saw', 2, 'Owned', 'OK', '2015-10-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (19, 1, 'Circular Saw', 2, 'Owned', 'OK', '2015-10-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (20, 1, 'Really Big Crane', 1, 'Leased', 'OK', '2015-06-05', '2015-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (21, 1, 'Really Big Crane', 1, 'Leased', 'OK', '2015-06-05', '2016-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (22, 1, 'Really Big Crane', 1, 'Leased', 'OK', '2015-06-05', '2016-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (23, 1, 'Really Big Crane', 1, 'Leased', 'OK', '2015-06-05', '2016-12-31', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (24, 1, 'Air Hammer', 1, 'Owned', 'OK', '2015-05-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (25, 1, 'Air Hammer', 1, 'Owned', 'OK', '2015-05-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (26, 1, 'Air Hammer', 1, 'Owned', 'OK', '2015-05-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (27, 1, 'Air Hammer', 2, 'Owned', 'OK', '2015-05-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (28, 1, 'Air Hammer', 2, 'Owned', 'OK', '2015-05-05', '', '')");
        db.execSQL("insert into " + OWNED_TOOLS_TABLE + " values (29, 1, 'Air Hammer', 2, 'Owned', 'OK', '2015-05-05', '', '')");
*/

        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (1, 1, 1, '2015-06-14', '2015-12-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (2, 2, 2, '2015-10-14', '2015-12-31')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (3, 1, 2, '2015-11-14', '2015-11-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (4, 4, 1, '2015-06-14', '2015-12-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (5, 5, 2, '2015-11-14', '2015-11-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (6, 6, 3, '2016-01-14', '2016-02-15')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (7, 7, 1, '2015-06-14', '2015-12-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (8, 8, 2, '2015-11-14', '2016-02-27')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (9, 9, 3, '2016-01-01', '2016-02-01')");
        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values (10, 10, 1, '2015-06-14', '2015-12-27')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Project> getProjects(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Project> projects = new ArrayList<Project>();
        int projectID;
        String projectName;
        String startDate;
        String endDate;

        Cursor res = db.rawQuery("Select " + PROJECT_PROJECTID + ", " +
                PROJECT_PROJECT_NAME + ", " +
                PROJECT_STARTDATE + ", " +
                PROJECT_ENDDATE +
                " from " + PROJECT_TABLE +
                " where " + PROJECT_PROJECT_NAME + " like '%" + name + "%' and " +
                PROJECT_CLIENTID + " = " + this.ID, null);

        res.moveToFirst();
        while(!res.isAfterLast()){
            projectID = res.getInt(res.getColumnIndex(PROJECT_PROJECTID));
            projectName = res.getString(res.getColumnIndex(PROJECT_PROJECT_NAME));
            startDate = res.getString(res.getColumnIndex(PROJECT_STARTDATE));
            endDate = res.getString(res.getColumnIndex(PROJECT_ENDDATE));
            projects.add(new Project(projectID, projectName, startDate, endDate));
            res.moveToNext();
        }
        res.close();

        return projects;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public boolean saveProject(int clientID, String projectName, String startDate, String endDate, boolean isUpdate){
        SQLiteDatabase db = this.getWritableDatabase();

        if(!isUpdate) {
            Cursor res = db.rawQuery("Select P." + PROJECT_PROJECT_NAME +
                    " from " + PROJECT_TABLE + " as P" +
                    " where lower(P." + PROJECT_PROJECT_NAME + ") = lower('" + projectName + "')", null);

            res.moveToFirst();

            if (res.getCount() > 0) {
                res.close();
                return false;
            }

            db.execSQL("Insert into " + PROJECT_TABLE + " values (" +
                    "(Select max(" + PROJECT_PROJECTID + ")+1 from " + PROJECT_TABLE + "), " +
                    this.ID + ", '" +
                    projectName + "', '" +
                    startDate + "', '" +
                    endDate + "')");
            return true;
        }else{
            db.execSQL("update " + PROJECT_TABLE +
                    " set " + PROJECT_STARTDATE + " = '" + startDate + "', " + PROJECT_ENDDATE + " = '" + endDate + "'" +
                    " where lower(" + PROJECT_PROJECT_NAME + ") = lower('" + projectName + "')");

            db.execSQL("update " + TOOL_SCHEDULE_TABLE +
                    " set " + TOOL_SCHEDULE_ENDDATE + " = '" + endDate + "'" +
                    " where " + TOOL_SCHEDULE_RENTALID + " in (" +
                    " select TS." + TOOL_SCHEDULE_RENTALID +
                    " from " + TOOL_SCHEDULE_TABLE + " as TS, " +
                    PROJECT_TABLE + " as P " +
                    "where TS." + TOOL_SCHEDULE_PROJECTID + " = P." + PROJECT_PROJECTID + " and " +
                    "lower(P." + PROJECT_PROJECT_NAME + ") = lower('" + projectName + "') and " +
                    "TS." + TOOL_SCHEDULE_ENDDATE + " > " + endDate + ")");
            return true;
        }
    }

    public ArrayList<Tool> getTools(String toolNameSearch, int clientID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Tool> toolArrayList = new ArrayList<Tool>();
        String toolName;
        String modelNumber;
        String description;
        int toolCount;

        Cursor res = db.rawQuery("Select distinct T." + TOOL_INFO_TOOLNAME +
                ", T." + TOOL_INFO_MODELNUMBER +
                ", T." + TOOL_INFO_DESCRIPTION +
                ", count(O." + OWNED_TOOLS_TOOLID + ") as TOOLCOUNT" +
                " from " + TOOL_INFO_TABLE + " as T, " +
                OWNED_TOOLS_TABLE + " as O" +
                " where T." + TOOL_INFO_TOOLNAME + " = O." + OWNED_TOOLS_TOOLNAME + " and " +
                "T." + TOOL_INFO_MODELNUMBER + " = O." + OWNED_TOOLS_MODELNUMBER + " and " +
                "O." + OWNED_TOOLS_CLIENTID + " = " + this.ID + " and " +
                "T." + TOOL_INFO_TOOLNAME + " like '%" + toolNameSearch + "%'" +
                " group by T." + TOOL_INFO_TOOLNAME + ", T." + TOOL_INFO_MODELNUMBER + ", T." + TOOL_INFO_DESCRIPTION, null);


        res.moveToFirst();
        while(!res.isAfterLast()){
            toolName = res.getString(res.getColumnIndex(TOOL_INFO_TOOLNAME));
            modelNumber = res.getString(res.getColumnIndex(TOOL_INFO_MODELNUMBER));
            description = res.getString(res.getColumnIndex(TOOL_INFO_DESCRIPTION));
            toolCount = res.getInt(res.getColumnIndex("TOOLCOUNT"));

            toolArrayList.add(new Tool(toolName, modelNumber, description, toolCount));
            res.moveToNext();
        }

        res.close();

        return toolArrayList;
    }

    public ArrayList<Tool> getToolID(String searchToolID, int clientID, String searchType){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Tool> toolArrayList = new ArrayList<Tool>();
        int toolID = 0;
        String toolName = "";
        String toolModelNumber = "";
        String ownership = "";
        String status = "";
        String purchaseDate = "";
        String returnDate = "";
        String beginningSQL = "";
        String endSQL = "";

        if(searchType.equals(SEARCH_UNIQUE)){
            beginningSQL = " = '";
            endSQL = "'";
        }else if (searchType.equals(SEARCH_LIKE)){
            beginningSQL = " like '%";
            endSQL = "%'";
        }

        Cursor res = db.rawQuery("Select O." + OWNED_TOOLS_TOOLID +
                ", T." + TOOL_INFO_TOOLNAME +
                ", T." + TOOL_INFO_MODELNUMBER +
                ", O." + OWNED_TOOLS_OWNERSHIP +
                ", O." + OWNED_TOOLS_STATUS +
                ", O." + OWNED_TOOLS_PURCHASEDATE +
                ", O." + OWNED_TOOLS_RETURNDATE +
                " from " + TOOL_INFO_TABLE + " as T, " +
                OWNED_TOOLS_TABLE + " as O" +
                " where T." + TOOL_INFO_TOOLNAME + " = O." + OWNED_TOOLS_TOOLNAME + " and " +
                "T." + TOOL_INFO_MODELNUMBER + " = O." + OWNED_TOOLS_MODELNUMBER + " and " +
                "O." + OWNED_TOOLS_CLIENTID + " = " + this.ID + " and " +
                "O." + OWNED_TOOLS_TOOLID + beginningSQL + searchToolID + endSQL, null);

        res.moveToFirst();

        while(!res.isAfterLast()){
            toolID = res.getInt(res.getColumnIndex(OWNED_TOOLS_TOOLID));
            toolName = res.getString(res.getColumnIndex(TOOL_INFO_TOOLNAME));
            toolModelNumber = res.getString(res.getColumnIndex(TOOL_INFO_MODELNUMBER));
            ownership = res.getString(res.getColumnIndex(OWNED_TOOLS_OWNERSHIP));
            status = res.getString(res.getColumnIndex(OWNED_TOOLS_STATUS));
            purchaseDate = res.getString(res.getColumnIndex(OWNED_TOOLS_PURCHASEDATE));
            returnDate = res.getString(res.getColumnIndex(OWNED_TOOLS_RETURNDATE));

            toolArrayList.add(new Tool(toolID, toolName, toolModelNumber, ownership, status, purchaseDate, returnDate));
            res.moveToNext();
        }
        res.close();

        return toolArrayList;
    }

    public ArrayList<Tool> getToolByName(String toolName, String modelNumber, int clientID, String projectID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Tool> toolArrayList = new ArrayList<Tool>();
        int toolID = 0;
        String toolModelNumber = "";
        String ownership = "";
        String status = "";
        String purchaseDate = "";
        String returnDate = "";
        String searchModelQuery = "";
        String searchProjectQuery = "";

        if(!modelNumber.equals("")){
            searchModelQuery = " and T." + TOOL_INFO_MODELNUMBER + " = '" + modelNumber + "'";
        }


        if(!projectID.equals("")){
            searchProjectQuery = " and O." + OWNED_TOOLS_TOOLID + " in (select TS." + TOOL_SCHEDULE_TOOLID +
                    " from " + TOOL_SCHEDULE_TABLE + " as TS where TS." + TOOL_SCHEDULE_PROJECTID + " = '" + projectID + "' and " +
                    "TS." + TOOL_SCHEDULE_TOOLID + " = O." + OWNED_TOOLS_TOOLID + ")";
        }

        Cursor res = db.rawQuery("Select O." + OWNED_TOOLS_TOOLID +
                ", T." + TOOL_INFO_TOOLNAME +
                ", T." + TOOL_INFO_MODELNUMBER +
                ", O." + OWNED_TOOLS_OWNERSHIP +
                ", O." + OWNED_TOOLS_STATUS +
                ", O." + OWNED_TOOLS_PURCHASEDATE +
                ", O." + OWNED_TOOLS_RETURNDATE +
                " from " + TOOL_INFO_TABLE + " as T, " +
                OWNED_TOOLS_TABLE + " as O" +
                " where T." + TOOL_INFO_TOOLNAME + " = O." + OWNED_TOOLS_TOOLNAME + " and " +
                "T." + TOOL_INFO_MODELNUMBER + " = O." + OWNED_TOOLS_MODELNUMBER + " and " +
                "O." + OWNED_TOOLS_CLIENTID + " = " + clientID + " and " +
                "T." + TOOL_INFO_TOOLNAME + " = '" + toolName + "'" +
                searchModelQuery + searchProjectQuery, null);

        res.moveToFirst();

        while(!res.isAfterLast()){
            toolID = res.getInt(res.getColumnIndex(OWNED_TOOLS_TOOLID));
            toolName = res.getString(res.getColumnIndex(TOOL_INFO_TOOLNAME));
            toolModelNumber = res.getString(res.getColumnIndex(TOOL_INFO_MODELNUMBER));
            ownership = res.getString(res.getColumnIndex(OWNED_TOOLS_OWNERSHIP));
            status = res.getString(res.getColumnIndex(OWNED_TOOLS_STATUS));
            purchaseDate = res.getString(res.getColumnIndex(OWNED_TOOLS_PURCHASEDATE));
            returnDate = res.getString(res.getColumnIndex(OWNED_TOOLS_RETURNDATE));

            toolArrayList.add(new Tool(toolID, toolName, toolModelNumber, ownership, status, purchaseDate, returnDate));
            res.moveToNext();
        }
        res.close();

        return toolArrayList;
    }

    public ArrayList<ToolSchedule> getToolSchedule(String toolID, int clientID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ToolSchedule> scheduleArrayList = new ArrayList<ToolSchedule>();
        int rentalID = 0;
        int projectID = 0;
        String projectName = "";
        String startDate = "";
        String endDate = "";


        Cursor res = db.rawQuery("Select TS.*, " + "P." + PROJECT_PROJECT_NAME +
                " from " + TOOL_SCHEDULE_TABLE + " as TS, " +
                PROJECT_TABLE + " as P" +
                " where TS." + TOOL_SCHEDULE_TOOLID + " = " + toolID + " and " +
                "TS." + TOOL_SCHEDULE_PROJECTID + " = P." + PROJECT_PROJECTID + " and " +
                "P." + PROJECT_CLIENTID + " = " + this.ID, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            rentalID = res.getInt(res.getColumnIndex(TOOL_SCHEDULE_RENTALID));
            projectID = res.getInt(res.getColumnIndex(TOOL_SCHEDULE_PROJECTID));
            projectName = res.getString(res.getColumnIndex(PROJECT_PROJECT_NAME));
            startDate = res.getString(res.getColumnIndex(TOOL_SCHEDULE_STARTDATE));
            endDate = res.getString(res.getColumnIndex(TOOL_SCHEDULE_ENDDATE));

            scheduleArrayList.add(new ToolSchedule(rentalID, projectID, projectName, startDate, endDate));
            res.moveToNext();
        }

        res.close();

        return scheduleArrayList;
    }

    public ArrayList<Tool> getToolByProject(String projectID, String clientID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Tool> toolArrayList = new ArrayList<Tool>();
        String toolName = "";
        String toolModelNumber = "";
        String toolDescription = "";
        int toolCount = 0;

        Cursor res = db.rawQuery("Select distinct T." + TOOL_INFO_TOOLNAME +
                ", T." + TOOL_INFO_MODELNUMBER +
                ", T." + TOOL_INFO_DESCRIPTION +
                ", count(distinct O." + OWNED_TOOLS_TOOLID + ") as TOOLCOUNT" +
                " from " + TOOL_INFO_TABLE + " as T, " +
                OWNED_TOOLS_TABLE + " as O, " +
                TOOL_SCHEDULE_TABLE + " as TS" +
                " where TS." + TOOL_SCHEDULE_TOOLID + " = O." + OWNED_TOOLS_TOOLID + " and " +
                "T." + TOOL_INFO_TOOLNAME + " = O." + OWNED_TOOLS_TOOLNAME + " and " +
                "T." + TOOL_INFO_MODELNUMBER + " = O." + OWNED_TOOLS_MODELNUMBER + " and " +
                "TS." + TOOL_SCHEDULE_PROJECTID + " = " + projectID +
                " group by T." + TOOL_INFO_TOOLNAME + ", T." + TOOL_INFO_MODELNUMBER + ", T." + TOOL_INFO_DESCRIPTION, null);

        res.moveToFirst();

        while(!res.isAfterLast()){
            toolName = res.getString(res.getColumnIndex(TOOL_INFO_TOOLNAME));
            toolModelNumber = res.getString(res.getColumnIndex(TOOL_INFO_MODELNUMBER));
            toolDescription = res.getString(res.getColumnIndex(TOOL_INFO_DESCRIPTION));
            toolCount = res.getInt(res.getColumnIndex("TOOLCOUNT"));

            toolArrayList.add(new Tool(toolName, toolModelNumber, toolDescription, toolCount));
            res.moveToNext();
        }
        res.close();

        return toolArrayList;
    }

    public boolean assignTool(String projectID, String toolName, String modelNumber, String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectedToolID = "";

        startDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8);
        endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8);

        Cursor res = db.rawQuery("select OT." + OWNED_TOOLS_TOOLID + " " +
                "from " + OWNED_TOOLS_TABLE + " as OT " +
                "where OT.toolName = '" + toolName + "' and " +
                "OT." + OWNED_TOOLS_MODELNUMBER + " = '" + modelNumber + "' and " +
                "OT." + OWNED_TOOLS_STATUS + " = 'OK' and " +
                "OT." + OWNED_TOOLS_CLIENTID + " = " + this.ID + " " + " and " +
                "OT." + OWNED_TOOLS_PURCHASEDATE + " < '" + startDate + "' and " +
                "(OT." + OWNED_TOOLS_RETURNDATE + " = ''" + " or " +
                "OT." + OWNED_TOOLS_RETURNDATE + " > '" + endDate + "') and " +
                "OT." + OWNED_TOOLS_TOOLID + " not in (" +
                "select TS." + TOOL_SCHEDULE_TOOLID +
                " from " + TOOL_SCHEDULE_TABLE + " as TS " +
                "where TS." + TOOL_SCHEDULE_TOOLID + " = OT." + OWNED_TOOLS_TOOLID + " and " +
                "(('" + startDate + "' > TS." + TOOL_SCHEDULE_STARTDATE + " and '" + startDate + "' < TS." + TOOL_SCHEDULE_ENDDATE + ") or " +
                "('" + endDate + "' > TS." + TOOL_SCHEDULE_STARTDATE + " and '" + endDate + "' < TS." + TOOL_SCHEDULE_ENDDATE + ") or " +
                "(TS." + TOOL_SCHEDULE_STARTDATE + " > '" + startDate + "' and TS." + TOOL_SCHEDULE_STARTDATE + " < '" + endDate + "') or " +
                "(TS." + TOOL_SCHEDULE_ENDDATE + " > '" + startDate + "' and TS." + TOOL_SCHEDULE_ENDDATE + " < '" + endDate + "')))", null);

                /*
                "(" + startDate + " between TS." + TOOL_SCHEDULE_STARTDATE + " and TS." + TOOL_SCHEDULE_ENDDATE + " or " +
                endDate + " between TS." + TOOL_SCHEDULE_STARTDATE + " and TS." + TOOL_SCHEDULE_ENDDATE + " or " +
                "TS." + TOOL_SCHEDULE_STARTDATE + " between " + startDate + " and " + endDate + " or " +
                "TS." + TOOL_SCHEDULE_ENDDATE + " between " + startDate + " and " + endDate + ")) limit 1", null);
                */

                //"(Date(" + startDate + ") between Date(substr(TS." + TOOL_SCHEDULE_STARTDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 9, 2)) and " +
                //"Date(substr(TS." + TOOL_SCHEDULE_ENDDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 9, 2)) or " +
                //"Date(" + endDate + ") between Date(substr(TS." + TOOL_SCHEDULE_STARTDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 9, 2)) and " +
                //"Date(substr(TS." + TOOL_SCHEDULE_ENDDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 9, 2)) or " +
                //"Date(substr(TS." + TOOL_SCHEDULE_STARTDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_STARTDATE + ", 9, 2)) between Date(" + startDate + ") and Date(" + endDate  +") or " +
                //"Date(substr(TS." + TOOL_SCHEDULE_ENDDATE + ",1, 4)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 6, 2)||substr(TS." + TOOL_SCHEDULE_ENDDATE + ", 9, 2)) between Date(" + startDate + ") and Date(" + endDate + "))) limit 1", null);
        res.moveToFirst();

        if(res.getCount() == 0){
            res.close();
            return false;
        }

        while(!res.isAfterLast()){
            selectedToolID = Integer.toString(res.getInt(res.getColumnIndex(OWNED_TOOLS_TOOLID)));
            res.moveToNext();
        }

        res.close();

        db.execSQL("insert into " + TOOL_SCHEDULE_TABLE + " values((select max(" + TOOL_SCHEDULE_RENTALID +
                ")+1 from " + TOOL_SCHEDULE_TABLE + "), " + selectedToolID + ", " + projectID + ", '" + startDate + "', '" + endDate + "')");

        return true;
    }

    public void updateToolStatus(String toolID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update " + OWNED_TOOLS_TABLE + " set " + OWNED_TOOLS_STATUS + " = 'Broken' where " + OWNED_TOOLS_TOOLID + " = '" + toolID + "'");
    }

    public Project getProjectName(String projectID){
        SQLiteDatabase db = this.getWritableDatabase();

        Project project = new Project();
        String projectName;
        String startDate;
        String endDate;

        Cursor res = db.rawQuery("Select " + PROJECT_PROJECTID + ", " +
                PROJECT_PROJECT_NAME + ", " +
                PROJECT_STARTDATE + ", " +
                PROJECT_ENDDATE +
                " from " + PROJECT_TABLE +
                " where " + PROJECT_PROJECTID + " = " + projectID, null);

        res.moveToFirst();
        while(!res.isAfterLast()){
            projectName = res.getString(res.getColumnIndex(PROJECT_PROJECT_NAME));
            startDate = res.getString(res.getColumnIndex(PROJECT_STARTDATE));
            endDate = res.getString(res.getColumnIndex(PROJECT_ENDDATE));
            project = new Project(Integer.parseInt(projectID), projectName, startDate, endDate);
            res.moveToNext();
        }
        res.close();

        return project;
    }

    public int getCred(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        String password;
        String username;
        int ID;

        Cursor res = db.rawQuery("Select " + CLIENT_CLIENTUSERNAME + ", " +
                CLIENT_PASSWORD + ", " +
                CLIENT_CLIENTID +
                " from " + CLIENT_TABLE +
                " where " + CLIENT_CLIENTUSERNAME + " like '%" + user + "%'", null);

        res.moveToFirst();
        while(!res.isAfterLast()){
            username = res.getString(res.getColumnIndex(CLIENT_CLIENTUSERNAME));
            password = res.getString(res.getColumnIndex(CLIENT_PASSWORD));
            ID = res.getInt(res.getColumnIndex(CLIENT_CLIENTID));
            if(username.equals(user)) {
                if (password.equals(pass)) {
                    this.ID = ID;
                    return 0;
                } else {
                    return 1;
                }
            }
            res.moveToNext();
        }
        res.close();

        return 2;

    }

    public int getID() {
        return this.ID;
    }

    public String getEmail() {
        SQLiteDatabase db = this.getWritableDatabase();

        String email;

        Cursor res = db.rawQuery("Select " + CLIENT_EMAIL +
                " from " + CLIENT_TABLE +
                " where " + CLIENT_CLIENTID + " = " + this.ID, null);

        res.moveToFirst();
        email = res.getString(res.getColumnIndex(CLIENT_EMAIL));
        res.close();
        return email;
    }

    public String getPassword() {
        SQLiteDatabase db = this.getWritableDatabase();

        String password;

        Cursor res = db.rawQuery("Select " + CLIENT_PASSWORD +
                " from " + CLIENT_TABLE +
                " where " + CLIENT_CLIENTID + " = " + this.ID, null);

        res.moveToFirst();
        password = res.getString(res.getColumnIndex(CLIENT_PASSWORD));
        res.close();
        return password;
    }
}