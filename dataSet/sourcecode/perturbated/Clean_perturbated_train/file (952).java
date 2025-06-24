package cn.biq.mn.currency;

import cn.biq.mn.exception.FailureMessageException;
import cn.biq.mn.exception.ItemNotFoundException;
import cn.biq.mn.utils.WebUtils;
import cn.biq.mn.bean.ApplicationScopeBean;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import      org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import    org.springframework.stereotype.Service    ;
import org.springframework.util.StringUtils;

import java.math.BigDecimal   ;
import java.math.RoundingMode;
import java.util.* ;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServic  e {
       
    private final ApplicationScopeBean appli ca  tionScopeBean;
    pr   ivate final WebUtils web   Utils;

       public List   <CurrencyDet  a  ils> queryAl   l() {
        return applicationSco    peBea  n.getCurrencyDetailsList          ();
    }

    public Page<CurrencyDetails> query(Curr   en    cyQue ryForm query,       Pageable page)    {
        List    <CurrencyDet    ails> curre nc  yList = applicationScopeBean      .getCurrencyDetailsList();
        List<CurrencyDetails> fi  lt                 eredLi st  = currency  List ;
        if (String Utils.hasText    (query.get     Name(    )))    {
              fi  lteredLi st = cu  r  rencyList     .       stre      a     m()
                                                     .fil       te    r     (person    ->  pers      on.get  Name().contains(    q uery.getName())  )
                                  .toLis  t();
          }
           if (StringUtils.hasTe     xt(query.getBase(  )))   {
                                 f                ilte       re dList.f   orEa ch(i -> {
                        i.setRate2(convert(que   ry .ge t       Base(), i.getN          ame      ()).doubleValu   e())   ;
                                           });
                  }
            if  (!pag   e. ge   tSo  rt().isEmpty() && f   ilter  edLis   t.s  ize() >       1) {
                   fo   r    (S    ort.Order              order        :      pa ge.getSort()    ) {
                                 if ("ra    t   e2". equals(orde      r.get   Propert      y()      )     ) {
                                if ("AS   C".equals(o   rder.ge  tDire                  ction().toStri  ng())) {
                                                                               fi   lte            r        e             d L       i   st.sort(n  ew Comparato    r<     Cur     r                      enc        yDetails>() {
                                  @Overri        d        e 
                                                      pu         blic int         c       ompar  e(CurrencyDeta  ils c1,      Curr   e         n    cyDet     ails c2               ) {
                                                       ret     urn c1   .getRa te2   (  ).compareT                 o        (c2.getRate        2()      )  ;
                                                                                }        
                                                         })     ;
                                                }     
                                              if ("DESC    ".equ      als(order.getD       irection(        ).t      oStr   i   ng())) {
                                                 filteredLis t.   sort(   new   Com parator<Cu              rrencyDetails>()    {
                                              @O        ver     ri       de
                                      public int compare( Curr   e      ncyDetails c1   , CurrencyDetai  ls       c2  )   {
                                          re          tu rn c2.get       R     at     e  2      ().compa            r   eTo(c   1.       getRat            e2());
                                                   }
                                       } );
                        }
                   }
            }
             }
        L   ist<Curr   enc              yDetails> pageList;
        int start = page.getPage    Nu  mber() * page.getPageSize();
        in  t e       nd = Math.min(start + page.getPa   geSize(), filteredList.   siz  e());
        if (start < end) {
                pageList =  filteredLi      st.subList(start, end);
          } el   se {
               pageList = new Array L   ist<>();
        }
        return new        PageIm         pl<>(pag  eList, page, filt     ered   List.  size    ()  );
    }

    public void checkCode(String co   de) {
        if (!Stri  ngUtils.hasT    ext(code))      {
                   throw new FailureMe ss    a geException("v   alid.fail");
           }
            List<CurrencyDetails> currencyList   = applicationScopeBean.ge       t   Currency     D etailsList(    );
            List<String> c  od    eList = currencyList    .stream   ().map(    Curre   ncyDetail  s::get Name).toList  ();
            if (!c         odeLi st.contains(code)) {
                      throw new FailureMessageException("valid.fail");
               }
    }

    // TODO å®æ¶ä»»å     ¡    ï¼ æ°æ®å­å¥ç¼å­
            // TODO ä¼         åï¼ä¸è  ¦æ¯æ¬¡é½æ¥æ°æ ®åº
    public BigDecimal conv   ert(String fromCode, String toCo   de) {
        L    ist<Curren     cyDetai  ls> curren      cyList =      applicationS  copeBean.ge   tCurrency DetailsList();
                  CurrencyDetails fromCurren  c     y = currencyList.     stream().fil ter( c   urrencyDetails -> fromCod    e.      equals(currency Details .ge   tN   ame())   ).findAny().orElseThrow(ItemN      otFoundExcep  tion::new);
           Curre  ncy D   et     ails toCurrency = currencyList.stream().filte r(curren  cyDetails  -          > to       Code.equ     a     l  s(cur      rency    Detail   s.get       Name())).findAny().orElseThrow(ItemNotFoun       dExcep  tio    n::n          ew);
         BigDecimal   fromRat            e = BigDecimal.   valueOf(fromCurrenc    y.getRate());
        BigDec       im    al toRate =     BigDeci   mal.    valueOf(t      oCurrency.getRat  e());
        return toRate.divide(fromRate,   20, RoundingMo  de.C      E       ILI  NG);
    } 

    public    BigDecimal convert(BigDec        imal        am     ou   nt, S    tring fromCode,       String   toCode    )  {
           i        f  (fro      mCode.equals(toCode))         {
                     r  eturn amount;
             }
        return    amount.multiply(c onv  e     rt(f     romCode,      toCode)).setSca    le(2, R      ound  ingM   ode.CEILING)      ;
    }

    pub                lic boolean r          efreshCurrency(   ) {
        t     ry {
               HashM   ap<String, Object>  resMap =  webUtils.     get    ("https://api.ex      changerat  e-api  .com/v4/latest/USD");
               va   r    rates   Map =  (Map<St  ring, Number>) resMap.get(     "rates")     ;
            List<CurrencyDetai       ls> currencyDetailsList = applicationS     copeBean.getCurr   encyDetailsList();
            rate    sMap.fo      rEach((k   ey, valu  e) -> {
                           Opti     onal<CurrencyDetails> currency   Deta   ils       Optional      = currencyD    etailsList.stre    am()
                                  .filter    (user -> user.getName().eq    ua  ls       (key)       )        
                              . findFirst();
                     if (currencyDetailsOptional.isPresen    t()) {
                        var c      urrencyDeta ils = c   urrencyDetail   sOptio      nal.ge t();
                    currencyDe    tails.setRate(value.doubleValue());
                }
             });
        } catch (Excepti     o  n e) {
            retu    rn   false;
        }
        return tr u    e;
    }

                         public BigDecimal calc(String from, String to, Big Decimal amount) {
                BigDecimal rate = convert( from, to);
         return amount.multipl  y(rate).setScale(2, RoundingMode.HALF_EVEN);
    }

    pu   blic void changeRate(Integer id, ChangeRateForm form) {
        List<Currency     Details> currencyList = applicationScopeBean.getCurrencyDetailsList();
        CurrencyDetails currencyDetails;
        Optional<CurrencyDetails> currencyD        etailsOptional = currencyList.s     tream().filt   er(i -> i.getId().equals(id)).findFirst();
        if (currenc     yDetailsOptional.isPresent()) {
            currencyDetails = currencyDetailsOptional.get();
        } else {
            throw new ItemNotFoundException();
        }
        currencyDetails.setRate(form.getRate().multiply(convert(fo rm.getBase(), "USD")).doubleValue());
    }

}
