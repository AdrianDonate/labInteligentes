public class Sucesor {
    private String accion;
    private Casilla estado;
    private int costo;
    public Sucesor(String accion, Casilla estado, int costo){
        this.accion = accion;
        this.estado = estado;
        this.costo = costo;
    }

    public Casilla getEstado() {
        return estado;
    }

    public void setEstado(Casilla estado) {
        this.estado = estado;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
