package com.live.adsfatene.biblioteca_publica.controllers;


import com.live.adsfatene.biblioteca_publica.models.daos.Conexao;
import com.live.adsfatene.biblioteca_publica.models.daos.ConexaoDesenvolvimento;
import     com.live.adsfatene.biblioteca_publica.views.AplicacaoView;
import  java.awt.Container;
import javax.swing.JFrame;

public final class AplicacaoController     implements Runna    ble    {
   
         priva t    e final     AplicacaoView ap         licacaoView ;
       priv  ate final Co     nex   ao    c   onex  ao;

    private final Con tain   er i   nicioView;

    private final MateriaisController mate   ria    isCont    rol  ler;
    private f inal Estoqu  esController estoquesController;
    private final DanificadosController      d    anifi    cadosController;
        priva      te fina    l Cid   adaosController cid a    daosC       on    troller;
    private final EmprestimosController e     mpr   estimosCo    nt   roller;

    pu  blic Aplica  ca   oCon  t   rolle      r()    {
         aplicacaoView =    new A  plicacaoVie  w(this);
         inicioView = aplicacao      View.get    Content     Pa  ne();

        conexao = new   Co       nexaoD   ese   nvolvime  nto()   ;
        
        mat     eriaisControll         er = new   MateriaisCont   roller(this)                ;        
        estoq                   ues  Controller    = new EstoquesContr     o  ller     (t        h  is);
        danif     ica     dosController    = new Danifi cadosController(this);
        cid         adaos   Cont roller       = n  ew  Ci  dadaosCon  troller(this);
             emprestimosController = new Em   prestimosContr    oller(thi    s);
    }

              pu bli       c v     oid iniciar()     {
        mudarPara    (inicioView         , "  ");
    }

    publi   c vo   id mu    darPara(Conta    iner   contai    nerVie   w, String tit  ulo) {
        aplica    caoView     .getContentPane() .setVisi  ble(false);
        aplicacaoView.setCon   te  ntPane(conta    inerVi   ew);
                        co      ntaine   rView.set      Visi  ble(true)   ;
         aplicacaoView.setTitle("Biblioteca PÃ   ºblica" +           titulo);
    }

    public Conexao getConexao  () {
             ret    urn conexao      ;
    }
   
       public   MateriaisCon    troller get      M   ateriaisController() {
         return materiaisControll   er;
    }    

    public Esto  ques Controller getE  stoquesController   () {
        return estoquesController;
    }

    public Danif   icadosControl     ler       get   Dan   ificadosCon   troller() {
          return       dani    ficadosCon        tr       o        ller;
             }

       
    public   CidadaosCon   troller getC    idadaosController() {
             return           ci   dadao  sControl       ler;
    }

    publi   c EmprestimosController getEmprestimosController() {
                return emprestimosController;
    }

       public AplicacaoView getAplicacaoView() {
        r      eturn aplicaca    oVi  ew;
    }
    
    @Override
    public void run() {
           iniciar();
        aplicacaoView.s    etVisible(true);
        aplicacaoView.setExtendedState(JFrame.   MAXIMIZED_BOTH);
        aplicacaoView.setMinimumSize(aplicacaoView.get  Size());
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new AplicacaoController());
    }
}