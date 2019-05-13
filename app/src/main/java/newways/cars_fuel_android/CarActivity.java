package newways.cars_fuel_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    MainDataManager mdm;
    Button          refill_btn;
    DrawerLayout    drawer;
    ImageButton     btn_drawer;
    NavigationView  navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        mdm = new MainDataManager(this);

        initGUI();

        new initCars().execute();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_btn_settings){
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
        }
        if (id == R.id.drawer_btn_add_car) {
            new AddCar().addCar(this);
            new initCars().execute();
        }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initGUI(){
        refill_btn = (Button) findViewById(R.id.btn_refill);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        btn_drawer = (ImageButton) findViewById(R.id.btn_drawer);
        btn_drawer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class initCars extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {

            final SubMenu drawerMenu  = navigationView.getMenu().findItem(R.id.you_cars_group).getSubMenu();
            final List<Car> allCars = mdm.getAllCars();
            final int[] viewIDs = new int[allCars.size()];
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    drawerMenu.clear();
                }
            });

            String curCarTitle[] = new String[allCars.size()];

            for(int i = 0; i < allCars.size(); i++) {
                viewIDs[i] = View.generateViewId();
                Car curCar = allCars.get(i);
                curCarTitle[i] = curCar.getMake() + " " + curCar.getName() + " " + curCar.getFuelAbbr();
            }

            final String curCarTitleFin[] = curCarTitle;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < allCars.size(); i++)
                        drawerMenu.add(R.id.drawer_cars_menu, viewIDs[i], Menu.NONE, curCarTitleFin[i]);

                    drawerMenu.add(R.id.drawer_cars_menu, R.id.drawer_btn_add_car, Menu.NONE, R.string.add_car);
                    drawerMenu.findItem(R.id.drawer_btn_add_car).setIcon(R.mipmap.baseline_add_black_18dp);
                }
            });


            return null;
        }
    }

    class AddCar{

        private Spinner     spinMake;
        private EditText    tinputModel;
        private EditText    tinputYear;
        private Spinner     spinFuel;
        private EditText    tinputMileage;

        private String      make,
                name,
                fuelType;
        private short       year;
        private int         mileage;

        public void addCar(Context context){
            AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
            builder .setView(R.layout.dialog_add_car)
                    .setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener(){
                        public void onClick(DialogInterface di, int id) {
                            di.cancel();
                        }
                    })
                    .setPositiveButton(R.string.ok, new CarAddDialog());
            AlertDialog ad = builder.create();
            ad.show();

            spinMake        = ad.findViewById(R.id.add_spinner_make);
            tinputModel     = ad.findViewById(R.id.add_tinput_model);
            tinputYear      = ad.findViewById(R.id.add_tinput_year);
            spinFuel        = ad.findViewById(R.id.add_spinner_fuel_type);
            tinputMileage   = ad.findViewById(R.id.add_tinput_mileage);

            int fuelTypesArrayID = getResources().getIdentifier(new CFASettingsManager(context).getFuel()[1], "array", getPackageName());

            spinFuel.setAdapter(ArrayAdapter.createFromResource(getApplicationContext(), fuelTypesArrayID, android.R.layout.simple_spinner_item));
            spinMake.setOnItemSelectedListener(new CarMakeSpinnerListener());
            spinFuel.setOnItemSelectedListener(new CarFuelSpinnerListener());
        }

        class CarAddDialog implements android.app.AlertDialog.OnClickListener
        {
            @Override
            public void onClick(DialogInterface di, int id){
                try {
                    name    = tinputModel.getText().toString();
                    year    = Short.parseShort(tinputYear.getText().toString());
                    mileage = Integer.parseInt(tinputMileage.getText().toString());
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), R.string.incorrect_data, Toast.LENGTH_LONG).show();
                }

                if (make != null && name != null && year > 1885 && year < Calendar.getInstance().get(Calendar.YEAR) && mileage >= 0) {
                     AsyncTask.execute(new Runnable() {
                         @Override
                         public void run() {
                             mdm.addCar(make, name, year, mileage, fuelType);
                         }
                     });
                } else {
                    Toast.makeText(getApplicationContext(), R.string.incorrect_data, Toast.LENGTH_LONG).show();
                }
                new initCars().execute();
            }
        }

        class CarMakeSpinnerListener implements AdapterView.OnItemSelectedListener
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        }

        class CarFuelSpinnerListener implements AdapterView.OnItemSelectedListener
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fuelType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        }
    }
}
