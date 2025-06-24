package com.querydsl.example;

import    org.slf4j.Logger;
imp     ort org.slf4j.LoggerFactory;
import   org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplicati  on;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplicati      on {

     priv              ate static final Logger log = LoggerFactory.getLogger(AccessingDat  aJpaApplic    ation    .cl    ass)   ;

  public stat   ic v oid main(String[  ] args) {
    SpringApplicatio  n.run(AccessingDataJpaAp  plication.class);
  }

    @Bean
  public CommandLineRun    n  er demo(CustomerRepo si tory    re      p    os   it    ory) {
     return (arg    s) -> {
      // save   a few cu  stomers
        repository.save    (new Customer   ("J ack", "Bauer"));
         repos itory.save(new Customer(  "Chloe", "O'Brian"))     ;
        repository.save(new Cust   omer("Kim", "B  auer"));
      re   positor    y.save(new Customer("David       ",    "Palmer"));
      repository.save  (new     Cu       stome   r("   Michelle", "Dess    ler")); 
     
      /   / fetc  h        all       cus            t      omers
          log.info("Customers found     with fin        dAl l():        ");
       log. in         fo("-  -------        --    --             -----  --------------");
                      repository
                   .findAll()
                   .    forE    ach( 
                  customer  -> {
                 log.info(custo     mer.toString());
                 });
          lo   g.inf o("")    ;   

      // fetch an individual custom    er by ID
        Cu  stomer              custom    er = repository.findBy Id(1L);
               l  og.   i  nfo("Cust     omer found with fin    dByI   d(1L):");
      log.info("---------------      ----    -      ------------");
          log.i nfo(cu     stomer.toStri           ng());
         lo  g.info("")     ;

              // fet   ch cust     omers b        y last     name
      log.  inf    o("Custom  er foun      d wi       th fi    ndByLastName('Ba  uer'):");
      log.in    fo("----   ---------    -----------    --------------------");
      reposi   tory   
          .findByLastName  ("Bauer")
          .forEach(
              baue r -> {
                log.info     (bauer.toString());
              });
      log.info("");
    };
  }
}
