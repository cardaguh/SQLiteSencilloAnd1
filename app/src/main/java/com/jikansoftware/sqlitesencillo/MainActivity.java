package com.jikansoftware.sqlitesencillo;

        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1, et2;

    private Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et_dni);
        et2 = findViewById(R.id.et_nombre);

        b1 = (Button)findViewById(R.id.button_crear);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });

        b2 = (Button)findViewById(R.id.button_eliminar);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baja();
            }
        });

        b3 = (Button)findViewById(R.id.button_consultar);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta();
            }
        });

        b4 = (Button)findViewById(R.id.button_modificar);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modicar();
            }
        });

    }

    public void crear(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et1.getText().toString();
        String nombre = et2.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("dni", dni);
        registro.put("nombre", nombre);
        long rowInserted = bd.insert("votantes", null, registro);
        et1.setText("");
        et2.setText("");
        if(rowInserted != -1)
            Toast.makeText(this, "Usuario añadido exitosamente: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Se presentó un error al ingresar el usuario", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "Se guardó el usuario", Toast.LENGTH_SHORT).show();
    }

    public void consulta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et1.getText().toString();
        Cursor fila = bd.rawQuery("SELECT nombre FROM votantes WHERE dni=" + dni, null);
        Log.i(fila.toString(),"info");
        if(fila.moveToFirst()){
            //et1.setText(fila.getString(0));
            et2.setText(fila.getString(0));
        }else{
            Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
        }
        bd.close();

    }

    public void baja(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et1.getText().toString();

        int cant = bd.delete("votantes", "dni="+dni, null);
        bd.close();
        et1.setText("");
        et2.setText("");

        if(cant == 1){
            Toast.makeText(this, "Se eliminó el usuario exitosamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe ese usuario con ese DNI", Toast.LENGTH_SHORT).show();
        }
    }

    public void modicar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et1.getText().toString();
        String nombre = et2.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("dni", dni);
        registro.put("nombre", nombre);

        int cant = bd.update("votantes", registro, "dni=" + dni, null);
        bd.close();

        if(cant == 1){
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe ese usuario con ese DNI", Toast.LENGTH_SHORT).show();
        }
    }


}


    //int cant = bd.delete("votantes", "dni" +dni, null);