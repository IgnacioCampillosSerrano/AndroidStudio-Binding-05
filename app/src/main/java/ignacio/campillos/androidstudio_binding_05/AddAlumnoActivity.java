package ignacio.campillos.androidstudio_binding_05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import ignacio.campillos.androidstudio_binding_05.databinding.ActivityAddAlumnoBinding;
import ignacio.campillos.androidstudio_binding_05.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio_binding_05.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;//Usar el que este relacionado con la actividad a acceder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_add_alumno); -- Reemplazada por el binding

        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater()); //Lee el XML de la actividad vinculada, y carga los componentes en el binding para acceder a ellos
        setContentView(binding.getRoot());

        binding.buttonCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED); //El resultado de la actividad es cancelado
                finish();
            }
        });

        binding.buttonCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recoger informacion para crear alumno

                Alumno alumno = crearAlumno();

                if (alumno !=null){
                    // Hacer el intent
                    Intent intent = new Intent();

                    // Poner el bundle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO",alumno); //Para convertir la informacion de un objeto en datos capaces de ser pasados a otra interfaz
                    intent.putExtras(bundle);

                    // Terminar
                    setResult(RESULT_OK,intent); //Avisar de que a acabado bien y pasar el intent con los datos
                    finish();

                }else{
                    Toast.makeText(AddAlumnoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Alumno crearAlumno() {
        if(binding.editTextNombreAddAlumno.getText().toString().isEmpty()){
            return null;
        }
        if(binding.editTextApellidosAddAlumno.getText().toString().isEmpty()){
            return null;
        }
        if(binding.spinnerCiclosAddAlumno.getSelectedItemPosition()==0){
            return null;
        }
        if(!binding.radioButtonGrupoAAddAlumno.isChecked()
                && !binding.radioButtonGrupoBAddAlumno.isChecked()
                && !binding.radioButtonGrupoCAddAlumno.isChecked()){
            return null;
        }

        RadioButton rb = findViewById(binding.radioGroupAddAlumnos.getCheckedRadioButtonId());
        char grupo = (rb.getText().toString().split(" ")[1].charAt(0)); //Convierte el String "GRUPO X" a un array, y luego coge la posicion 1 (X) y lo convierte a un character

        Alumno alumno = new Alumno(
                binding.editTextNombreAddAlumno.getText().toString(),
                binding.editTextApellidosAddAlumno.getText().toString(),
                binding.spinnerCiclosAddAlumno.getSelectedItem().toString(),
                grupo
        );
        return alumno;
    }
}