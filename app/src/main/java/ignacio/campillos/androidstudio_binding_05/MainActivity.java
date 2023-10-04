package ignacio.campillos.androidstudio_binding_05;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import ignacio.campillos.androidstudio_binding_05.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio_binding_05.modelos.Alumno;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher; //Sirve para lanzar una actividad y recibir informacion en esta actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());   //Bind XML with the interface, so we can access the interface properties with binding.<property>

        setSupportActionBar(binding.toolbar);

        inicializarLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir la actividad add alumno
                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class)); //Abrir desde esta actividad, otra actividad para recibir informacion <Intent>

            }
        });
    }

    private void inicializarLauncher() {
        addAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),   //Iniciar la actividad y esperar un result (RESULT_OK / RESULT_CANCELED)
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null && result.getData().getExtras()!= null){ //Comprobar que tiene datos en el bundle
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO"); //Crear alumno con los extras (datos) del bundle,
                                // utilizando serializable para pasarle todos los datos al objeto, y la key que pusimos antes

                                Toast.makeText(MainActivity.this, alumno.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}