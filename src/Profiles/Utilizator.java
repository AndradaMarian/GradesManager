package Profiles;

import Domain.HasID;
import Domain.TipUtilizator;

import java.util.Objects;

public  class Utilizator implements HasID<String> {
    public String nume;
    private String parola;
    public String nrMatricol=null;
    TipUtilizator tipUtilizator;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNrMatricolBaza() {
        if(nrMatricol==null)
            return null;
        if(nrMatricol.equals(""))
        return null;
        else
            return nrMatricol;
    }
    public String getNrMatricol() {
        if(nrMatricol==null)
            return nrMatricol;
        else
            return "";
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public TipUtilizator getTipUtilizator() {
        return tipUtilizator;
    }

    public void setTipUtilizator(TipUtilizator tipUtilizator) {
        this.tipUtilizator = tipUtilizator;
    }

    public Utilizator(String nume, String parola, String nrMatricol, TipUtilizator tipUtilizator) {
        this.nume = nume;
        this.parola = parola;
        this.nrMatricol=nrMatricol;
        this.tipUtilizator = tipUtilizator;
    }

    public Utilizator(String nume, String parola, TipUtilizator tipUtilizator) {
        this.nume = nume;
        this.parola = parola;
        this.tipUtilizator = tipUtilizator;
        nrMatricol=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, tipUtilizator);
    }

    @Override
    public String getID() {
        return nume;
    }
    @Override
    public void setID(String s) {
        nume=s;
    }
}
