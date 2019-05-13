package newways.cars_fuel_android;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Ref;
import java.util.Date;

@Entity
public class Refill
{
    @PrimaryKey
    private long    id;
    private long    date;
    private short   volume;
    private short   cost;

    @Deprecated
    Refill(){}

    Refill(long id, Date date, Car curCar, short volume, short cost) {
        setId(id);
        setDateAsDate(date, curCar);
        setVolume(volume);
        setCost(cost);
    }

    //setters
    public void setId(long id) {
        this.id = id;
    }

    @Deprecated
    public void setDate(long date) {
        this.date = date;
    }

    public void setDateAsDate(Date date, Car curCar){
        Date curDate = new Date();
        Date carDate = new Date(curCar.getYear(), 1, 1);
        if (date.before(curDate) && date.after(carDate))
            this.date = date.getTime();
    }

    public void setDateAsDate(long date, Car curCar){
        setDateAsDate(new Date(date), curCar);
    }

    public void setVolume(short volume){
        if (volume > 0)
            this.volume = volume;
    }

    public void setCost(short cost)
    {
        if(cost >= 0)
            this.cost = cost;
    }

    //getters
    public long getId() {
        return id;
    }

    public Date getDateAsDate() {
        return new Date(this.date);
    }

    public long getDate(){
        return this.date;
    }

    public short getVolume() {
        return volume;
    }

    public short getCost() {
        return cost;
    }
}