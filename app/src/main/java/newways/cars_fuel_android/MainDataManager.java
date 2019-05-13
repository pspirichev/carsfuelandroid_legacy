package newways.cars_fuel_android;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Date;
import java.util.List;

public class MainDataManager
{
    private CarDb carDb;
    private Context context;

    MainDataManager(Context context){
        carDb = Room.databaseBuilder(context, CarDb.class, "car_database").build();
        this.context = context;
    }

    //getters
    public List<Car>    getAllCars(){
        return carDb.carDao().getAll();
    }

    public String       getLastRefill(long carId, Context context, String volUnit, String costUnit){
        Car curCar          = carDb.carDao().getById(carId);
        Refill lastRefill   = curCar.getLastRefill(context);

        String date         = lastRefill.getDateAsDate().toString();
        String volume       = Short.toString(lastRefill.getVolume());
        String cost         = Short.toString(lastRefill.getCost());

        return date + "\n" + volume + volUnit + cost + costUnit;
    }

    public String       getAllMoneySpent(long carId, Context context, String costUnit){
        List<Refill> refillList = carDb.carDao().getById(carId).getAllRefills(context);
        long costSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            costSumm += refillList.get(i).getCost();
        return Long.toString(costSumm) + costUnit;
    }

    public String       getAverageRefillBill(long carId, Context context, String costUnit){
        List<Refill> refillList = carDb.carDao().getById(carId).getAllRefills(context);
        long costSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            costSumm += refillList.get(i).getCost();
        costSumm /= refillList.size();
        return Long.toString(costSumm) + costUnit;
    }

    public String       getAllFuelConsumed(long carId, Context context, String volUnit){
        List<Refill> refillList = carDb.carDao().getById(carId).getAllRefills(context);
        long volSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            volSumm += refillList.get(i).getVolume();
        return Long.toString(volSumm) + volUnit;
    }

    public String       getAverageFuelCost(long carId, Context context, String costUnit, String volUnit){
        List<Refill> refillList = carDb.carDao().getById(carId).getAllRefills(context);

        long costSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            costSumm += refillList.get(i).getCost();

        long volSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            volSumm += refillList.get(i).getVolume();

        return Long.toString(costSumm / volSumm) + costUnit + "/" + volUnit;
    }

    public String       getAverageEconomy(long carId, Context context, String volUnit, String distUnit, int curMileage) {
        Car curCar              = carDb.carDao().getById(carId);
        int startMileage        = curCar.getMileage();
        List<Refill> refillList = curCar.getAllRefills(context);
        long volSumm = 0;
        for (int i = 0; i < refillList.size(); i++)
            volSumm += refillList.get(i).getVolume();
        return Long.toString(volSumm / (curMileage - startMileage)) + volUnit + "/" + distUnit;
    }

    //adders
    public void         addRefill(long carId, Context context, long date, short volume, short cost){
        Car curCar = carDb.carDao().getById(carId);
        curCar.addRefill(context, new Refill(curCar.getAllRefills(context).size(), new Date(date), curCar, volume, cost));
    }

    public void         addCar(String make, String name, short year, int mileage, String fuelType){
        String[] fuelTypes  = context.getResources().getStringArray(R.array.fuel_types);
        String[] fuelAbbrs  = context.getResources().getStringArray(R.array.fuel_abbr);
        String   fuelAbbr   = "";

        for (int i = 0; i < fuelTypes.length; i++)
            if (fuelTypes[i].equals(fuelType))
                fuelAbbr = fuelAbbrs[i];

        carDb.carDao().insert(new Car(carDb.carDao().getAll().size(), name, make, year, mileage, fuelType, fuelAbbr));
    }

    //deleter
    public void         deleteCar(long carId){
        carDb.carDao().delete(carDb.carDao().getById(carId));
    }
}
