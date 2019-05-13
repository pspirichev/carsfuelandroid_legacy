package newways.cars_fuel_android;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Calendar;
import java.util.List;

@Entity
public class Car
{
    @PrimaryKey
    private long            id;
    private String          name;
    private String          make;
    private short           year;
    private int             mileage = 0;
    private String          fuel;
    private String          fuelAbbr;

    @Deprecated
    Car(){}

    Car(long id, String name, String make, short year, int mileage, String fuelType, String fuelAbbr){
        this.id = id;
        setMake(make);
        setName(name);
        setMileage(mileage);
        setYear(year);
        setFuel(fuelType);
        setFuelAbbr(fuelAbbr);
    }

    //setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMake(String make){
        this.make = make;
    }

    public void setYear(short year){
        Calendar cal = Calendar.getInstance();
        if (year >= 1885 && year <= cal.get(Calendar.YEAR))
            this.year = year;
    }

    public void setMileage(int mileage){
        if (mileage >= this.mileage)
            this.mileage = mileage;
    }

    public void setFuel(String fuel){
        this.fuel = fuel;
    }

    public void setFuelAbbr(String fuelAbbr){
        this.fuelAbbr = fuelAbbr;
    }

    //getters
    public long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getName() {
        return name;
    }

    public short getYear(){
        return year;
    }

    public int getMileage(){
        return mileage;
    }

    public String getFuel() {
        return fuel;
    }

    public String getFuelAbbr() {
        return fuelAbbr;
    }

    //refills
    public Refill       getLastRefill(Context context){
        RefillDb refillDb = Room.databaseBuilder(context, RefillDb.class, Long.toString(id)).build();
        List<Refill> allRefills = refillDb.refillDao().getAll();
        return allRefills.get(allRefills.size() - 1);
    }

    public Refill       getLastRefill(RefillDb refillDb){
        List<Refill> allRefills = refillDb.refillDao().getAll();
        return allRefills.get(allRefills.size() - 1);
    }

    public Refill       getRefillById(Context context, int id){
        RefillDb refillDb = Room.databaseBuilder(context, RefillDb.class, Long.toString(id)).build();
        return refillDb.refillDao().getById(id);
    }

    public Refill       getRefillById(RefillDb refillDb, int id){
        return refillDb.refillDao().getById(id);
    }

    public List<Refill> getAllRefills(Context context){
        RefillDb refillDb = Room.databaseBuilder(context, RefillDb.class, Long.toString(id)).build();
        return refillDb.refillDao().getAll();
    }

    public List<Refill> getAllRefills(RefillDb refillDb){
        return refillDb.refillDao().getAll();
    }


    public void         addRefill(Context context, Refill refill){
        RefillDb refillDb = Room.databaseBuilder(context, RefillDb.class, Long.toString(id)).build();
        refillDb.refillDao().insert(refill);
    }

    public void         addRefill(RefillDb refillDb, Refill refill){
        refillDb.refillDao().insert(refill);
    }
}
