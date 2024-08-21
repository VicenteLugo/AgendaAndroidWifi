package edu.utez.agendawifi1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.utez.utilerias.ConexionServidor;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CuatroActivity extends ActionBarActivity implements OnItemSelectedListener{
	ImageView imagenContacto;	
	ImageView imagenFoto;
	File archivo;
	String rutaBD;
	Uri uri=null;
	protected int position;
    protected String selection;
    protected ArrayAdapter<CharSequence> adapter;
    protected ArrayAdapter<CharSequence> adapter1;
    public static final int DEFAULT_POSITION = 1;  
   // List<ContactoBean> lisTel=new ArrayList <ContactoBean>();
   // List<ContactoBean> lisEml=new ArrayList <ContactoBean>();
   // ContactoBean telB=new ContactoBean();
   // ContactoBean emlB=new ContactoBean();
    String tele="";
    String emla="";
    
    EditText  nombre;
	EditText apellidos ;
	EditText sexo;
	EditText fechaNac ;
	EditText telefono;
	EditText email;
	ImageView imagen;	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cuatro);
		nombre = (EditText)findViewById(R.id.editText1);
		apellidos = (EditText)findViewById(R.id.editText2);
		sexo = (EditText)findViewById(R.id.editText3);
		fechaNac = (EditText)findViewById(R.id.editText4);
		telefono = (EditText)findViewById(R.id.editText5);	
		email = (EditText)findViewById(R.id.editText6);
		imagen= (ImageView)findViewById(R.id.imageView1);
		
		String n = getIntent().getExtras().getString("nombre");
		String ap = getIntent().getExtras().getString("apellidos");
		String sx = getIntent().getExtras().getString("correo");
		String fc = getIntent().getExtras().getString("fechaNacimiento");
		String tel = getIntent().getExtras().getString("telefono");	
		String em=getIntent().getExtras().getString("email");
		String img = getIntent().getExtras().getString("imagen");
		
		nombre.setText(n);
		apellidos.setText(ap);
		sexo.setText(sx);
		fechaNac.setText(fc);
		telefono.setText(tel);
		email.setText(em);		
		if(img==null){			
		}else{
			InputStream in_stream;
			try {
				in_stream = getContentResolver().openInputStream(
						Uri.parse(img));
				Bitmap btp_img = BitmapFactory.decodeStream(in_stream);
	            Bitmap resized = Bitmap.createScaledBitmap(btp_img,(int)
	            		(btp_img.getWidth()*0.2), (int)(btp_img.getHeight()*0.2), true);
	            imagen.setImageBitmap(resized);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
            
		}

				
	}
	
	public void actualizar(View v){
		EditText nombreE = (EditText) findViewById(R.id.editText1);
    	EditText apellidosE = (EditText) findViewById(R.id.editText2);
    	EditText emailE = (EditText) findViewById(R.id.editText3);
    	EditText telefonoE = (EditText) findViewById(R.id.editText4);
    	EditText fech = (EditText) findViewById(R.id.editText5);    	
    	    			
				String nombre = nombreE.getText().toString();
		    	String apellidos = apellidosE.getText().toString();
		    	String correo = emailE.getText().toString();
		    	String telefono = telefonoE.getText().toString();		    			    	
		    	String fecha = fech.getText().toString();
		    	int id=getIntent().getExtras().getInt("id");
		    	
		    	ConexionServidor con = new 	ConexionServidor();
				List<NameValuePair> valores = new ArrayList();
				
				valores.add(new BasicNameValuePair("id", id+""));
				valores.add(new BasicNameValuePair("nombre", nombre));
				valores.add(new BasicNameValuePair("apellidos", apellidos));
				valores.add(new BasicNameValuePair("correo", correo));
				valores.add(new BasicNameValuePair("telefono", telefono));				
				valores.add(new BasicNameValuePair("fechaNacimiento", fecha));
				valores.add(new BasicNameValuePair("imagen", uri+""));
				
				
				String mensaje =con.conexionServlet(
						"http://192.168.1.75:8084/AgendaWifiWeb/ServletActualizar",valores);
								
				Toast.makeText(this, "Contacto Actualizado", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this,MainActivity.class));
				finish();
				
						
	}

	public void tomarFoto(View v){    	
    	Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
    	archivo = new File(Environment.getExternalStorageDirectory(),"foto_" +        
                String.valueOf(System.currentTimeMillis()) + ".png");                
    	uri= Uri.fromFile(archivo);
    	intento.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    	startActivityForResult(intento, 12345);
	}

    public void seleccionarImagen(View v){
		Intent intento = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);	 
		startActivityForResult(intento, 1);
	}
 
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data){	    	
	    	if(requestCode == 123456 && resultCode == RESULT_OK){
	    		imagenFoto.setImageURI(uri);
	    	}	    	
	    	if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            uri=selectedImage;	            
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };	 
	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();	             
	            imagenFoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));	         
	        }	    		    	
	    }
       
	 public void crear(View v){
			ciclo();		
		}
		 int cont=1;
		 int left=150;	 
		 
		 
		@SuppressLint("NewApi") public void ciclo(){		
			LinearLayout p=(LinearLayout)findViewById(R.id.segundo);
			//TableRow row=new TableRow(this);
			Spinner sp=new Spinner(this);
			sp.getAdapter();
			
			
			EditText edt=new EditText(this);
			edt.setId(cont);
			edt.setHintTextColor(Color.WHITE);
			edt.setHint("Telefono "+cont);
			edt.setTextColor(Color.WHITE);
			edt.setInputType(InputType.TYPE_CLASS_PHONE);
			
			//telB.setTelefono(edt.getText().toString()+"");
			if(selection.equals("Casa") || selection.equals("Oficina") || selection.equals("movil")){
			//	telB.setTelefonoTipo(selection+"");
				Toast.makeText(this,"Selección actual: "+selection,Toast.LENGTH_SHORT).show();
			}
			tele=tele+edt.getText().toString()+selection;
			//lisTel.add(telB);
			
			final ImageButton act=new ImageButton(this);
			act.setImageResource(android.R.drawable.ic_input_add);
			act.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					cont++;
					//left=left+50;
					ciclo();
									
				}
			});
			//act.setY(left);
			//act.setX(10);
			//edt.setY(left);
			//edt.setX(50);
			p.addView(sp);
			p.addView(act);
			p.addView(edt);
			
		}
		
		public void crear1(View v){
			ciclo1();		
		}
		 int cont1=1;
		 int left1=200;	 
		 
		 
		@SuppressLint("NewApi") public void ciclo1(){		
			LinearLayout p=(LinearLayout)findViewById(R.id.segundo);
			//TableRow row=new TableRow(this);
			EditText eml=new EditText(this);
			eml.setId(cont);
			eml.setHintTextColor(Color.WHITE);
			eml.setHint("Email "+cont);
			eml.setTextColor(Color.WHITE);
			eml.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			
		//	emlB.setEmail(eml.getText().toString()+"");
			if(selection.equals("Personal") || selection.equals("Profesional") || selection.equals("Organizacional")){
		//		emlB.setEmailTipo(selection+"");
				Toast.makeText(this,"Selección actual: "+selection,Toast.LENGTH_SHORT).show();
			}
			emla=emla+eml.getText().toString()+selection;
		//	lisEml.add(emlB);
			
			final ImageButton act=new ImageButton(this);
			act.setImageResource(android.R.drawable.ic_input_add);
			act.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					cont++;
					left1=left1+50;
					ciclo1();
									
				}
			});
			//act.setY(left1);
			//act.setX(10);
			//eml.setY(left1);
			//eml.setX(60);
			p.addView(act);
			p.addView(eml);
			
		}
    
		@Override
	    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	        this.position = position;
	        selection = parent.getItemAtPosition(position).toString();        
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> parent) {}

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cuatro, menu);
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
}
