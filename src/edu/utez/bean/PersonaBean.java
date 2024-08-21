package edu.utez.bean;

public class PersonaBean {
	 private int idPersona;
	    private String nombre;
	    private String apellidos;
	    private String sexo;
	    private String fechaNac;    
	    private String email;    
	    private String telefono;   
	    private String imagen;
//	    public PersonaBean(String nombre, String apellidos, String sexo, String fechaNac, String email, int telefono){
//	        this.nombre=nombre;
//	        this.apellidos=apellidos;
//	        this.sexo=sexo;
//	        this.fechaNac=fechaNac;
//	        this.email=email;
//	        this.telefono=telefono;
//	    }
	    
	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getApellidos() {
	        return apellidos;
	    }

	    public void setApellidos(String apellidos) {
	        this.apellidos = apellidos;
	    }

	    public String getSexo() {
	        return sexo;
	    }

	    public void setSexo(String sexo) {
	        this.sexo = sexo;
	    }

	    public String getFechaNac() {
	        return fechaNac;
	    }

	    public void setFechaNac(String fechaNac) {
	        this.fechaNac = fechaNac;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getTelefono() {
	        return telefono;
	    }

	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
	    }

	    public int getIdPersona() {
	        return idPersona;
	    }

	    public void setIdPersona(int idPersona) {
	        this.idPersona = idPersona;
	    }

	    public String getImagen() {
	        return imagen;
	    }

	    public void setImagen(String imagen) {
	        this.imagen = imagen;
	    }
}
