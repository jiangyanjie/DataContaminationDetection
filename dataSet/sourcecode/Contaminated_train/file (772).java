/*
 * Copyright (C) 2014 Sérgio Ramos da Silva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.edu.utfpr.cm.tsi.openbibliotek.entidades;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Sérgio Ramos da Silva
 */
public class Conta implements Serializable {

    private int id;
    private Pessoa pessoa;
    private Nivel nivel;
    private String matricula;
    private char[] senha;
    private boolean bloqueado;

    public Conta() {
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(char[] senha) {
        this.senha = senha;
    }

    /**
     * @param bloqueado the bloqueado to set
     */
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @return the nivel
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @return the senha
     */
    public char[] getSenha() {
        return senha;
    }

    /**
     * @return the bloqueado
     */
    public boolean isBloqueado() {
        return bloqueado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.pessoa);
        hash = 47 * hash + Objects.hashCode(this.nivel);
        hash = 47 * hash + Objects.hashCode(this.matricula);
        hash = 47 * hash + Arrays.hashCode(this.senha);
        hash = 47 * hash + (this.bloqueado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conta other = (Conta) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        if (!Objects.equals(this.nivel, other.nivel)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Arrays.equals(this.senha, other.senha)) {
            return false;
        }
        return this.bloqueado == other.bloqueado;
    }

    @Override
    public String toString() {
        return this.getMatricula();
    }

}
