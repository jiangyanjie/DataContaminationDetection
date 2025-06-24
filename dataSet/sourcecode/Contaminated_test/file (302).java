/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ArmandoPC
 */
public class DataCompiler {

    private ArrayList<ConstansToken> lista;
    private Object[][] tableTans;

    public DataCompiler() {
    }

    public DataCompiler(ArrayList<ConstansToken> lista, Object[][] tableTans) {
        this.lista = lista;
        this.tableTans = tableTans;
    }

    /**
     * @return the lista
     */
    public ArrayList< ConstansToken> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ArrayList< ConstansToken> lista) {
        this.lista = lista;
    }

    /**
     * @return the tableTans
     */
    public Object[][] getTableTans() {
        return tableTans;
    }

    /**
     * @param tableTans the tableTans to set
     */
    public void setTableTans(Object[][] tableTans) {
        this.tableTans = tableTans;
    }

    /**
     * Devulve el valor del match segun la cadena
     *
     * @param cad
     * @return Integer valor del match
     */
    public int valueofCad(String cad) {
        for (int i = 0; i < this.getLista().size() - 1; i++) {
            if (this.getLista().get(i).getIsPattern()) {
                if (cad.matches(this.getLista().get(i).getMatch())) {
                    return this.getLista().get(i).getValor();
                }
            } else {
                if (cad.equals(this.getLista().get(i).getMatch())) {
                    return this.getLista().get(i).getValor();
                }
            }

        }
        return this.getLista().get(this.getLista().size() - 1).getValor(); // si no coincide retorn ]a valor de otros
    }

    /**
     * Devulve el valor del match segun la cadena
     * @param cad
     * @return Integer valor del match
     */
    public int valueofCadEquals(String cad) {
        for (int i = 0; i < this.getLista().size() - 1; i++) {
            if (cad.equals(this.getLista().get(i).getMatch())) {
                return this.getLista().get(i).getValor();
            }
        }
        return 0; // si no coincide retorn ]a valor de otros
    }

}
