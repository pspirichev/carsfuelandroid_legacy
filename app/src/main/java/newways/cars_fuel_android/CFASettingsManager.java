package newways.cars_fuel_android;

import android.content.Context;
import android.content.SharedPreferences;

public class CFASettingsManager
{
    private SharedPreferences sPref;
    private static final String S_PREF_NAME = "CFA_Settings";
    private static final String CUR         = "CurrencyName";
    private static final String CUR_ABR     = "CurrencyAbbr";
    private static final String VOL         = "Volume";
    private static final String VOL_ABR     = "VolumeAbbr";
    private static final String FUEL        = "FuelType";
    private static final String FUEL_ABR    = "FuelAbbr";
    private static final String CURRENT_CAR = "CurCar";

    private String  cur;
    private String  curAbr;
    private String  vol;
    private String  volAbr;
    private String  fuel;
    private String  fuelAbr;
    private long    curCar;

    CFASettingsManager(Context context){
        sPref = context.getSharedPreferences(S_PREF_NAME, Context.MODE_PRIVATE);
        sPref.getString(CUR, context.getResources().getStringArray(R.array.currency)[0]);
        sPref.getString(CUR_ABR, context.getResources().getStringArray(R.array.cur_abbr)[0]);
        sPref.getString(VOL, context.getResources().getStringArray(R.array.volume)[0]);
        sPref.getString(VOL_ABR, context.getResources().getStringArray(R.array.volume_abbr)[0]);
        sPref.getString(FUEL, context.getResources().getStringArray(R.array.fuel_types)[0]);
        sPref.getString(FUEL_ABR, context.getResources().getStringArray(R.array.fuel_abbr)[0]);

        if(cur == null)
            cur = context.getResources().getStringArray(R.array.currency)[0];
        if(curAbr == null)
            curAbr = context.getResources().getStringArray(R.array.cur_abbr)[0];
        if(vol == null)
            vol = context.getResources().getStringArray(R.array.volume)[0];
        if(volAbr == null)
            volAbr = context.getResources().getStringArray(R.array.volume_abbr)[0];
        if(fuel == null)
            fuel = context.getResources().getStringArray(R.array.fuel_types)[0];
        if(fuelAbr == null)
            fuelAbr = context.getResources().getStringArray(R.array.fuel_abbr)[0];
    }

    private boolean reWrite(){
        SharedPreferences.Editor spEdit = sPref.edit();
        spEdit.putString(CUR, cur);
        spEdit.putString(CUR_ABR, curAbr);
        spEdit.putString(VOL, vol);
        spEdit.putString(VOL_ABR, volAbr);
        spEdit.putString(FUEL, fuel);
        spEdit.putString(FUEL_ABR, fuelAbr);
        spEdit.putLong(CURRENT_CAR, curCar);

        return spEdit.commit();
    }

    //setters
    public boolean setCur(String currency, String abbreviation){
        String  old     = this.cur,
                oldAbr  = this.curAbr;
        this.cur    = currency;
        this.curAbr = abbreviation;
        if (reWrite())
            return true;
        this.cur    = old;
        this.curAbr = oldAbr;
        return false;
    }

    public boolean setVol(String volume, String abbreviation){
        String  old     = this.vol,
                oldAbr  = this.volAbr;
        this.vol    = volume;
        this.volAbr = abbreviation;
        if (reWrite())
            return true;
        this.vol    = old;
        this.volAbr = oldAbr;
        return false;
    }

    public boolean setFuel(String fuelType, String abbreviation){
        String  old     = this.fuel,
                oldAbr  = this.fuelAbr;
        this.fuel       = fuelType;
        this.fuelAbr    = abbreviation;
        if (reWrite())
            return true;
        this.fuel       = old;
        this.fuelAbr    = oldAbr;
        return false;
    }

    public boolean setCurCar(long id){
        long old    = this.curCar;
        this.curCar = id;
        if (reWrite())
            return true;
        this.curCar = old;
        return false;
    }

    //getters
    public String[] getCur(){
        return new String[] {cur, curAbr};
    }

    public String[] getVol() {
        return new String[] {vol, volAbr};
    }

    public String[] getFuel() {
        return new String[] {fuel, fuelAbr};
    }
}
