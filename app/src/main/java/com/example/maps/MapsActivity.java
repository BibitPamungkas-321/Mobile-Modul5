package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task= fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                    +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //add current location
        LatLng cur = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(cur)
                .title("Here I Am");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(cur));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur,15F));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options, menu);
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.Pilihan_map:
                Toast.makeText(this,"Silahkan Pilih", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.Pilihan_tempat:
                Toast.makeText(this,"Silahkan Pilih", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.spbu_1:
                LatLng spb1 = new LatLng(-7.931499, 112.602905);
                mMap.addMarker(new MarkerOptions().position(spb1).title("Marker in SPBU Tlogomas"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spb1 , 15F));
                return true;
            case R.id.spbu_2:
                LatLng spb2 = new LatLng(-7.920667, 112.595090);
                mMap.addMarker(new MarkerOptions().position(spb2).title("Marker in SPBU Universitas Muhammadiyah Malang"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spb2 , 15F));
                return true;
            case R.id.spbu_3:
                LatLng spb3 = new LatLng(-7.898625, 112.601425);
                mMap.addMarker(new MarkerOptions().position(spb3).title("Marker in SPBU Dadaprejo"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spb3 , 15F));
                return true;
            case R.id.spbu_4:
                LatLng spb4 = new LatLng(-7.956925, 112.613395);
                mMap.addMarker(new MarkerOptions().position(spb4).title("Marker in SPBU Bendungan Sutami"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spb4 , 15F));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
