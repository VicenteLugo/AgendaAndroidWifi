package edu.utez.agendawifi1;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.utez.bean.ContactoBean;
import edu.utez.utilerias.ConexionServidor;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements TextWatcher {
	TextView txt;
	AutoCompleteTextView filtro;
	List<String> ls = new ArrayList<String>();
	List<ContactoBean> lista;
	private ListView lisB; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lisB = (ListView)findViewById(R.id.listView);        
        
        filtro = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		filtro.addTextChangedListener(this);

		filtro.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, ls));
		
			
				//String[] contactoss = new String[lista.size()];			
	     //   for(int i= 0 ; i < lista.size(); i++){	        	
	       // 	String nombre = lista.get(i).getNombre();
	        //	ls.add(lista.get(i).getNombre());
	          //  String apellidos = lista.get(i).getApellidos();
	          //  ls.add(lista.get(i).getApellidos());
	          //  String sexo = lista.get(i).getSexo();  
	          //  ls.add(lista.get(i).getSexo());
	          //  String telefono = lista.get(i).getTelefono();
	          //  ls.add(lista.get(i).getTelefono());
	        	//String info = (nombre+" "+apellidos+" "+sexo+"\nTel: "+telefono);
	        	//contactoss[i]=info;        	
	        	
	       // }        
	        
		
		filtro.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub				
				Intent intento = new Intent(filtro.getContext(),SecondActivity.class);
				
				intento.putExtra("nombre", filtro.getText().toString());						
				//Intent intento =new Intent(act.getContext(), CuatroActivity.class);
				//intento.putExtra("id", idContacto);
				//startActivity(intento);
				//finish();
				startActivity(intento);
			}

		});
        
        try {
			enviarJSON();	        
			if(lista.size()==0){
				Toast.makeText(this, "Sin contactos", Toast.LENGTH_SHORT).show();
			}else{				
				String[] contactos = new String[lista.size()];			
	        for(int i= 0 ; i < lista.size(); i++){	        	
	        	String nombre = lista.get(i).getNombre();
	            String apellidos = lista.get(i).getApellidos();	            
	            String sexo = lista.get(i).getSexo();        
	            String telefono = lista.get(i).getTelefono();
	        	String info = (nombre+" "+apellidos+" "+sexo+"\nTel: "+telefono);
	        	contactos[i]=info;        	
	        	
	        }
	        
	        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactos);
	        lisB.setAdapter(adaptador);
	        lisB.setClickable(true);
	        
	        lisB.setOnItemClickListener(new OnItemClickListener(){
       		 
        	    public void onItemClick(AdapterView <?> arg0, View arg1, int position, long id) {

        	        Intent intento = new Intent(lisB.getContext(),SecondActivity.class);
     
        			intento.putExtra("nombre", lista.get(position).getNombre());
        			intento.putExtra("apellidos", lista.get(position).getApellidos());
        			intento.putExtra("correo", lista.get(position).getEmail());
        			intento.putExtra("fechaNac", lista.get(position).getFecha());
        			intento.putExtra("sexo", lista.get(position).getSexo());
        			intento.putExtra("telefono", lista.get(position).getTelefono());
        			intento.putExtra("imagen", lista.get(position).getImagen());        			
			    	startActivity(intento);
        	    }        	 
        	}); 	        
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

   
    public void enviarJSON() throws JSONException{                                            
		ConexionServidor con = new ConexionServidor();		
		List<NameValuePair> valores = new ArrayList<NameValuePair>();		
	    JSONObject objeto = con.conexionServletJSON("http://192.168.1.75:8084/AgendaWifiWeb/ServletConsultar", valores);  	    
	    String lista = objeto.getString("lista"); 	    
	    Gson gson=new Gson();	    
	    Type listType = new TypeToken<List<ContactoBean>>() {}.getType();
	    List<ContactoBean> yourList = gson.fromJson(lista, listType);
	    this.lista=yourList; 
	}

	
	
public void agregar(View v){
	startActivity(new Intent(this, ThirdActivity.class));
}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onTextChanged(CharSequence s, int start, int before, int count) {
		txt.setText(filtro.getText());		
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void afterTextChanged(Editable s) {

	}
}
