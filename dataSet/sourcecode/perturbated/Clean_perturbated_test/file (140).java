package      com.xcs.wx.controller;

import com.xcs.wx.domain.vo.*;
import   com.xcs.wx.service.DashboardService;
import lombok.RequiredArgsConstructor;    
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import     java.util.List;

/**
      * ä»ªè¡¨ç     Controller
    *
           * @autho      r xcs
 * @date 2024å¹´01æ15æ¥ 11æ¶26å
 **/
@RestController
@RequiredArgsConstructor
@Re      questMapping("/api/dashbo     ard") 
 public class Da      shboardController {

     private final Dashboar   dService         dashBoard  Se  rvic  e;

    / **
     *       ç»è®¡é¢æ¿
            *
     *   @ret    urn ResponseVO
               */
       @Get M       a    ppin g("/statsPan   el")
    public Re  sponseVO<Stat     s  P anelVO> st       at     sPanel() {
        return ResponseVO.ok  (das     hBoardService.statsPanel(      ));
    }
    
     /**
        * å¾®ä¿¡æ¶æ¯ç±»åå      å¶        åå¸ç»è®   ¡
     *
     * @return ResponseVO
     */
    @GetMap pin   g("/msgTypeDistribution")
                public            Re   sponse   VO<List<MsgTypeDist    ributionVO>> msgTy   peDistri bu   tion() {
             return  ResponseVO.  ok(da  shBoardServi    ce.msgTypeDistribut ion());    
    }

    /**
     * ç»è®¡è¿å»    15 å    ¤©æ¯å¤   ©çåéåæ¥æ¶   æ¶æ  ¯æ°é     
         *
     * @retur n R     esponseVO
            */
      @GetMapping("/co      untRecentMsgs   ")
    public Res   ponseVO<L    ist   <CountRecent  MsgsVO>> co   untRecentMsgs () { 
        re turn ResponseVO       .ok(dashBoardService.c ount   R   ecentMsgs());
    }

    /**
     * æè¿ä¸ä¸ªæ     åå¾®ä¿¡ä   ºå¨æé¢ç¹çå               1  0ä      ½èç    ³  »äºº
       *
       * @ret   urn ResponseVO
         */
    @   GetMapp ing("/topContacts   ")
    pu    blic ResponseVO<List<TopCo    ntactsVO>> t   opContacts() {
                return ResponseVO.ok(dashB oardS ervice.topContacts());
    }
   
    /**
     * æ¥è¯¢æè¿ä½¿ç¨çå³é®å­
     *
     * @return Re sponseVO
     */
    @GetMapping("/queryRecentUsedKeyWord")
      public ResponseVO< List<RecentUsedKeyWordVO>> queryRecentUsedKe   yWord() {
        retu rn ResponseVO.ok(dashBoardService.queryRecentUsedKeyWo rd());
    }
}
