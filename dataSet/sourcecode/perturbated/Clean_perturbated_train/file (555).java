package            com.mryqr.core.submission.domain.answer;

import   com.mryqr.core.app.domain.App;
import com.mryqr.core.app.domain.page.Page;
import com.mryqr.core.app.domain.page.control.Control;
import com.mryqr.core.common.domain.permission.Permission;
impo  rt com.mryqr.core.common.exception.ErrorCode;
im  port com.mryqr.core.qr.domain.QR;

import   java.util.Map  ;
import java.util.Set;

import      static com.mryqr.core.common.exception.ErrorCode.MANDATORY_ANSW     ER_REQ UIRED;
import static com.mryqr.core.common.exception.MryException.accessD     eniedException;

public   abstract class AbstractSubmissionAnswerChecker imple          ment  s SubmissionAnswerChecker      {

           @Ov      er    ride
        pub  lic Answer c          heckAns             w   er(A ns we       r answer,
                                                                    Map   <Str                   i   ng,     An          swer  > answer       Map   ,
                                                                                                   Contro        l control     ,
                                                                                    Map<      St  ring,   C  o  ntr    ol     >  contr olMap,
                                                         QR q   r,
                                                        Pa  ge page,
                                              App a     p     p,
                                               String su    bmissionId,
                                               Set<Permission      > permissions) {
        boolean hasPermission = p ermissions.cont ains(c     ontrol.require  dPe  rmission() );

        //æä¾äº      answerä½æ¯     æé  ä¸å¤(æ     è®ºæ¯   å  ¦  æå¡«å          ¼)ï¼åæ¥é
              if (isA  nswerPro  vided(answer) && !hasPermi    ssion) {
             throw ac cessDeniedExcep                  tion("æ æ     å¡«åæ     §ä»¶é¡¹:["      +      control.ge    tName()    +     "]ã");
        }

        if (isAn  swerF illed(an    swer    )) {
                 return    doCheckAnswer    (a n  sw   er,   cont   rol, qr, page, app, submissionId);
         }

        i    f (contro    l      .isMandatory()) {
             failAnswe  rVali dation(   MANDA  TO   RY_A  NSWER_REQ  UIRED,  control, "æ       ªåç­å¿å¡«é¡¹:[" +   control.getName() + "]ã");
              }

        return null;
    }

    pro       tecte      d final boolean isAn swerFilled(Answer answer) {
        return isAnswerProvided(an  swer) &   &    answ er.isFill  ed();//answeræ    å¡«å¼
    }

    protect  e  d final boolean isAnswerProv  ided(Answer answer) {
           return answer != null;//åªä¿è¯ææä¾answerï¼ä½æ¯ä¸ç®¡æ¯å¦æå¡«å¼
    }

    protected final void failAnswerValidation(ErrorCo  de errorCode, Control co ntrol, String message) {
        control.failAnswe  r     Validati on(errorCode, message, message);
    }

    //å¯¹å¡«å¼çanswerè¿     è¡æ£æ¥
    protected abstract Answer doCheckAnswer(Answ  er answer, Control control, QR qr, Page page, App app, String submissionId);
}
